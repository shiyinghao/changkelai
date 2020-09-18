package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.icss.newretail.model.*;
import com.icss.newretail.service.pay.IcbcInteractiveService;
import com.icss.newretail.service.pay.PayTradeService;
import com.icss.newretail.service.trade.TradeInfoService;
import com.icss.newretail.service.user.BillingRecordService;
import com.icss.newretail.user.dao.*;
import com.icss.newretail.user.entity.BillingOrderShip;
import com.icss.newretail.user.entity.BillingRecord;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.StringUtils;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 店铺开票记录 服务实现类
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-05-26
 */
@Service
@Slf4j
public class BillingRecordServiceImpl implements BillingRecordService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private BillingRecordMapper billingRecordMapper;

    @Autowired
    private BillingRegionMapper billingRegionMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BillingOrderShipMapper billingOrderShipMapper;

    @RpcReference(microserviceName = "trade-service", schemaId = "tradeInfoApi")
    private TradeInfoService tradeInfoService;

    @RpcReference(microserviceName = "pay-service", schemaId = "PayTradeApi")
    private PayTradeService payTradeService;

    @RpcReference(microserviceName = "pay-service", schemaId = "IcbcInteractiveApi")
    private IcbcInteractiveService icbcInteractiveService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseBase addBillingRecord(BillingRecordDTO para) {
        ResponseBase base = new ResponseBase();
        String BillingRecordId = UUID.randomUUID().toString().replace("-", "");
        String userId = JwtTokenUtil.currUser();
        try {
            BillingRecord BillingRecord = Object2ObjectUtil.parseObject(para, BillingRecord.class);
            BillingRecord.setBillId(BillingRecordId);
            BillingRecord.setStatus(1);
            BillingRecord.setCreateUser(JwtTokenUtil.currUser());
            BillingRecord.setCreateTime(LocalDateTime.now());
            int a = billingRecordMapper.insert(BillingRecord);

            List<BillingOrderShipDTO> billingOrderShipDTOS = para.getBillingOrderShipDTOList();
            if (billingOrderShipDTOS != null && billingOrderShipDTOS.size() > 0) {
                for (int i = 0; i < billingOrderShipDTOS.size(); i++) {
                    BillingOrderShipDTO billingOrderShipDTO = billingOrderShipDTOS.get(i);
                    BillingOrderShip billingOrderShip = Object2ObjectUtil.parseObject(billingOrderShipDTO, BillingOrderShip.class);
                    billingOrderShip.setUuid(UUID.randomUUID().toString().replace("-", ""));
                    billingOrderShip.setBillId(BillingRecordId);
                    billingOrderShipMapper.insert(billingOrderShip);
                }
            }

            if (a > 0) {
                //提交给银行
                UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(para.getOrgSeq());
                if (ToolUtil.isEmpty(userStoreInfoDTO)) {
                    throw new Exception("根据门店编码未查询到门店信息");
                }
                para.setBillId(BillingRecordId);
                para.setMerid(userStoreInfoDTO.getMerid());
                ResponseBase result = icbcInteractiveService.invoiceSubmit(para);
                if (result.getCode() != 1) {
                    throw new Exception("调用开票记录提交接口出错：" + result.getMessage());
                }
                base.setCode(1);
                base.setMessage("新增成功");
            } else {
                base.setCode(0);
                base.setMessage("新增失败");
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("新增失败" + ex.getMessage());
            log.error("BillingRecordServiceImpl|addBillingRecord->新增异常：" + ex.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
        }
        return base;
    }

    @Override
    public ResponseResultPage<BillingRecordDTO> queryBillingRecord(PageData<BillingRecordDTO> para) {
        ResponseResultPage<BillingRecordDTO> result = new ResponseResultPage<>();
        try {
            Page<BillingRecordDTO> page = new Page(para.getCurrent(), para.getSize());
            Page<BillingRecordDTO> resultPage = billingRecordMapper.queryBillingRecord(page, para.getCondition());
            result.setCode(1);
            result.setMessage("问题信息查询成功，共有" + resultPage.getRecords().size() + "条数据");
            result.setRecords(resultPage.getRecords());
            if(resultPage.getRecords().size() == 0){
                BillingRecordDTO billingRecordDTO = new BillingRecordDTO();
                if(!ToolUtil.isEmpty(para.getCondition().getOrgSeq())) {
                    UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(para.getCondition().getOrgSeq());
                    if (ToolUtil.isEmpty(userStoreInfoDTO)) {
                        throw new Exception("根据门店编码未查询到门店信息");
                    }
                    billingRecordDTO.setMerid(userStoreInfoDTO.getMerid());
                    billingRecordDTO.setCompanyName(userStoreInfoDTO.getCompanyName());
                    billingRecordDTO.setCompanyTel(userStoreInfoDTO.getLegalPersonPhone());
                    billingRecordDTO.setCompanyAddress(userStoreInfoDTO.getStoreAddress());
                    billingRecordDTO.setReceiver(userStoreInfoDTO.getStoreManagerName());
                    billingRecordDTO.setCompanyTel(userStoreInfoDTO.getStoreOwnerPhone());
                    billingRecordDTO.setReceiverAddress(userStoreInfoDTO.getStoreAddressDetail());
                    billingRecordDTO.setStatus(0);
                    List<BillingRecordDTO> list= Lists.newArrayListWithCapacity(1);
                    list.add(billingRecordDTO);
                    result.setRecords(list);
                }
            }
            result.setSize(para.getSize());
            result.setCurrent(para.getCurrent());
            result.setTotal(resultPage.getTotal());

        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("问题信息查询失败：" + ex.getMessage());
            log.error("BillingRecordServiceImpl|queryFeedback->问题信息查询：[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseBase updateBillingRecordById(BillingRecordDTO para) {
        ResponseBase base = new ResponseBase();
        try {
            BillingRecord billingRecord = Object2ObjectUtil.parseObject(para, BillingRecord.class);
            BillingRecord old = billingRecordMapper.selectById(para.getBillId());
            if(old == null){
                base.setCode(0);
                base.setMessage("修改失败;该开票记录不存在");
                return base;
            }
            int a = billingRecordMapper.updateById(billingRecord);
            if(para.getStatus() == 1){
                //提交给银行
                UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(para.getOrgSeq());
                if (ToolUtil.isEmpty(userStoreInfoDTO)) {
                    throw new Exception("根据门店编码未查询到门店信息");
                }
                para.setMerid(userStoreInfoDTO.getMerid());
                ResponseBase result = icbcInteractiveService.invoiceSubmit(para);
                if (result.getCode() != 1) {
                    throw new Exception("调用开票记录提交接口出错：" + result.getMessage());
                }
            }
            if(billingRecord.getStatus() == 2) {
                List<BillingOrderShip> billingOrderShipList = billingOrderShipMapper.selectList(
                        new QueryWrapper<BillingOrderShip>().eq("bill_id", para.getBillId()));
                if (billingOrderShipList != null && billingOrderShipList.size() > 0) {
                    PayRecordParamDTO param = new PayRecordParamDTO();
                    List<String> confirmList = billingOrderShipList.stream().map(BillingOrderShip::getOrderId).collect(Collectors.toList());
                    param.setConfirmList(confirmList);

                    ResponseBase responseBase = payTradeService.feePayRecordsByOrderId(param);
                    if (responseBase.getCode() != 1) {
                        billingRecord.setStatus(3);
                        int b = billingRecordMapper.updateById(billingRecord);
                    }
                }
                List<BillingOrderShipDTO> billingOrderShipDTOS = para.getBillingOrderShipDTOList();
                if (billingOrderShipDTOS != null && billingOrderShipDTOS.size() > 0) {
                    for (int i = 0; i < billingOrderShipDTOS.size(); i++) {
                        BillingOrderShipDTO billingOrderShipDTO = billingOrderShipDTOS.get(i);
                        BillingOrderShip billingOrderShip = Object2ObjectUtil.parseObject(billingOrderShipDTO, BillingOrderShip.class);
                        if (StringUtils.isEmpty(billingOrderShipDTO.getUuid())) {
                            billingOrderShip.setUuid(UUID.randomUUID().toString().replace("-", ""));
                            billingOrderShipMapper.insert(billingOrderShip);
                        } else {
                            billingOrderShipMapper.updateById(billingOrderShip);
                        }
                    }
                }
            }
            if (a != 0) {
                base.setCode(1);
                base.setMessage("修改成功");
            } else {
                base.setCode(0);
                base.setMessage("修改失败");
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage("修改失败：" + ex.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
        }
        return base;
    }

    @Override
    public ResponseResult<BillingRecordDTO> queryBillingRecordById(String orgseq,String billId) {
        ResponseResult<BillingRecordDTO> result = new ResponseResult<>();
        try {
//            BillingRecord billingRecord = billingRecordMapper.selectById(uuid);
//            BillingRecordDTO billingRecordDTO = Object2ObjectUtil.parseObject(billingRecord,BillingRecordDTO.class);
            BillingRecordDTO billingRecordDTO =  billingRecordMapper.queryBillingRecordById(billId);
            if (billingRecordDTO != null && !ToolUtil.isEmpty(billingRecordDTO.getBillingOrderShipDTOList())) {
                List<BillingOrderShipDTO> billingList = billingRecordDTO.getBillingOrderShipDTOList();
                List<BillingOrderShipDTO> orderList = queryPayRecordListOnly(orgseq,billingList);
                billingRecordDTO.setBillingOrderShipDTOList(orderList);
            }
            if (billingRecordDTO != null && billingRecordDTO.getOrgSeq() != null) {
                UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(billingRecordDTO.getOrgSeq());
                if (userStoreInfoDTO != null) {
                    billingRecordDTO.setUserStoreInfoDTO(userStoreInfoDTO);
                } else {
                    throw new Exception("根据门店orgSeq未查到门店");
                }
                result.setCode(1);
                result.setMessage("查询成功");
                result.setResult(billingRecordDTO);
            } else {
                result.setCode(0);
                result.setMessage("查询失败,订单详情不存在");
            }
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("详情明细查询失败：" + ex.getMessage());
            log.error("BillingRecordServiceImpl|queryBillingRecordById->详情明细查询：[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    public ResponseResultPage<BillingRegionDTO> queryRegionTel(PageData<BillingRecordDTO> para) {
        ResponseResultPage<BillingRegionDTO> result = new ResponseResultPage<>();
        try {
            Page<BillingRegionDTO> page = new Page(para.getCurrent(), para.getSize());
            Page<BillingRegionDTO> resultPage = billingRegionMapper.queryPage(page, para);
            result.setCode(1);
            result.setMessage("区域号码查询成功，共有" + resultPage.getRecords().size() + "条数据");
            result.setSize(para.getSize());
            result.setCurrent(para.getCurrent());
            result.setTotal(resultPage.getTotal());
            result.setRecords(resultPage.getRecords());
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("区域号码查询失败：" + ex.getMessage());
            log.error("BillingRecordServiceImpl|queryFeedback->区域号码查询：[" + ex.getMessage() + "]");
        }
        return result;
    }

    public List<BillingOrderShipDTO> queryPayRecordListOnly(String orgseq,List<BillingOrderShipDTO> orderList){
        if (!ToolUtil.isEmpty(orderList)) {
            StringBuffer orderIds = new StringBuffer();
            for (BillingOrderShipDTO billingOrderShipDTO : orderList) {
                orderIds.append("'").append(billingOrderShipDTO.getOrderId()).append("'").append(",");
            }
            List<TradeOrdeFmtDTO> orderDetailList = null;
            List<DayRecordInfoDTO> dayRecordInfoDTOList = null;
            if (orderIds.length() > 0) {
                TradeInfoParameterDTO params = new TradeInfoParameterDTO();
                params.setDetailInclude(0);//是否需要跨库商品中心取商品照片，单位信息等 true ? 0 : 1
                params.setOrderIds("(" + orderIds.substring(0, orderIds.length() - 1) + ")");
                orderDetailList = tradeInfoService.queryOrderListByParam(params);

                PayRecordParamDTO param = new PayRecordParamDTO();
                param.setOrgSeq(orgseq);
                param.setOrderIds("(" + orderIds.substring(0, orderIds.length() - 1) + ")");
                ResponseRecords<DayRecordInfoDTO> list = payTradeService.queryPayOrderThawRecordLists(param);
                dayRecordInfoDTOList = list.getRecords();
            }
            if (!ToolUtil.isEmpty(orderDetailList)) {
                for (BillingOrderShipDTO payRecordInfoDTO : orderList) {
                    for (TradeOrdeFmtDTO tradeOrdeFmtDTO : orderDetailList) {
                        if (tradeOrdeFmtDTO.getUuid().equals(payRecordInfoDTO.getOrderId())) {
                            payRecordInfoDTO.setOrderNo(tradeOrdeFmtDTO.getOrderNo());
                            payRecordInfoDTO.setMemberId(tradeOrdeFmtDTO.getMemberId());
                            payRecordInfoDTO.setMemberName(tradeOrdeFmtDTO.getMemberName());
                            payRecordInfoDTO.setTel(tradeOrdeFmtDTO.getTel());
                            payRecordInfoDTO.setUserName(tradeOrdeFmtDTO.getUserName());
                            payRecordInfoDTO.setUserHeadPic(tradeOrdeFmtDTO.getHeadPicUrl());
                            payRecordInfoDTO.setTotalAmount(tradeOrdeFmtDTO.getTotalAmount());
                            payRecordInfoDTO.setOrderStatus(tradeOrdeFmtDTO.getOrderStatus());
                            payRecordInfoDTO.setOrderTime(tradeOrdeFmtDTO.getCreateTime());
                            payRecordInfoDTO.setDeliveryMethod(tradeOrdeFmtDTO.getDeliveryMethod());
                            payRecordInfoDTO.setGoodsList(tradeOrdeFmtDTO.getGoodsList());
                            break;
                        }
                    }

                }
            }

            if (!ToolUtil.isEmpty(dayRecordInfoDTOList)) {
                for (BillingOrderShipDTO payRecordInfoDTO : orderList) {
                    for (DayRecordInfoDTO tradeOrdeFmtDTO : dayRecordInfoDTOList) {
                        if (tradeOrdeFmtDTO.getOrderId().equals(payRecordInfoDTO.getOrderId())) {
                            payRecordInfoDTO.setFeeMoney(tradeOrdeFmtDTO.getFeeMoney());
                            payRecordInfoDTO.setTotalMoney(tradeOrdeFmtDTO.getTotalMoney());
                            payRecordInfoDTO.setReceivedMoney(tradeOrdeFmtDTO.getReceivedMoney());
                            break;
                        }
                    }

                }
            }
        }
        return orderList;
    }

}
