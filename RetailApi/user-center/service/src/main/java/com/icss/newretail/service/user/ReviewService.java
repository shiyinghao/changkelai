package com.icss.newretail.service.user;

import com.icss.newretail.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author jc
 * @date 2020/3/23 18:07
 */
public interface ReviewService {

	ResponseResult<LoginUserDTO> shopAuth(String authCode);

	/**
	 * 店铺申请
	 *
	 * @param userReviewInfoDTO
	 * @return
	 */
	ResponseBase applyShop(UserReviewInfoDTO userReviewInfoDTO);

	/**
	 * 根据类型查询商户店铺申请信息
	 *
	 * @param pageData
	 * @return
	 */
	ResponseResultPage<UserReviewInfoDTO> queryUserReviewInfo(PageData<UserReviewRequest> pageData);

	/**
	 * 查询店铺申请信息
	 *
	 * @param uuId
	 * @return
	 */
	ResponseResult queryReviewInfo(String uuId);

	ResponseBase preliminaryExamination(PreliminaryExamination preliminaryExamination);

	/**
	 * 生成店铺信息
	 *
	 * @param preliminaryExamination
	 * @return
	 */
	ResponseBase finalReview(PreliminaryExamination preliminaryExamination);

	/**
	 * 系统管理员新增编辑门店
	 */

	ResponseBase addStore(UserStoreInfoDTO userStoreInfoDTO);

	/**
	 * 编辑店员信息
	 */

	ResponseBase editClerk(UserClerkDTO userclerk);

	/**
	 * 编辑巡店店员信息
	 */

	ResponseBase editStoreClerk(UserClerkDTO userclerk);

	/**
	 * 查询店员信息
	 */

	ResponseResultPage<UserInfoDTO> qryClerk(PageData<UserClerkRequest> userclerkPage);

	/**
	 * 查询店员信息详情
	 */

	ResponseResult<UserInfoDTO> qryClerkUserInfo(String uuid);

	/**
	 * 查询店铺信息
	 */
	ResponseResultPage<UserQryStoreDTO> qryStore(PageData<UserStoreRequest> userStoreInfo);

	/**
	 * 店铺强关开
	 *
	 * @param uuid
	 * @param openStatus
	 * @return
	 */
	ResponseBase storeAdministration(String uuid, Integer openStatus);

	/**
	 * 战区查询接口
	 *
	 * @return
	 */
	Map<String, Object> getAllZone();

	ResponseRecords getAllZoneList();
	/**
	 * 基地查询接口
	 *
	 * @return
	 */
	Map<String, Object> getAllBase(String code);


	/**
	 * 查询店铺信息详情
	 */
	ResponseResult<UserStoreInfoDTO> queryUserStoreInfo(String uuid);


	/**
	 * 基地业务员查询接口
	 *
	 * @return
	 */
	Map<String, Object> getBaseUserInfo(String orgSeq);

	/**
	 * 基于用户查看流程信息以及店铺申请信息
	 *
	 * @param orgSeq
	 * @return
	 */
	ResponseResult<UserReviewInfoDTO> getStoreByAuthCode(String orgSeq);

	/**
	 * 店铺编辑接口
	 */
	ResponseBase editStore(UserReviewInfoDTO userReviewInfoDTO);

	/**
	 * 店铺初审
	 *
	 * @param preliminaryExamination
	 * @return
	 */
	ResponseBase shopPreliminary(PreliminaryExamination preliminaryExamination);

	/**
	 * 店铺复审
	 *
	 * @param preliminaryExamination
	 * @return
	 */
	ResponseBase storeFinalReview(PreliminaryExamination preliminaryExamination);

	/**
	 * 店长店主电话验证
	 * 1：店长电话保证唯一
	 * 2：店主电话只允许存在在店主等级新增
	 *
	 * @param phone
	 * @param type
	 * @return
	 */

	ResponseBase phoneVerification(String phone, Integer type);


	/**
	 * 根据reviewid 查询编辑申请详情
	 *
	 * @param reviewId
	 * @return
	 */
	ResponseResult<UserReviewInfoDTO> getReviewId(String reviewId);

	ResponseResultPage<StoreUserInfoDTO> queryStoreUserInfo(PageData<StoreUserInfoDTO> dto);


	/**
	 * 查询店铺申请工行模板
	 */
	ResponseResult<List<UserIcbcTempDTO>> queryUserIcbcTemp(Integer type);


	/**
	 * 门店申请信息保存
	 */
	ResponseBase saveUserReviewInfo(UserReviewInfoDTO userReviewInfoDTO);

	ResponseBase edidUserIcbcTemp(UserIcbcTempDTO UserIcbcTempDTO);

	ResponseBase jumpUserReviewInfo(IcbcAnswerRequest icbcAnswerRequest);

	ResponseBase paymentInformation(String uuid, Integer isSwith);

	Map<String, Object> getAllShop(String code);

	ResponseBase finalReview2(PreliminaryExamination examination);

	ResponseBase addAuhCode(AddAuthCode addAuthCode);

	ResponseBase addAccount(StoreAccountDTO dto);

	ResponseResultPage<UserStoreVerifyDTO> getAllAuthCode(PageData<AddAuthCode> pageData);

	ResponseBase deleteLoginAuthCode(String uuid);

	ResponseRecords<UserQryStoreDTO> qryStoreAmount(UserStoreRequest userStoreInfo);

	ResponseBase deleteClerk(String uuid);

	ResponseBase resetPwd(String authCode);
}
