package com.icss.newretail.api.user;

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
import com.icss.newretail.service.user.OrganizationService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestSchema(schemaId = "organization")
@RequestMapping(path = "/v1/organization")
public class OrganizationApi {
    @Autowired
    private OrganizationService organizationService;

    /**
     * 组织机构添加
     *
     * @param org
     * @return
     */
    @PostMapping(path = "createOrganization")
    public ResponseBase createOrganization(@RequestBody OrganizationDTO org) {
        return organizationService.createOrganization(org);
    }

    /**
     * 1. 组织编码不会修改 2. 上级组织编码不会修改 3. 组织类型可修改 4. 状态不会修改
     *
     * @param org
     * @return
     */
    @PutMapping(path = "modifyOrganization")
    public ResponseBase modifyOrganization(@RequestBody OrganizationDTO org) {
        return organizationService.modifyOrganization(org);
    }

    /**
     * 组织机构删除
     *
     * @param orgId
     * @return
     */
    @DeleteMapping(path = "deleteOrganization")
    public ResponseBase deleteOrganization(@RequestParam(name = "orgId") String orgId) {
        return organizationService.deleteOrganization(orgId);
    }

}
