package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.*;
import com.icss.newretail.service.member.MemberService;
import com.icss.newretail.service.user.SideStoreService;
import com.icss.newretail.user.dao.StoreMapper;
import com.icss.newretail.user.entity.UserStoreInfo;
import com.icss.newretail.util.MathUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SideStoreServiceImpl implements SideStoreService {

    @RpcReference(microserviceName = "member-service", schemaId = "MemberApi")
    private MemberService memberService;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public ResponseResult<RecommendStoreDTO> recommend(ConsumerInfoRequest consumerInfoRequest) {

        ResponseResult<RecommendStoreDTO> result = new ResponseResult<>();

        try {
            //调用memberService 从nr_member库中获取到门店Id
            ResponseResult<String> responseResult = memberService.queryMemberById(consumerInfoRequest.getConsumerId());
            if (ToolUtil.notEmpty(responseResult) && ToolUtil.notEmpty(responseResult.getResult())) {
                UserStoreInfo userStoreInfo = storeMapper.selectOne(new QueryWrapper<UserStoreInfo>().eq("org_seq", responseResult.getResult()));
                if (ToolUtil.notEmpty(userStoreInfo)) {
                    //获取店铺经度 纬度
                    String storeLngStr = userStoreInfo.getStoreLng();
                    String storeLatStr = userStoreInfo.getStoreLat();
                    //获取消费者的经度和纬度
                    String consumerLngStr = consumerInfoRequest.getLng();
                    String consumerLatStr = consumerInfoRequest.getLat();

                    //将经度和纬度转换为Double类型,并计算距离
                    double storeLng = Double.valueOf(storeLngStr).doubleValue();
                    double storeLat = Double.valueOf(storeLatStr).doubleValue();
                    double consumerLng = Double.valueOf(consumerLngStr).doubleValue();
                    double consumerLat = Double.valueOf(consumerLatStr).doubleValue();

                    Double distance = MathUtil.getDistance(storeLng, storeLat, consumerLng, consumerLat);

                    RecommendStoreDTO recommendStoreDTO = Object2ObjectUtil.parseObject(userStoreInfo, RecommendStoreDTO.class);
                    recommendStoreDTO.setDistance(distance.intValue());
                    result.setCode(1);
                    result.setMessage("获取到该会员绑定的店铺信息");
                    result.setResult(recommendStoreDTO);
                } else {
                    result.setCode(0);
                    result.setMessage("您曾经绑定的店铺不存在!");
                }
            } else {
                //如果该用户不是会员,那么则根据经纬度,查询距离最近的门店
                UserStoreInfo userStoreInfo = storeMapper.queryStoreByLngAndlat(consumerInfoRequest);
                if (ToolUtil.notEmpty(userStoreInfo)) {
                    double storeLng = Double.valueOf(userStoreInfo.getStoreLng()).doubleValue();
                    double storeLat = Double.valueOf(userStoreInfo.getStoreLat()).doubleValue();
                    double consumerLng = Double.valueOf(consumerInfoRequest.getLng()).doubleValue();
                    double consumerLat = Double.valueOf(consumerInfoRequest.getLat()).doubleValue();

                    Double distance = MathUtil.getDistance(storeLng, storeLat, consumerLng, consumerLat);
                    RecommendStoreDTO recommendStoreDTO = Object2ObjectUtil.parseObject(userStoreInfo, RecommendStoreDTO.class);
                    recommendStoreDTO.setDistance(distance.intValue());
                    result.setCode(1);
                    result.setMessage("您尚未绑定店铺,为您推荐最近店铺");
                    result.setResult(recommendStoreDTO);
                } else {
                    result.setCode(0);
                    result.setMessage("未查到门店信息");
                    return result;
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("查询门店失败");
        }

        return result;
    }

    @Override
    public ResponseResultPage<RecommendStoreDTO> footPrint(FootPrintRequest footPrintRequest) {

        ResponseResultPage<RecommendStoreDTO> responseResultPage = new ResponseResultPage<>();
        if (StringUtils.isEmpty(footPrintRequest.getMemberId())) {
            responseResultPage.setCode(0);
            responseResultPage.setMessage("输入参数错误");
        } else {

            PageData<MemberReqDetail> pageData = new PageData<>();

            pageData.setCurrent(footPrintRequest.getCurrent());
            pageData.setSize(footPrintRequest.getSize());
            MemberReqDetail memberReqDetail = new MemberReqDetail();
            memberReqDetail.setMemberId(footPrintRequest.getMemberId());
            pageData.setCondition(memberReqDetail);

            ResponseResultPage<String> resultPage = null;
            try {

                resultPage = memberService.queryStoreIdsBymId(pageData);
                List<String> orgSeqList = resultPage.getRecords();

                if (orgSeqList != null && orgSeqList.size() > 0) {
                    System.out.println("门店id集合orgSeqList>>>>" + orgSeqList);
                    //根据memberId到足迹表中查询店铺 orgSeqList 集合
                    List<RecommendStoreDTO> list = storeMapper.queryfootPrint(orgSeqList);
                    System.out.println("查询出来的店铺集合list为>>>" + list);
                    for (RecommendStoreDTO shop : list) {
                        Double distance = MathUtil.getDistance(Double.valueOf(footPrintRequest.getLng().doubleValue()),
                                Double.valueOf(footPrintRequest.getLat().doubleValue()),
                                Double.parseDouble(shop.getStoreLng()), Double.parseDouble(shop.getStoreLat()));

                        shop.setDistance(distance.intValue());
                    }
                    responseResultPage.setRecords(list);
                    responseResultPage.setCurrent(resultPage.getCurrent());
                    responseResultPage.setSize(resultPage.getSize());
                    responseResultPage.setTotal(resultPage.getTotal());
                } else {
                    responseResultPage.setCode(0);
                    responseResultPage.setMessage("您尚未浏览任何店铺");
                }

            } catch (Exception e) {
                e.printStackTrace();
                responseResultPage.setCode(0);
                responseResultPage.setMessage("获取门店id 为空");
            }
        }
        return responseResultPage;
    }

    @Override
    public ResponseResultPage<RecommendStoreDTO> searchStore(SearchStoreRequest searchStoreRequest) {

        ResponseResultPage<RecommendStoreDTO> responseResultPage = new ResponseResultPage<>();
        PageData<MemberBrowerStore> pageData = new PageData<>();
        pageData.setCurrent(searchStoreRequest.getCurrent());
        pageData.setSize(searchStoreRequest.getSize());

        MemberBrowerStore memberBrowerStore = new MemberBrowerStore();
        memberBrowerStore.setMemberId(searchStoreRequest.getMemberId());
        pageData.setCondition(memberBrowerStore);

        ResponseResultPage<String> resultPage = null;
        try {
            resultPage = memberService.queryStoreIdsByname(pageData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> orgSeqList = resultPage.getRecords();

        if (orgSeqList != null && orgSeqList.size() > 0) {
            List<RecommendStoreDTO> list = storeMapper.searchStore(orgSeqList);
            for (RecommendStoreDTO shop : list) {
                Double distance = MathUtil.getDistance(Double.valueOf(searchStoreRequest.getLng().doubleValue()),
                        Double.valueOf(searchStoreRequest.getLat().doubleValue()),
                        Double.parseDouble(shop.getStoreLng()), Double.parseDouble(shop.getStoreLat()));
                shop.setDistance(distance.intValue());
            }
            responseResultPage.setRecords(list);
            responseResultPage.setCurrent(resultPage.getCurrent());
            responseResultPage.setSize(resultPage.getSize());
            responseResultPage.setTotal(resultPage.getTotal());

        } else {
            responseResultPage.setCode(0);
            responseResultPage.setMessage("您尚未浏览任何店铺");
        }
        return responseResultPage;
    }
}
