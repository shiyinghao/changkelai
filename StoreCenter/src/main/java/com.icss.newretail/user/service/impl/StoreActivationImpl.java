package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.ActivationCountDTO;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.data.DataService;
import com.icss.newretail.service.data.StoreActivationService;
import com.icss.newretail.service.trade.MyStockService;
import com.icss.newretail.service.user.IStoreActivationService;
import com.icss.newretail.service.user.ReviewService;
import com.icss.newretail.user.dao.ShopLoginRecordMapper;
import com.icss.newretail.user.dao.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jc
 * @date 2020/7/28 17:35
 */
@Slf4j
@Service
public class StoreActivationImpl implements IStoreActivationService {

	@Autowired
	private ShopLoginRecordMapper shopLoginRecordMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@RpcReference(microserviceName = "user-service", schemaId = "review")
	private ReviewService reviewService;

	@RpcReference(microserviceName = "data-service", schemaId = "dataApi")
	private DataService dataService;

	@RpcReference(microserviceName = "data-service", schemaId = "StoreActivation")
	private StoreActivationService storeActivationService;

	@RpcReference(microserviceName = "trade-service", schemaId = "MyStockApi")
	private MyStockService myStockService;

	@Override
	public ResponseResult<ActivationCountDTO> queryActivationCount(String beginDate, String endDate) {
		ResponseResult<ActivationCountDTO> responseResult = new ResponseResult<>();
		try {
			ActivationCountDTO activationCountDTO = shopLoginRecordMapper.queryCount(beginDate, endDate);
//			UserStoreRequest userStoreInfo = new UserStoreRequest();
//			ArrayList<UserQryStoreDTO> records = userInfoMapper.qryStoreAmount(userStoreInfo);
//
//			ResponseRecords<TradeDataStoreGoodsDTO> responseRecords = dataService.queryDataStoreGoods(null);
//			List<TradeDataStoreGoodsDTO> orderList = responseRecords.getRecords();
//			Map<String, BigDecimal> mapOfOrderList = orderList.stream().collect(
//					Collectors.toMap(
//							e -> e.getOrgSeq(),
//							e -> e.getSaleCount(),
//							(e1, e2) -> e1, // Merge Function
//							TreeMap<String, BigDecimal>::new));
//
//			Integer lowerNum = 0, mediumNum = 0, upNum = 0;
//			QueryTradeBizStockReq qts = new QueryTradeBizStockReq();
//			ResponseRecords<TradeBizStockDTO> responseRecords1 = myStockService.queryStoreGoodsStockList(qts);
//			List<TradeBizStockDTO> tradeBizStockList = responseRecords1.getRecords();
//			Map<String, BigDecimal> mapOfTradeBizStockList = tradeBizStockList.stream().collect(
//					Collectors.toMap(
//							e -> e.getOrgSeq(),
//							e -> e.getAmount(),
//							(e1, e2) -> e1, // Merge Function
//							TreeMap<String, BigDecimal>::new));
//
//			for (int j = 0; j < records.size(); j++) {
//				UserQryStoreDTO dtoJ = records.get(j);
//				if (mapOfOrderList.getOrDefault(dtoJ.getOrgSeq(), BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0) {
//					upNum++;
//					continue;
//				}
//				if (mapOfTradeBizStockList.getOrDefault(dtoJ.getOrgSeq(), BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0) {
//					mediumNum++;
//					continue;
//				}
//				lowerNum++;
//			}
//			activationCountDTO.setLowStoreCount(lowerNum).setMiddleStoreCount(mediumNum).setHighStoreCount(upNum);

			ResponseResult<ActivationCountDTO> responseAcount = storeActivationService.queryStoreAcount();
			if(null != responseAcount) {
				ActivationCountDTO res = responseAcount.getResult();
				activationCountDTO.setLowStoreCount(res.getLowStoreCount()).setMiddleStoreCount(res.getMiddleStoreCount()).setHighStoreCount(res.getHighStoreCount());
			}
			responseResult.setResult(activationCountDTO).setCode(1).setMessage("成功");
		} catch (Exception e) {
			log.error("查询活跃度统计异常:{}", e.getMessage());
			responseResult.setCode(0).setMessage("系统异常");
		}
		return responseResult;
	}
}
