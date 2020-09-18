package com.icss.newretail.service.user;

import com.icss.newretail.model.AreaInfoDTO;
import com.icss.newretail.model.OrganizationChildDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.OrganizationInfoDTO;
import com.icss.newretail.model.OrganizationRequest;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.StoreFunctionDTO;
import com.icss.newretail.model.StoreInfoDTO;
import com.icss.newretail.model.StoreInfoRequest;
import com.icss.newretail.model.StoreMessage;
import com.icss.newretail.model.StoreMessageRequest;
import com.icss.newretail.model.StoreParamDTO;
import com.icss.newretail.model.StoreRequest;
import com.icss.newretail.model.StoreTypeDTO;
import com.icss.newretail.model.StoreTypeRequest;
import com.icss.newretail.model.UserStoreInfoDTO;
import com.icss.newretail.model.UserStoreMessageCountDTO;
import com.icss.newretail.model.UserStoreMessageDTO;

import java.util.List;

public interface OrganizationService {

  public ResponseBase createOrganization(OrganizationDTO org);

  /**
   * 1. 组织编码不会修改 2. 上级组织编码不会修改 3. 组织类型可修改 4. 状态不会修改
   *
   * @param org
   * @return
   */
  public ResponseBase modifyOrganization(OrganizationDTO org);

  public ResponseBase deleteOrganization(String orgId);

  public ResponseBase disableOrganization(String orgId);

  public ResponseBase enableOrganization(String orgId);

  /**
   * 组织机构信息查询
   *
   * @param para
   * @return
   */
  public ResponseResultPage<OrganizationDTO> queryOrganizations(PageData<OrganizationRequest> para);

  /**
   * 添加门店
   *
   * @param store
   * @return
   */
  public ResponseBase createStore(StoreInfoDTO store);

  /**
   * 修改门店
   *
   * @param store
   * @return
   */
  public ResponseBase modifyStore(StoreInfoDTO store);

  /**
   * 删除门店
   *
   * @param storeId
   * @return
   */
  public ResponseBase deleteStore(String storeId);

  /**
   * 门店类型查询
   *
   * @param para
   * @return
   */
  public ResponseRecords<StoreTypeDTO> queryStoreTypes(StoreTypeRequest para);

  /**
   * 门店类型添加
   *
   * @param storeType
   * @return
   */
  public ResponseBase createStoreType(StoreTypeDTO storeType);

  /**
   * 门店类型修改
   *
   * @param storeType
   * @return
   */
  public ResponseBase modifyStoreType(StoreTypeDTO storeType);

  /**
   * 门店类型删除
   *
   * @param storeType
   * @return
   */
  public ResponseBase deleteStoreType(String storeType);

  /**
   * 门店参数更新
   *
   * @param params
   * @return
   */
  public ResponseBase saveStoreParams(List<StoreParamDTO> params);

  /**
   * 根据门店ID获取门店信息
   *
   * @param storeMessage
   * @return
   */

  /**
   * 根据门店ID获取门店参数
   *
   * @param storeId
   * @return List<StoreParamDTO>
   */
  public ResponseRecords<StoreParamDTO> queryStoreParams(String storeId);

  /**
   * 根据组织ID、终端ID获取终端菜单列表
   *
   * @param orgId
   * @param orgId
   * @return List<StoreFunctionDTO>
   */
  public ResponseRecords<StoreFunctionDTO> queryStoreFunctions(String orgId);

  /**
   * 根据地理位置查询门店(可选择优先方式、返回数量)
   *
   * @param storeRequest
   * @return
   */
  public ResponseRecords<OrganizationInfoDTO> queryStores(StoreRequest storeRequest);

  /**
   * 查询行政区划信息（根据ID）
   *
   * @param areaId
   * @return
   */
  public ResponseResult<AreaInfoDTO> queryAreaInfoById(String areaId);

  /**
   * 修改行政区划信息
   *
   * @param areaInfoDTO
   * @return
   */
  public ResponseBase modifyAreaInfo(AreaInfoDTO areaInfoDTO);

  /**
   * 添加行政区划信息（禁止行政区划编码重复）
   *
   * @param areaInfoDTO
   * @return
   */
  public ResponseBase addAreaInfo(AreaInfoDTO areaInfoDTO);

  /**
   * 行政区划查询
   *
   * @param areaRequest
   * @return
   */
  public ResponseRecords<AreaInfoDTO> queryAreaInfos(AreaInfoRequest areaRequest);

  public ResponseBase deleteStoreParam(String paramId);

  /**
   * 按照行政区划统计门店数量 group by areaSeq having status=
   *
   * @param areaSeq
   * @param status
   * @return
   */
  public ResponseRecords<AreaInfoDTO> countStoreByArea(String areaSeq, String status);

  /**
   * 统计消息数量
   *
   * @param storeId
   * @return
   */
  public ResponseRecords<UserStoreMessageCountDTO> countMessages(String storeId);

  /**
   * 查询终端消息
   *
   * @param pageData
   * @return
   */
  public ResponseResultPage<UserStoreMessageDTO> receiveMessages(
      PageData<StoreMessageRequest> pageData);

  /**
   * 终端消息发送 1.保存消息 2.推送消息（PushMessageServiceImpl.pushAccountList）
   *
   * @param messages
   * @return
   */
  public ResponseBase sendMessages(List<String> deviceIds, UserStoreMessageDTO mess);

  /**
   * 终端消息读取
   *
   * @param messageId
   * @return
   */
  public ResponseBase readMessage(String messageId);

  public ResponseResultPage<OrganizationInfoDTO> queryStoreByAreaSeq(
      PageData<StoreRequest> pageData);

  /**
   * 获取推荐店铺（有推荐标识的最近店铺，只返回一个）
   *
   * @param lng
   * @param lat
   * @return
   */
  public ResponseRecords<OrganizationInfoDTO> getRecoStore(String lng, String lat);

  public ResponseResultPage<StoreInfoDTO> queryStoreInfo(PageData<StoreInfoRequest> pageData);

  /**
   * 获取下级所有连锁门店--根据orgSeq
   *
   * @param orgSeq
   * @return
   */
  public ResponseRecords<StoreInfoDTO> getStoresByUpOrgSeq(String orgSeq);

  /**
   * 根据门店ID获取门店信息
   *
   * @param orgSeq
   * @return
   */
  public ResponseResult<StoreInfoDTO> getStoreInfo(String orgSeq);

  ResponseRecords<StoreInfoDTO> getStoresByUserId();

  ResponseRecords<OrganizationDTO> getOrgTree();

  ResponseResultPage<UserStoreInfoDTO> queryStored(PageData<StoreMessage> storeMessage);

  ResponseResult<OrganizationDTO> queryTopById(String orgSeq);

  ResponseRecords<UserStoreInfoDTO> queryStoreByUUId(StoreMessage storeMessage);

  ResponseResult<StoreInfoDTO> queryStoreNameById(String orgSeq);

  ResponseRecords<OrganizationChildDTO> queryOrganizationById(String orgSeq);

  ResponseRecords<OrganizationChildDTO> queryOrgChildById(String orgSeq);

  ResponseRecords<OrganizationDTO> getActiveOrgTree(String orgSeq);

  ResponseResult<OrganizationChildDTO> queryZqOrgSeq(String orgSeq);

  ResponseResult<UserStoreInfoDTO> queryStoreById(String orgSeq);

  ResponseRecords<OrganizationDTO> getOrgTreeQuery(String upOrgSeq);

  Integer getStoreArea(String orgSeq);

  ResponseResultPage<UserStoreInfoDTO> queryOftenStores(PageData<StoreMessage> storeMessage);
}
