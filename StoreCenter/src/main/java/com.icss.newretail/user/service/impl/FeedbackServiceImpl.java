package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.FeedbackDTO;
import com.icss.newretail.model.FeedbackPicturesDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserInfoDTO;
import com.icss.newretail.model.UserStoreInfoDTO;
import com.icss.newretail.service.pay.IcbcInteractiveService;
import com.icss.newretail.service.user.FeedbackService;
import com.icss.newretail.user.dao.FeedbackMapper;
import com.icss.newretail.user.dao.FeedbackPicturesMapper;
import com.icss.newretail.user.dao.OrganizationMapper;
import com.icss.newretail.user.dao.UserInfoMapper;
import com.icss.newretail.user.entity.Feedback;
import com.icss.newretail.user.entity.FeedbackPictures;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @RpcReference(microserviceName = "pay-service", schemaId = "IcbcInteractiveApi")
    private IcbcInteractiveService icbcInteractiveService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private FeedbackPicturesMapper feedbackPicturesMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    private final static String PROBLEMSEQ = "problemSeq";

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseBase addFeedback(FeedbackDTO para) {
        ResponseBase base = new ResponseBase();
        String feedbackId = UUID.randomUUID().toString();
        String userId = JwtTokenUtil.currUser();
        try {
            Feedback feedback = Object2ObjectUtil.parseObject(para, Feedback.class);
            feedback.setUuid(feedbackId);
            feedback.setProblemSeq(generateProblemSeq());
            feedback.setProcessingProgress(1);
            feedback.setCreateUser(userId);
            feedback.setCreateTime(LocalDateTime.now());
            int a = feedbackMapper.insert(feedback);

            List<FeedbackPicturesDTO> feedbackPictures = para.getFeedbackPictures();
            if (feedbackPictures != null && feedbackPictures.size() > 0) {
                for (int i = 0; i < feedbackPictures.size(); i++) {
                    FeedbackPicturesDTO feedbackPicturesDTO = feedbackPictures.get(i);
                    FeedbackPictures feedbackPicture = Object2ObjectUtil.parseObject(feedbackPicturesDTO, FeedbackPictures.class);
                    feedbackPicture.setUuid(UUID.randomUUID().toString());
                    feedbackPicture.setFeedbackId(feedbackId);
                    feedbackPicture.setCreateUser(JwtTokenUtil.currUser());
                    feedbackPicture.setCreateTime(LocalDateTime.now());
                    feedbackPicturesMapper.insert(feedbackPicture);
                }
            }
            if (a > 0) {
                //提交给银行
                UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(para.getOrgSeq());
                if (ToolUtil.isEmpty(userStoreInfoDTO)) {
                    throw new Exception("根据门店编码未查询到门店信息");
                }
                UserInfoDTO userInfo = userInfoMapper.queryUserById(userId);
                if (ToolUtil.isEmpty(userInfo)) {
                    throw new Exception("未查到人员信息");
                }
                para.setUuid(feedbackId);
                para.setMerchantNo(userStoreInfoDTO.getMerid());
                para.setMerchantName(userStoreInfoDTO.getCompanyName());
                para.setContactPerson(userInfo.getRealName());
                para.setContactPhone(userInfo.getTel());
                ResponseBase result = icbcInteractiveService.feedbackSubmit(para);
                if (result.getCode() != 1) {
                    throw new Exception("调用工单提交接口出错：" + result.getMessage());
                }
                base.setCode(1);
                base.setMessage("新增成功");
            } else {
                base.setCode(0);
                base.setMessage("新增失败");
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("新增失败：" + ex.getMessage());
            log.error("FeedbackServiceImpl|addFeedback->新增异常：" + ex.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
        }
        return base;
    }

    @Override
    public ResponseResultPage<FeedbackDTO> queryFeedback(PageData<FeedbackDTO> para) {
        ResponseResultPage<FeedbackDTO> result = new ResponseResultPage<>();
        try {
            Page<FeedbackDTO> page = new Page(para.getCurrent(), para.getSize());
            Page<FeedbackDTO> resultPage = feedbackMapper.queryFeedback(page, para.getCondition());
            result.setCode(1);
            result.setMessage("问题信息查询成功，共有" + resultPage.getRecords().size() + "条数据");
            result.setSize(para.getSize());
            result.setCurrent(para.getCurrent());
            result.setTotal(resultPage.getTotal());
            result.setRecords(resultPage.getRecords());
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("问题信息查询失败：" + ex.getMessage());
            log.error("FeedbackServiceImpl|queryFeedback->问题信息查询：[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    public ResponseResult<FeedbackDTO> queryFeedbackInfoById(String uuid) {
        ResponseResult<FeedbackDTO> result = new ResponseResult<>();
        try {
            FeedbackDTO feedbackDTO = feedbackMapper.queryFeedbackInfoById(uuid);
            if (feedbackDTO != null && feedbackDTO.getOrgSeq() != null) {
                UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(feedbackDTO.getOrgSeq());
                if (userStoreInfoDTO != null) {
                    UserInfoDTO userInfo = userInfoMapper.queryUserById(feedbackDTO.getCreateUser());
                    if (ToolUtil.isEmpty(userInfo)) {
                        throw new Exception("未查到人员信息");
                    }
                    userStoreInfoDTO.setStoreManagerName(userInfo.getRealName());
                    userStoreInfoDTO.setStoreManagerPhone(userInfo.getTel());
                    feedbackDTO.setUserStoreInfoDTO(userStoreInfoDTO);
                } else {
                    throw new Exception("根据门店orgSeq未查到门店");
                }
                result.setCode(1);
                result.setMessage("查询成功");
                result.setResult(feedbackDTO);
            } else {
                result.setCode(0);
                result.setMessage("查询失败,反馈问题不存在");
            }
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("反馈问题明细查询失败：" + ex.getMessage());
            log.error("FeedbackServiceImpl|queryFeedbackInfoById->反馈问题明细查询：[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    public ResponseBase updateFeedbackById(FeedbackDTO para) {
        ResponseBase base = new ResponseBase();
        try {
            Feedback feedback = Object2ObjectUtil.parseObject(para, Feedback.class);
//            feedback.setUpdateTime(LocalDateTime.now());
            int a = feedbackMapper.updateById(feedback);
            if (a != 0) {
                base.setCode(1);
                base.setMessage("修改成功");
            } else {
                base.setCode(0);
                base.setMessage("修改失败,无此记录");
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("修改失败：" + ex.getMessage());
        }
        return base;
    }

    /**
     * 生成问题编码
     *
     * @return
     */
    public String generateProblemSeq() {
        int year = LocalDate.now().getYear();
        DecimalFormat df = new DecimalFormat("0000");
        RedisAtomicLong counter = new RedisAtomicLong(PROBLEMSEQ, redisTemplate.getConnectionFactory());
        long count = counter.incrementAndGet();
        String problemSeq = "PROBLEM" + year + df.format(count);
        return problemSeq;
    }

}
