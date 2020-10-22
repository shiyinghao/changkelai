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

}
