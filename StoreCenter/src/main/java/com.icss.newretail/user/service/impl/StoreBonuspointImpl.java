package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.StoreBonuspointReq;
import com.icss.newretail.model.StoreBonuspointRuleDTO;
import com.icss.newretail.service.user.StoreBonuspointRuleService;
import com.icss.newretail.service.user.StoreBonuspointService;
import com.icss.newretail.user.dao.StoreBonuspointMapper;
import com.icss.newretail.user.dao.StoreBonuspointRecordMapper;
import com.icss.newretail.user.dao.StoreGradelevelRuleMapper;
import com.icss.newretail.user.dao.StoreInfoMapper;
import com.icss.newretail.user.entity.StoreBonuspoint;
import com.icss.newretail.user.entity.StoreBonuspointRecord;
import com.icss.newretail.user.entity.StoreGradelevelRule;
import com.icss.newretail.user.entity.StoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class StoreBonuspointImpl implements StoreBonuspointService {

//根据门店编码  更新门店积分  生成积分记录   门店积分变动   修改门店等级

    //门店积分

    @Autowired
    private StoreBonuspointMapper storeBonuspointMapper;

    //门店积分记录
    @Autowired
    private StoreBonuspointRecordMapper storeBonuspointRecordMapper;

    //门店表
    @Autowired
    private StoreInfoMapper storeInfoMapper;

    //门店积分规则
    @Autowired
    private StoreBonuspointRuleService storeBonuspointRuleService;

    //门店等级表
    @Autowired
    private StoreGradelevelRuleMapper storeGradelevelRuleMapper;

    @Override
    @Transactional
    public ResponseBase updateStoreScore(StoreBonuspointReq storeBonuspointReq) {
        ResponseBase rb = new ResponseBase();
        rb.setCode(0).setMessage("积分更新失败");
        try {
            //  根据门店id  查询门店当前积分
            QueryWrapper<StoreBonuspoint> qwQuery = new QueryWrapper<>();
            qwQuery.eq( "org_seq", storeBonuspointReq.getOrgSeq());
            StoreBonuspoint storeBonuspoint = storeBonuspointMapper.selectOne(qwQuery);
            //总积分   当前分
            BigDecimal totalScore = storeBonuspoint.getTotalScore().add(storeBonuspointReq.getScore());
            BigDecimal currentScore = storeBonuspoint.getCurrentScore().add(storeBonuspointReq.getScore());
            //1.0 更新门店积分信息  //不能使用这种方法更新门店积分
            Integer l =  storeBonuspointMapper.updateStoreScore(storeBonuspoint.getUuid(),storeBonuspointReq.getScore(),storeBonuspointReq.getScore());
            //2.0 插入 积分变动记录表
            StoreBonuspointRecord storeBonuspointRecord = new StoreBonuspointRecord();
            storeBonuspointRecord.setUuid(UUID.randomUUID().toString());
            //门店ID
            storeBonuspointRecord.setOrgSeq(storeBonuspointReq.getOrgSeq());
            //积分值
            storeBonuspointRecord.setBonuspoint(storeBonuspointReq.getScore());
            //订单关联ID
            String orderId = storeBonuspointReq.getOrderId();
            if (orderId != null || !orderId.equals("")) {
                storeBonuspointRecord.setOrderId(orderId);
            }
            storeBonuspointRecord.setCreateTime(LocalDateTime.now());
            //备注
            storeBonuspointRecord.setRemark("积分记录");
            //获取积分类型   门店---- 1 分享所得   2  用户消费 门店获得积分  3门店所得积分  用来抵扣RMB（3暂时没有）
            storeBonuspointRecord.setSourceType(storeBonuspointReq.getSourceType());
            storeBonuspointRecordMapper.insert(storeBonuspointRecord);

            //获取最高等级门店-----
            StoreGradelevelRule storeGradelevel = storeGradelevelRuleMapper.queryMaxGradeleve();

            //如果总分值大于最高等级
            if(storeGradelevel!=null&&totalScore.intValue()>=storeGradelevel.getEndScore().intValue()){
                Integer i = storeInfoMapper.updateGradelevelOne(storeGradelevel.getUuid(),storeBonuspointReq.getOrgSeq());
            }else {
                //3.2 把等级入库到店铺信息表     有状态的  必须状态==1  在启用状态  ok
                Integer j = storeInfoMapper.updateGradelevel(storeBonuspointReq.getOrgSeq());
                rb.setCode(1).setMessage("积分变动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rb.setCode(0).setMessage("积分更新失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rb;
    }
    /**
     * 情况一   会员属于绑定门店  积分比例 100%转换
     * 情况二   会员在非绑定门店消费  积分比例按照规则来
     *
     * @param storeBonuspointReq
     * @return
     */
    @Override
    @Transactional
    public ResponseBase addStoreScore(StoreBonuspointReq storeBonuspointReq) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(1).setMessage("操作成功");
        try {
            //查询门店积分规则
            ResponseResult<StoreBonuspointRuleDTO> result = storeBonuspointRuleService.queryStoreBonuspointOne();
            StoreBonuspointRuleDTO resultStoreBonuspointRuleDTO = result.getResult();
            //积分比例转换   消费多少金额 ---- 得多少积分  divide积分值
            BigDecimal divide = storeBonuspointReq.getScore().divide(resultStoreBonuspointRuleDTO.getConsumeRate());
            ResponseBase responseBase1 = new ResponseBase();
            if (storeBonuspointReq.getOrgSeq().equals(storeBonuspointReq.getBindOrgSeq())) {
                storeBonuspointReq.setScore(divide);
                //绑定门店消费  divide
                responseBase1 = updateStoreScore(storeBonuspointReq);
            } else {
                //非绑定门店消费
                //兼容之前方法  参数转换
                String bindOrgSeq = storeBonuspointReq.getBindOrgSeq();
                String orgSeq = storeBonuspointReq.getOrgSeq();
                //1.0  绑定门店获得积分
                //-----按照比例来分配积分---门店积分规则表
                storeBonuspointReq.setScore(divide.multiply(resultStoreBonuspointRuleDTO.getBackBindRate()));
                storeBonuspointReq.setOrgSeq(bindOrgSeq);
                responseBase1 = updateStoreScore(storeBonuspointReq);
                if(responseBase1.getCode()==0){
                    throw new Exception(responseBase1.getMessage());
                }
                //2.0  非绑定门店获得积分
                //-----按照比例来分配积分---门店积分规则表
                storeBonuspointReq.setScore(divide.multiply(resultStoreBonuspointRuleDTO.getBackNobindRate()));
                storeBonuspointReq.setOrgSeq(orgSeq);
                responseBase1 = updateStoreScore(storeBonuspointReq);
                if(responseBase1.getCode()==0){
                    throw new Exception(responseBase1.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBase.setCode(0).setMessage("操作失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return responseBase;
    }

}
