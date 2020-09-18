package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.CardConfigDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.ServiceRecordDTO;
import com.icss.newretail.model.StoreCardDTO;
import com.icss.newretail.model.StoreCardReq;
import com.icss.newretail.model.StoreUsableCardDTO;
import com.icss.newretail.model.UserOrgRelationDTO;
import com.icss.newretail.service.user.StoreCardService;
import com.icss.newretail.user.dao.UserCardInfoMapper;
import com.icss.newretail.user.entity.UserCardInfo;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.StringUtils;
import com.icss.newretail.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author : yanghu
 * @date : Created in 2020/7/16 17:10
 * @description : TODO
 * @modified By :
 * @version: : TODO
 */

@Service
public class StoreCardServiceImpl implements StoreCardService {


    @Autowired
    UserCardInfoMapper userCardInfoMapper;

    /**
     * ---分页查--查-----
     *
     * @param pageData
     * @return
     */
    @Override
    public ResponseResultPage<StoreCardDTO> queryStoreCardInfo(PageData<StoreCardDTO> pageData) {
        ResponseResultPage<StoreCardDTO> resultPage = new ResponseResultPage<>();
        QueryWrapper<UserCardInfo> qw = new QueryWrapper<>();
        Page<UserCardInfo> page = new Page<>();
        page.setCurrent(pageData.getCurrent());
        page.setSize(pageData.getSize());
        page.setAsc(pageData.getAscs());
        page.setDesc(new String[]{"status", "create_time"});

        qw.eq((pageData.getCondition().getCardType() != null), "card_type", pageData.getCondition().getCardType());
        qw.eq("status", 1);
        qw.like(!StringUtils.isEmpty(pageData.getCondition().getCardName()), "card_name", pageData.getCondition().getCardName());

        Page<UserCardInfo> userCardInfoPage = userCardInfoMapper.selectPage(page, qw);
        List<StoreCardDTO> bonuspointRuleDTO = Object2ObjectUtil.parseList(userCardInfoPage.getRecords(), StoreCardDTO.class);
        //增加字段   判断类型为 商品名片 和 活动名片的时候  是否过期
        Iterator<StoreCardDTO> iterator = bonuspointRuleDTO.iterator();
        while (iterator.hasNext()) {
            StoreCardDTO next = iterator.next();
            if (next.getCardType() == 1 || next.getCardType() == 2) {
                LocalDate current = LocalDateTime.now().toLocalDate();
                LocalDate begin = next.getBeginTime().toLocalDate();
                LocalDate end = next.getEndTime().toLocalDate();
                if (begin == null || end == null || current.isAfter(end)) {
                    next.setExpiredStatus(0);
                } else if (current.isBefore(begin)) {
                    next.setExpiredStatus(2);
                } else {
                    next.setExpiredStatus(1);
                }
            } else if (next.getCardType() == 3) {
                next.setEndTime(null);
                next.setBeginTime(null);
            }
        }
        resultPage.setTotal(userCardInfoPage.getTotal()).setRecords(bonuspointRuleDTO);
        resultPage.setCurrent(userCardInfoPage.getCurrent()).setSize(userCardInfoPage.getSize());
        resultPage.setMessage("成功");
        return resultPage;
    }

    /**
     * 单个查询----根据uuid
     *
     * @param uuid
     * @return
     */
    @Override
    public ResponseResult<StoreCardDTO> queryStoreCardInfoByUuid(String uuid) {
        ResponseResult<StoreCardDTO> result = new ResponseResult<>();
        result.setMessage("成功");
        UserCardInfo userCardInfo = userCardInfoMapper.selectById(uuid);
        StoreCardDTO storeCardDTO = Object2ObjectUtil.parseObject(userCardInfo, StoreCardDTO.class);
        result.setResult(storeCardDTO);
        return result;
    }

    /**
     * 新增
     *
     * @param storeCardDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseBase addStoreCardInfo(StoreCardDTO storeCardDTO) {
        ResponseBase result = new ResponseBase();
        try {
            //如果类型为商品名片  只允许发布一个
            if (storeCardDTO == null) {
                result.setCode(0).setMessage("新增失败,参数为空!");
                return result;
            }
            UserCardInfo userCardInfo = Object2ObjectUtil.parseObject(storeCardDTO, UserCardInfo.class);
            String currUser = JwtTokenUtil.currUser();
            LocalDateTime now = LocalDateTime.now();
            userCardInfo.setUuid(UUID.randomUUID().toString()).setCreateTime(now).setCreateUser(currUser).setStatus(1).setUpdateUser(currUser).setUpdateTime(now);
            if (storeCardDTO.getCardType() == 1) {
                QueryWrapper<UserCardInfo> qw = new QueryWrapper<>();
                qw.eq("card_type", 1);
                qw.eq("status", 1);
                UserCardInfo userCardInfo1 = userCardInfoMapper.selectOne(qw);
                if (userCardInfo1 != null) {
                    result.setCode(0).setMessage("新增失败,请编辑已有商品名片");
                    return result;
                }
            }
            int insert = userCardInfoMapper.insert(userCardInfo);
            if (insert == 1) {
                result.setMessage("成功");

                //   最新活动名片   属性字段  newest_activity_download  属性
                if (storeCardDTO.getCardType() == 2) {
                    userCardInfoMapper.updateNewestActivityDownload();
                }
            } else {
                result.setCode(0).setMessage("新增失败!");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            e.printStackTrace();
            result.setCode(0).setMessage("后台异常,新增失败");
        }
        return result;
    }

    /**
     * 修改
     *
     * @param storeCardDTO
     * @return
     */
    @Override
    public ResponseBase alterStoreCardInfo(StoreCardDTO storeCardDTO) {
        ResponseBase result = new ResponseBase();
        try {
            UserCardInfo userCardInfo = Object2ObjectUtil.parseObject(storeCardDTO, UserCardInfo.class);
            userCardInfo.setUpdateUser(JwtTokenUtil.currUser()).setUpdateTime(LocalDateTime.now()).setStatus(1);
            //修改的时候  如果类型为商品  ==1  判断除当前id之外 是否存在类型为1的
            if (storeCardDTO != null && storeCardDTO.getCardType() != null && storeCardDTO.getCardType() == 1) {
                QueryWrapper<UserCardInfo> qw = new QueryWrapper<>();
                qw.eq("status", 1);
                qw.eq("card_type", 1);
                qw.notIn("uuid", userCardInfo.getUuid());
                UserCardInfo userCardInfo1 = userCardInfoMapper.selectOne(qw);
                if (userCardInfo1 != null) {
                    result.setCode(0).setMessage("修改失败,请编辑已有的商品名片");
                    return result;
                }
            }
            int insert = userCardInfoMapper.updateById(userCardInfo);
            if (insert == 1) {
                result.setMessage("成功");
            } else {
                result.setCode(0).setMessage("修改失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0).setMessage("后台异常,修改失败");
        }
        return result;
    }

    /**
     * 更新门店卡片配置
     *
     * @param storeCardReq
     * @return
     */
    @Override
    public ResponseBase updateStoreCardConfig(StoreCardReq storeCardReq) {
        ResponseBase responseBase = new ResponseBase();
        if (storeCardReq == null || storeCardReq.getOrgSeq() == null || storeCardReq.getOrgSeq().equals("")) {
            responseBase.setCode(0).setMessage("店铺组织编码参数不能为空");
            return responseBase;
        }
        Integer i = userCardInfoMapper.updateStoreCardConfig(storeCardReq);
        //根据userid 查出这个人是不是店长或者是店主
        if (ToolUtil.notEmpty(storeCardReq.getUserId())) {
            UserOrgRelationDTO userOrgRelationDTO = userCardInfoMapper.queryUserType(storeCardReq.getUserId());
            if (ToolUtil.notEmpty(userOrgRelationDTO) && ToolUtil.notEmpty(userOrgRelationDTO.getUserType())) {
                Integer userType = userOrgRelationDTO.getUserType();
                if (userType == 4) { //4店主 5 店长
                    userCardInfoMapper.updateStoredz(storeCardReq);
                } else if (userType == 5) {
                    userCardInfoMapper.updateStoreManager(storeCardReq);
                }
            }
        }
        if (i > 0) {
            responseBase.setCode(1).setMessage("更新成功");
        } else {
            responseBase.setCode(0).setMessage("更新失败");
        }
        return responseBase;
    }

    /**
     * 查询店铺卡片配置
     *
     * @param storeCardReq
     * @return
     */
    @Override
    public ResponseResult<CardConfigDTO> queryStoreCardConfig(StoreCardReq storeCardReq) {
        ResponseResult<CardConfigDTO> responseBase = new ResponseResult<>();
        try {
            responseBase.setMessage("成功");
            if (storeCardReq == null || storeCardReq.getOrgSeq() == null || storeCardReq.getOrgSeq().equals("")) {
                responseBase.setCode(0).setMessage("店铺组织编码参数不能为空");
                return responseBase;
            }
            CardConfigDTO cardConfigDTO = userCardInfoMapper.queryStoreCardConfig(storeCardReq);

            //如果查出来  店铺还没有设置数据   使用默认数据
            if (cardConfigDTO == null || cardConfigDTO.getCardPic() == null || cardConfigDTO.getCardPic().equals("")) {
//          cardConfigDTO = new CardConfigDTO();
                UserCardInfo userCardInfo = userCardInfoMapper.selectById("88888888");
                cardConfigDTO.setCardPic(userCardInfo.getCardPic());
                cardConfigDTO.setCardName(userCardInfo.getCardName());
                cardConfigDTO.setFontColor(userCardInfo.getFontColor());
            }
            responseBase.setResult(cardConfigDTO);
        } catch (Exception e) {
            responseBase.setCode(0).setMessage("查询失败");
        }
        return responseBase;
    }

    /**
     * @return
     */
    @Override
    public ResponseRecords<StoreUsableCardDTO> queryStoreUsableCard(String orgSeq) {
        ResponseRecords<StoreUsableCardDTO> responseRecords = new ResponseRecords<>();
        List<StoreUsableCardDTO> userCardInfos = userCardInfoMapper.queryStoreUsableCard(orgSeq);

        //如果没有选中 --默认选中  默认店铺名片
        Iterator<StoreUsableCardDTO> iterator = userCardInfos.iterator();
        StoreUsableCardDTO storeUsableCardDTO = null;
        Boolean flag = false;

        while (iterator.hasNext()) {
            StoreUsableCardDTO next = iterator.next();
            if (next.getChecked() == 1) {
                flag = true;
            }
            if (next.getUuid().equals("88888888")) {
                storeUsableCardDTO = next;
            }
        }
        if (!flag && storeUsableCardDTO != null) {
            storeUsableCardDTO.setChecked(1);
        }

        responseRecords.setMessage("成功");
        responseRecords.setRecords(userCardInfos);
        return responseRecords;
    }

    /**
     * 获取商品专用名片
     * ---------是否发布  ----  有没有过期
     *
     * @return
     */
    @Override
    public ResponseResult<StoreCardDTO> queryGoodsUsaCard() {
        ResponseResult<StoreCardDTO> result = new ResponseResult<>();
        try {
            QueryWrapper<UserCardInfo> qw = new QueryWrapper<>();
            qw.eq("card_type", 1);
            qw.eq("status", 1);
            qw.eq("publish_status", 1);
            UserCardInfo userCardInfo = userCardInfoMapper.selectOne(qw);
            if (userCardInfo == null) {
                result.setMessage("不存在发布的商品名片");
                return result;
            }
            result.setMessage("成功");
            StoreCardDTO storeCardDTO = Object2ObjectUtil.parseObject(userCardInfo, StoreCardDTO.class);
            LocalDate current = LocalDateTime.now().toLocalDate();
            if (storeCardDTO.getBeginTime() == null || storeCardDTO.getEndTime() == null) {
                storeCardDTO = null;
            }
            LocalDate begin = storeCardDTO.getBeginTime().toLocalDate();
            LocalDate end = storeCardDTO.getEndTime().toLocalDate();
            if (current.isBefore(begin) || current.isAfter(end)) {
                //移除失效的名片
                storeCardDTO = null;
            }
            result.setResult(storeCardDTO);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0).setMessage("查询失败!后台异常");
        }
        return result;
    }

    /**
     * 获取活动可用名片集合
     * --------是否发布   -----   有没有过期
     *
     * @return
     */
    @Override
    public ResponseRecords<StoreCardDTO> queryActivityUsaCard() {
        ResponseRecords<StoreCardDTO> result = new ResponseRecords<>();
        try {
            QueryWrapper<UserCardInfo> qw = new QueryWrapper<>();
            qw.eq("card_type", 2);
            qw.eq("status", 1);
            qw.eq("publish_status", 1);
            List<UserCardInfo> userCardInfos = userCardInfoMapper.selectList(qw);
            result.setMessage("成功");
            List<StoreCardDTO> storeCardDTOS = Object2ObjectUtil.parseList(userCardInfos, StoreCardDTO.class);

            //商品和活动名片  有效期判断   不在有效期的移除
            Iterator<StoreCardDTO> iterator = storeCardDTOS.iterator();
            while (iterator.hasNext()) {
                StoreCardDTO next = iterator.next();
                LocalDate current = LocalDateTime.now().toLocalDate();
                if (next.getBeginTime() == null || next.getEndTime() == null) {
                    iterator.remove();
                }
                LocalDate begin = next.getBeginTime().toLocalDate();
                LocalDate end = next.getEndTime().toLocalDate();
                if (current.isBefore(begin) || current.isAfter(end)) {
                    //移除失效的名片
                    iterator.remove();
                }
            }

            result.setRecords(storeCardDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0).setMessage("查询失败!后台异常");
        }
        return result;
    }


    @Override
    public ResponseResult<ServiceRecordDTO> queryStoreInfo(String orgSeq, String userId) {
        ResponseResult<ServiceRecordDTO> result = new ResponseResult<>();
        ServiceRecordDTO serviceRecordDTO = new ServiceRecordDTO();
        ServiceRecordDTO storeInfo = userCardInfoMapper.queryStoreInfo(orgSeq);
        ServiceRecordDTO userInfo = userCardInfoMapper.queryUserInfo(orgSeq, userId);
        serviceRecordDTO.setAuthCode(storeInfo.getAuthCode());
        serviceRecordDTO.setBaseName(storeInfo.getBaseName());
        serviceRecordDTO.setStoreName(storeInfo.getStoreName());
        serviceRecordDTO.setUpOrgZone(storeInfo.getUpOrgZone());

        serviceRecordDTO.setUserName(userInfo.getUserName());
        serviceRecordDTO.setUserTypeName(userInfo.getUserTypeName());
        result.setResult(serviceRecordDTO);
        return result;
    }
}
