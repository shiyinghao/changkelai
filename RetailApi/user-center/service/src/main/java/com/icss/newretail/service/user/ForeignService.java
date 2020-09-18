package com.icss.newretail.service.user;


import com.icss.newretail.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author yedaot
 * @date 2020/3/23 18:07
 */
public interface ForeignService {
	/**
	 * 
	 * 根据orgSeq查询组织关系
	 * @param orgSeq
	 * @return
	 */
	UserOrganizationRequest getOrganizationInfo(String orgSeq);
	
	
	/**
	 * 根据门店组织编码查询门店详情信息
	 */
	UserStoreInfoDTO getStoreInfo(String orgSeq);
	
	/**
	 * 根据id 等级id查询店铺详情
	 * @param foreignInStoreRequest
	 * @return
	 */
	List<UserStoreInfoDTO> getStoreInfoInIDLevel(ForeignInStoreRequest foreignInStoreRequest);




	/**
	 * 当前组织编码下门店组织编码
	 */
	List<OrganizationDTO> getStoreOrgInfo(String orgSeq, String orgSeqZQName, String orgSeqJDName, String MDorgSeqName, String authCode);


	/**
	 * 查询店铺详情 人员详情
	 * @param userId
	 * @param orgSeq
	 * @return
	 */
	UserAndStoreInfoRequest getUserAndStoreInfo(String userId, String orgSeq);


	/**
	 * 获取随机值
	 */
	String getIdByToday();

	/**
	 * 通过模板id查询模板
	 * @param
	 * @return
	 */

	ResponseBase getUmsDto(UmsParameter umsParameter);



	/**
	 * 查询店铺详情 人员详情
	 * @param authCode
	 * @param phone
	 * @return
	 */
	UserAndStoreInfoRequest getUserAndStoreByAuthCodeTel(String authCode, String phone);


	/**
	 * List<String>组织编码下门店组织编码
	 */
	List<OrganizationDTO> getStoreOrgInfoByList(List<String> orgSeqList);

	/**
	 * 生成门店组织机详情
	 *
	 * @return
	 */
	ResponseBase readExcel();


	/**
	 * 获取微信 token
	 */
	Map<String, Object> getAccessToken(String appID, String appScret);

	/**
	 * 通过参数 获取小程序码
	 *
	 * @param userId
	 * @param orgSeq
	 * @return
	 */

	ResponseResult getAppletShare(String userId, String orgSeq, String phones, Integer type);

	ResponseBase insertByAppletShare(String userId, String orgSeq, String url, String phone, Integer type);

	List<UserOrgRelationDTO> getAllOrgReletion();


	List<MemberMemberInfoDTO> getAllMemberMemberList(List<MemberMemberInfoDTO> lists);

	UserAndStoreInfoRequest getGoodsAndStoreByAuthCodeTel(String authCode, String goodSeq, String phone);

	TUserAppletContentDTO getAppletById(Integer uuid);

	Integer insertAppletById(String content);

	ResponseResultPage<UserUmsReponse> getUmsList(PageData<UserUmsReponse> pram);
}
