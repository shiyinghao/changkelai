package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserMessageInfoDTO;
import com.icss.newretail.model.UserMessageReadRecordDTO;
import com.icss.newretail.service.user.UserMessageInfoService;
import com.icss.newretail.user.dao.UserMessageInfoMapper;
import com.icss.newretail.user.dao.UserMessageReadRecordMapper;
import com.icss.newretail.user.entity.UserMessageInfo;
import com.icss.newretail.user.entity.UserMessageReadRecord;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.tools.Tool;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 消息通知表 服务实现类
 * </p>
 *
 * @author syh
 * @since 2020-08-20
 */
@Service
@Slf4j
public class UserMessageInfoServiceImpl implements UserMessageInfoService {

    private static final String MESSAGE_SEQ_KEY = "userMessage";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMessageInfoMapper userMessageInfoMapper;

    @Autowired
    private UserMessageReadRecordMapper userMessageReadRecordMapper;

    @Override
    @Transactional
    public ResponseBase createUserMessage(UserMessageInfoDTO userMessageInfoDTO) {
        ResponseBase base = new ResponseBase();
        try {
            UserMessageInfo userMessageInfo = Object2ObjectUtil.parseObject(userMessageInfoDTO, UserMessageInfo.class);
            userMessageInfo.setUuid(UUID.randomUUID().toString());
            userMessageInfo.setMessageSeq(generatePrizeCode());
            userMessageInfo.setCreateTime(LocalDateTime.now());
            userMessageInfo.setUpdateTime(LocalDateTime.now());
            userMessageInfo.setCreateUser(JwtTokenUtil.currUser());
            userMessageInfoMapper.insert(userMessageInfo);
            base.setCode(1);
            base.setMessage("添加成功");
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("添加失败" + ex.getMessage());
            log.error("UserMessageInfoServiceImpl|createUserMessage->创建消息[" + ex.getLocalizedMessage() + "]");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
        }
        return base;
    }

    @Override
    @Transactional
    public ResponseBase updateUserMessage(UserMessageInfoDTO userMessageInfoDTO) {
        ResponseBase base = new ResponseBase();
        try {
            UserMessageInfo userMessageInfo = Object2ObjectUtil.parseObject(userMessageInfoDTO, UserMessageInfo.class);
            userMessageInfo.setUpdateTime(LocalDateTime.now());
            userMessageInfo.setUpdateUser(JwtTokenUtil.currUser());
            userMessageInfoMapper.updateById(userMessageInfo);
            base.setCode(1);
            base.setMessage("修改成功");
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("修改失败" + ex.getMessage());
            log.error("UserMessageInfoServiceImpl|updateUserMessage->修改消息[" + ex.getLocalizedMessage() + "]");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
        }
        return base;
    }

    @Override
    public ResponseResultPage<UserMessageInfoDTO> queryUserMessage(PageData<UserMessageInfoDTO> pageData) {
        ResponseResultPage<UserMessageInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<UserMessageInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            Page<UserMessageInfoDTO> pageResult = userMessageInfoMapper.queryUserMessage(page, pageData.getCondition());
            if (ToolUtil.notEmpty(pageResult) && ToolUtil.notEmpty(pageResult.getRecords())) {
                List<UserMessageInfoDTO> list = pageResult.getRecords();
                for (UserMessageInfoDTO userMessageInfoDTO : list) {
                    if (ToolUtil.notEmpty(userMessageInfoDTO.getOrgSeq())) {
                        userMessageInfoDTO.setYdStatus(1);
                    } else {
                        userMessageInfoDTO.setYdStatus(0);
                    }
                }
            }
            result.setCode(1);
            result.setMessage("查询成功,共" + pageResult.getRecords().size() + "条记录");
            result.setCurrent(pageResult.getCurrent());
            result.setSize(pageResult.getSize());
            result.setTotal(pageResult.getTotal());
            result.setRecords(pageResult.getRecords());
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("查询异常" + e.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResultPage<UserMessageReadRecordDTO> queryUserMessageInfo(PageData<UserMessageReadRecordDTO> pageData) {
        ResponseResultPage<UserMessageReadRecordDTO> result = new ResponseResultPage<>();
        try {
            Page<UserMessageReadRecordDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            Page<UserMessageReadRecordDTO> pageResult = userMessageInfoMapper.queryUserMessageInfo(page, pageData.getCondition());
            result.setCode(1);
            result.setMessage("查询成功,共" + pageResult.getRecords().size() + "条记录");
            result.setCurrent(pageResult.getCurrent());
            result.setSize(pageResult.getSize());
            result.setTotal(pageResult.getTotal());
            result.setRecords(pageResult.getRecords());
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("查询异常" + e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional
    public ResponseBase addUserMessageInfo(UserMessageReadRecordDTO userMessageInfoDTO) {
        ResponseBase base = new ResponseBase();
        try {
            UserMessageReadRecord userMessageReadRecordd = userMessageReadRecordMapper.selectOne(new QueryWrapper<UserMessageReadRecord>()
                    .eq("message_id", userMessageInfoDTO.getMessageId())
                    .eq("org_seq", userMessageInfoDTO.getOrgSeq()));
            if (ToolUtil.isEmpty(userMessageReadRecordd)) {
                UserMessageReadRecord userMessageReadRecord = Object2ObjectUtil.parseObject(userMessageInfoDTO, UserMessageReadRecord.class);
                userMessageReadRecord.setUuid(UUID.randomUUID().toString());
                userMessageReadRecord.setCreateTime(LocalDateTime.now());
                userMessageReadRecord.setCreateUser(JwtTokenUtil.currUser());
                userMessageReadRecordMapper.insert(userMessageReadRecord);
            }
            base.setCode(1);
            base.setMessage("操作成功");
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("操作失败" + ex.getMessage());
            log.error("UserMessageInfoServiceImpl|addUserMessageInfo->添加消息阅读[" + ex.getLocalizedMessage() + "]");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
        }
        return base;
    }


    public String generatePrizeCode() {
        int year = LocalDate.now().getYear();
        DecimalFormat df = new DecimalFormat("000");
        RedisAtomicLong counter = new RedisAtomicLong(MESSAGE_SEQ_KEY, redisTemplate.getConnectionFactory());
        long count = counter.incrementAndGet();
        String prizeCode = "TSBH" + year + df.format(count);
        return prizeCode;
    }
}
