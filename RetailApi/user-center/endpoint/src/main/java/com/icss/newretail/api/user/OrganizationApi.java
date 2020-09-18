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
import com.icss.newretail.service.user.AreaInfoRequest;
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
     * 消费者最近推荐门店查询（根据坐标位置查询门店列表）
     *
     * @param storeMessage
     * @return
     */
    @PostMapping(path = "queryStored")
    public ResponseResultPage<UserStoreInfoDTO> queryStored(
            @RequestBody PageData<StoreMessage> storeMessage) {
        return organizationService.queryStored(storeMessage);
    }

    /**
     * 常用专卖店门店查询（根据坐标位置查询门店列表）
     *
     * @param storeMessage
     * @return
     */
    @PostMapping(path = "queryOftenStores")
    public ResponseResultPage<UserStoreInfoDTO> queryOftenStores(
            @RequestBody PageData<StoreMessage> storeMessage) {
        return organizationService.queryOftenStores(storeMessage);
    }

    /**
     * 门店信息查询（根据名称模糊查询）
     *
     * @param storeMessage
     * @return
     */
    @PostMapping(path = "queryStoreByUUId")
    public ResponseRecords<UserStoreInfoDTO> queryStoreByUUId(
            @RequestBody StoreMessage storeMessage) {
        return organizationService.queryStoreByUUId(storeMessage);
    }

    /**
     * 根据orgSeq查出战区下的门店，舍弃基地
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "queryOrganizationById")
    public ResponseRecords<OrganizationChildDTO> queryOrganizationById(
            @ApiParam(name = "orgSeq", value = "组织编码", required = true) String orgSeq) {
        ResponseRecords<OrganizationChildDTO> result = new ResponseRecords<>();
        if (StringUtils.isBlank(orgSeq)) {
            result.setCode(0);
            result.setMessage("传入的参数不能为空");
            return result;
        }
        return organizationService.queryOrganizationById(orgSeq);
    }

    /**
     * 根据orgSeq(总部/门店/战区)查出门店
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "queryOrgChildById")
    public ResponseRecords<OrganizationChildDTO> queryOrgChildById(
            @ApiParam(name = "orgSeq", value = "组织编码", required = true) String orgSeq) {
        ResponseRecords<OrganizationChildDTO> result = new ResponseRecords<>();
        if (StringUtils.isBlank(orgSeq)) {
            result.setCode(0);
            result.setMessage("传入的参数不能为空");
            return result;
        }
        return organizationService.queryOrgChildById(orgSeq);
    }

    /**
     * 根据门店orgseq查出战区zqOrgSeq
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "queryZqOrgSeq")
    public ResponseResult<OrganizationChildDTO> queryZqOrgSeq(@RequestParam() String orgSeq) {
        return organizationService.queryZqOrgSeq(orgSeq);
    }

    /**
     * 根据门店orgseq查出门店信息
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "queryStoreById")
    public ResponseResult<UserStoreInfoDTO> queryStoreById(@RequestParam() String orgSeq) {
        return organizationService.queryStoreById(orgSeq);
    }

    /**
     * 门店根据orgseq查询名称
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "queryStoreNameById")
    public ResponseResult<StoreInfoDTO> queryStoreNameById(@RequestParam() String orgSeq) {
        return organizationService.queryStoreNameById(orgSeq);
    }

    /**
     * 组织机构查询 根据商店的orgseq查询上级编码，查到战区，关联上商品表
     *
     * @param OrgSeq
     * @return
     */
    @PatchMapping(path = "queryTopById")
    public ResponseResult<OrganizationDTO> queryTopById(
            @RequestParam(name = "OrgSeq", value = "组织编码", required = true) String OrgSeq) {
        return organizationService.queryTopById(OrgSeq);
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

    /**
     * 组织机构禁用
     *
     * @param orgId
     * @return
     */
    @PatchMapping(path = "disableOrganization")
    public ResponseBase disableOrganization(@RequestParam(name = "orgId") String orgId) {
        return organizationService.disableOrganization(orgId);
    }

    /**
     * 组织机构启用
     *
     * @param orgId
     * @return
     */
    @PatchMapping(path = "enableOrganization")
    public ResponseBase enableOrganization(@RequestParam(name = "orgId") String orgId) {
        return organizationService.enableOrganization(orgId);
    }

    /**
     * 组织机构信息查询
     *
     * @param para
     * @return
     */
    @PostMapping(path = "queryOrganizations")
    public ResponseResultPage<OrganizationDTO> queryOrganizations(
            @RequestBody PageData<OrganizationRequest> para) {
        ResponseResultPage<OrganizationDTO> result = new ResponseResultPage<>();
        if (para == null || para.getCondition() == null) {
            result.setCode(0);
            result.setMessage("传入的查询分页对象或查询条件不能为空");
            return result;
        } else {
            return organizationService.queryOrganizations(para);
        }
    }

    /**
     * 新增门店
     *
     * @param store
     * @return
     */
    @PostMapping(path = "createStore")
    public ResponseBase createStore(@RequestBody StoreInfoDTO store) {
        return organizationService.createStore(store);
    }

    /**
     * 店主修改门店
     *
     * @param store
     * @return
     */
    @PutMapping(path = "modifyStore")
    public ResponseBase modifyStore(@RequestBody StoreInfoDTO store) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(store.getUuid())) {
            responseBase.setCode(0);
            responseBase.setMessage("门店的ID不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(store.getOrgSeq())) {
            responseBase.setCode(0);
            responseBase.setMessage("门店的组织编码orgSeq不能为空");
            return responseBase;
        }
        return organizationService.modifyStore(store);
    }

    /**
     * 门店信息删除（同时删除组织机构信息） 修改状态为删除-1
     *
     * @param storeId
     * @return
     */
    @DeleteMapping(path = "deleteStore")
    public ResponseBase deleteStore(
            @NotEmpty(message = "参数storeId不能为空")
            @ApiParam(name = "storeId", value = "门店id", required = true)
                    String storeId) {
        return organizationService.deleteStore(storeId);
    }

    /**
     * 门店类型查询
     *
     * @param para
     * @return
     */
    @PostMapping(path = "queryStoreTypes")
    public ResponseRecords<StoreTypeDTO> queryStoreTypes(@RequestBody StoreTypeRequest para) {
        return organizationService.queryStoreTypes(para);
    }

    /**
     * 门店类型添加
     *
     * @param storeType
     * @return
     */
    @PostMapping(path = "createStoreType")
    public ResponseBase createStoreType(@RequestBody StoreTypeDTO storeType) {
        return organizationService.createStoreType(storeType);
    }

    /**
     * 门店类型修改
     *
     * @param storeType
     * @return
     */
    @PutMapping(path = "modifyStoreType")
    public ResponseBase modifyStoreType(@RequestBody StoreTypeDTO storeType) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(storeType.getStoreType())) {
            responseBase.setCode(0);
            responseBase.setMessage("门店类型storeType不能为空");
            return responseBase;
        }
        return organizationService.modifyStoreType(storeType);
    }

    /**
     * 门店类型删除
     *
     * @param storeType
     * @return
     */
    @DeleteMapping(path = "deleteStoreType")
    public ResponseBase deleteStoreType(
            @NotEmpty(message = "参数storeType不能为空")
            @ApiParam(name = "storeType", value = "门店类型", required = true)
                    String storeType) {
        return organizationService.deleteStoreType(storeType);
    }

    /**
     * 门店查询（根据坐标位置查询门店列表）
     *
     * @param storeRequest
     * @return
     */
    @PostMapping(path = "queryStores")
    public ResponseRecords<OrganizationInfoDTO> queryStores(@RequestBody StoreRequest storeRequest) {
        return organizationService.queryStores(storeRequest);
    }

    /**
     * 获取推荐店铺
     *
     * @param lng
     * @param lat
     * @return
     */
    @GetMapping(path = "getRecoStore")
    public ResponseRecords<OrganizationInfoDTO> getRecoStore(
            @RequestParam(name = "lng") String lng, @RequestParam(name = "lat") String lat) {
        return organizationService.getRecoStore(lng, lat);
    }

    /**
     * 统计门店数量（根据门店所属行政区划） 根据行政区划获取下一级行政区划列表及门店数量 按照所属行政区划统计门店并根据门店经营状态过滤数据
     *
     * @param areaSeq
     * @return
     */
    @GetMapping(path = "countStoreByArea")
    public ResponseRecords<AreaInfoDTO> countStoreByArea(
            @NotEmpty(message = "参数areaSeq不能为空")
            @ApiParam(name = "areaSeq", value = "区域编码", required = true)
                    String areaSeq,
            @NotEmpty(message = "参数status不能为空")
            @ApiParam(name = "status", value = "经营状态", required = true)
                    String status) {
        ResponseRecords<AreaInfoDTO> responseBase = new ResponseRecords<AreaInfoDTO>();
        if (StringUtils.isBlank(areaSeq)) {
            responseBase.setCode(0);
            responseBase.setMessage("区域编码不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(status)) {
            responseBase.setCode(0);
            responseBase.setMessage("店经营状态不能为空");
            return responseBase;
        }
        return organizationService.countStoreByArea(areaSeq, status);
    }

    /**
     * 批量更新终端/门店配置参数
     *
     * @param params
     * @return
     */
    @PutMapping(path = "saveStoreParams")
    public ResponseBase saveStoreParams(@RequestBody List<StoreParamDTO> params) {
        return organizationService.saveStoreParams(params);
    }

    /**
     * 删除终端/门店配置参数
     *
     * @param paramId
     * @return
     */
    @DeleteMapping(path = "deleteStoreParam")
    public ResponseBase deleteStoreParam(
            @NotEmpty(message = "参数paramId不能为空")
            @ApiParam(name = "paramId", value = "参数id", required = true)
                    String paramId) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(paramId)) {
            responseBase.setCode(0);
            responseBase.setMessage("参数paramId不能为空");
            return responseBase;
        }
        return organizationService.deleteStoreParam(paramId);
    }

    /**
     * 门店参数查询(根据门店ID)
     *
     * @param storeId
     * @return
     */
    @GetMapping(path = "queryStoreParams")
    public ResponseRecords<StoreParamDTO> queryStoreParams(
            @NotEmpty(message = "参数storeId不能为空")
            @ApiParam(name = "storeId", value = "门店id", required = true)
                    String storeId) {
        return organizationService.queryStoreParams(storeId);
    }

    /**
     * 门店资源配置查询
     *
     * @param orgId
     * @return
     */
    @GetMapping(path = "queryStoreFunctions")
    public ResponseRecords<StoreFunctionDTO> queryStoreFunctions(
            @RequestParam(name = "orgId") String orgId) {
        return organizationService.queryStoreFunctions(orgId);
    }

    /**
     * 添加行政区划信息
     *
     * @param areaInfoDTO
     * @return
     */
    @PostMapping(path = "addAreaInfo")
    public ResponseBase addAreaInfo(@RequestBody AreaInfoDTO areaInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(areaInfoDTO.getAreaName())) {
            responseBase.setCode(0);
            responseBase.setMessage("区域名称不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(areaInfoDTO.getAreaSeq())) {
            responseBase.setCode(0);
            responseBase.setMessage("区域编码不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(areaInfoDTO.getUpAreaSeq())) {
            responseBase.setCode(0);
            responseBase.setMessage("上级区域编码不能为空");
            return responseBase;
        }
        return organizationService.addAreaInfo(areaInfoDTO);
    }

    /**
     * 修改行政区划信息
     *
     * @param areaInfoDTO
     * @return
     */
    @PutMapping(path = "modifyAreaInfo")
    public ResponseBase modifyAreaInfo(@RequestBody AreaInfoDTO areaInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(areaInfoDTO.getAreaId())) {
            responseBase.setCode(0);
            responseBase.setMessage("区域ID不能为空");
            return responseBase;
        }
        return organizationService.modifyAreaInfo(areaInfoDTO);
    }

    /**
     * 查看行政区划明细
     *
     * @param areaId
     * @return
     */
    @GetMapping(path = "queryAreaInfoById")
    public ResponseResult<AreaInfoDTO> queryAreaInfoById(
            @NotEmpty(message = "参数areaId不能为空")
            @ApiParam(name = "areaId", value = "区域id", required = true)
                    String areaId) {
        return organizationService.queryAreaInfoById(areaId);
    }

    /**
     * 查询行政区划列表
     *
     * @param para
     * @return
     */
    @PostMapping(path = "queryAreaInfos")
    public ResponseRecords<AreaInfoDTO> queryAreaInfos(@RequestBody AreaInfoRequest para) {
        return organizationService.queryAreaInfos(para);
    }

    /**
     * 终端消息数量查询（按照消息类型、读取状态汇总）
     *
     * @param storeId
     * @return
     */
    @GetMapping(path = "countMessages")
    public ResponseRecords<UserStoreMessageCountDTO> countMessages(
            @RequestParam(name = "storeId") String storeId) {
        return organizationService.countMessages(storeId);
    }

    /**
     * 终端消息查询（根据门店ID查询系统发送给终端的消息列表）
     *
     * @param pageData
     * @return
     */
    @PostMapping(path = "receiveMessages")
    public ResponseResultPage<UserStoreMessageDTO> receiveMessages(
            @RequestBody PageData<StoreMessageRequest> pageData) {
        return organizationService.receiveMessages(pageData);
    }

    /**
     * 终端消息发送（向终端发送消息并记录发送信息）
     *
     * @param mess
     * @return
     */
    @PostMapping(path = "sendMessages")
    public ResponseBase sendMessages(
            @RequestParam List<String> deviceIds, @RequestBody UserStoreMessageDTO mess) {
        return organizationService.sendMessages(deviceIds, mess);
    }

    /**
     * 终端消息读取（将消息标记为已读取状态并记录读取时间）
     *
     * @param messageId
     * @return
     */
    @GetMapping(path = "readMessage")
    public ResponseBase readMessage(@RequestParam(name = "messageId") String messageId) {
        return organizationService.readMessage(messageId);
    }

    /**
     * 根据区域编码获取门店信息
     *
     * @param pageData
     * @return
     */
    @PostMapping("queryStoreByAreaSeq")
    public ResponseResultPage<OrganizationInfoDTO> queryStoreByAreaSeq(
            @RequestBody PageData<StoreRequest> pageData) {
        return organizationService.queryStoreByAreaSeq(pageData);
    }

    /**
     * 查询门店信息,关联当前方案、当前卡券下各个门店的卡券数量（根据名称，门店等级模糊查询）
     *
     * @return
     */
    @PostMapping("queryStoreInfo")
    public ResponseResultPage<StoreInfoDTO> queryStoreInfo(
            @RequestBody PageData<StoreInfoRequest> pageData) {
        return organizationService.queryStoreInfo(pageData);
    }

    /**
     * 获取下级所有连锁门店--根据orgSeq
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "getStoresByUpOrgSeq")
    public ResponseRecords<StoreInfoDTO> getStoresByUpOrgSeq(
            @NotEmpty(message = "参数orgSeq不能为空")
            @ApiParam(name = "orgSeq", value = "区域id", required = true)
                    String orgSeq) {
        return organizationService.getStoresByUpOrgSeq(orgSeq);
    }

    /**
     * 获取当前用户所有门店
     *
     * @return
     */
    @PostMapping(path = "getStoresByUserId")
    public ResponseRecords<StoreInfoDTO> getStoresByUserId() {
        return organizationService.getStoresByUserId();
    }

    /**
     * 获取组织机构树
     *
     * @return
     */
    @GetMapping(path = "getOrgTree")
    public ResponseRecords<OrganizationDTO> getOrgTree() {
        return organizationService.getOrgTree();
    }

    /**
     * 获取组织机构树 分布查询 --上级组织id--
     *
     * @return
     */
    @GetMapping(path = "getOrgTreeQuery")
    public ResponseRecords<OrganizationDTO> getOrgTreeQuery(
            @ApiParam(name = "upOrgSeq", value = "上级组织") String upOrgSeq) {
        return organizationService.getOrgTreeQuery(upOrgSeq);
    }

    /**
     * 动态获取组织树 @Author wangyao
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "getActiveOrgTree")
    public ResponseRecords<OrganizationDTO> getActiveOrgTree(
            @RequestParam(value = "orgSeq", required = true) String orgSeq) {
        return organizationService.getActiveOrgTree(orgSeq);
    }

    /**
     * 获取该门店经营面积
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "getStoreArea")
    public Integer getStoreArea(String orgSeq) {
        return organizationService.getStoreArea(orgSeq);
    }
}
