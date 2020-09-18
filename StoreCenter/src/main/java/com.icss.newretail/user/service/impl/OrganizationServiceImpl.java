package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.AreaInfoDTO;
import com.icss.newretail.model.MemberReqDetail;
import com.icss.newretail.model.MessageCustom;
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
import com.icss.newretail.service.member.MemberService;
import com.icss.newretail.service.user.AreaInfoRequest;
import com.icss.newretail.service.user.OrganizationService;
import com.icss.newretail.user.dao.OrganizationInfoMapper;
import com.icss.newretail.user.dao.OrganizationMapper;
import com.icss.newretail.user.dao.StoreMapper;
import com.icss.newretail.user.dao.StoreMessageMapper;
import com.icss.newretail.user.dao.StoreParamMapper;
import com.icss.newretail.user.dao.StoreTypeMapper;
import com.icss.newretail.user.dao.UserAreaMapper;
import com.icss.newretail.user.dao.UserGroupInfoMapper;
import com.icss.newretail.user.dao.UserWarZoneInfoMapper;
import com.icss.newretail.user.entity.UserArea;
import com.icss.newretail.user.entity.UserOrganization;
import com.icss.newretail.user.entity.UserStoreInfo;
import com.icss.newretail.user.entity.UserStoreMessage;
import com.icss.newretail.user.entity.UserStoreParam;
import com.icss.newretail.user.entity.UserStoreType;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.MathUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @RpcReference(microserviceName = "member-service", schemaId = "MemberApi")
    private MemberService memberService;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserGroupInfoMapper userGroupInfoMapper;

    @Autowired
    private UserWarZoneInfoMapper userWarZoneInfoMapper;

    @Autowired
    private UserAreaMapper userAreaMapper;

    @Autowired
    private StoreParamMapper storeParamMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreMessageMapper storeMessageMapper;

    @Autowired
    private OrganizationInfoMapper organizationInfoMapper;

    @Autowired
    private StoreTypeMapper storeTypeMapper;

    @Autowired
    private OrganizationService organizationService;

    /**
     * 组织机构添加
     *
     * @param org
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseBase createOrganization(OrganizationDTO org) {
        ResponseBase base = new ResponseBase();
        try {
            String userId = JwtTokenUtil.currUser();
            UserOrganization userOrganization =
                    Object2ObjectUtil.parseObject(org, UserOrganization.class);
            userOrganization.setOrgSeq(UUID.randomUUID().toString());
            userOrganization.setStatus(1);
            userOrganization.setCreateUser(userId);
            userOrganization.setUpdateUser(userId);
            userOrganization.setCreateTime(LocalDateTime.now());
            userOrganization.setUpdateTime(LocalDateTime.now());
            organizationMapper.insert(userOrganization);
            //            if ("1".equals(userOrganization.getOrgType())) {
            //                // 集团
            //                UserGroupInfo groupInfo = new UserGroupInfo();
            //                groupInfo.setOrgSeq(userOrganization.getOrgSeq());
            //                groupInfo.setStatus(1);
            //                groupInfo.setName(userOrganization.getOrgName());
            //                groupInfo.setCreateUser(userId);
            //                groupInfo.setUpdateUser(userId);
            //                groupInfo.setCreateTime(LocalDateTime.now());
            //                groupInfo.setUpdateTime(LocalDateTime.now());
            //                userGroupInfoMapper.insert(groupInfo);
            //            } else if ("2".equals(userOrganization.getOrgType())) {
            //                //战区
            //                UserWarZoneInfo userWarZoneInfo = new UserWarZoneInfo();
            //                userWarZoneInfo.setOrgSeq(userOrganization.getOrgSeq());
            //                userWarZoneInfo.setStatus(1);
            //                userWarZoneInfo.setName(userOrganization.getOrgName());
            //                userWarZoneInfo.setCreateUser(userId);
            //                userWarZoneInfo.setUpdateUser(userId);
            //                userWarZoneInfo.setCreateTime(LocalDateTime.now());
            //                userWarZoneInfo.setUpdateTime(LocalDateTime.now());
            //                userWarZoneInfoMapper.insert(userWarZoneInfo);
            //            } else if ("4".equals(userOrganization.getOrgType())) {
            //                //门店
            //                UserStoreInfo userStoreInfo = new UserStoreInfo();
            //                userStoreInfo.setUuid(UUID.randomUUID().toString());
            //                userStoreInfo.setOrgSeq(userOrganization.getOrgSeq());
            //                userStoreInfo.setStoreName(userOrganization.getOrgName());
            //                userStoreInfo.setStatus(1);
            //                userStoreInfo.setCreateUser(userId);
            //                userStoreInfo.setUpdateUser(userId);
            //                userStoreInfo.setCreateTime(LocalDateTime.now());
            //                userStoreInfo.setUpdateTime(LocalDateTime.now());
            //                storeMapper.insert(userStoreInfo);
            //            }
            base.setCode(1);
            base.setMessage("组织机构创建完成");
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|createOrganization->组织机构添加[" + ex.getMessage() + "]");
        }
        return base;
    }

    /**
     * 1. 组织编码不会修改 2. 上级组织编码不会修改 3. 组织类型可修改 4. 状态不会修改
     *
     * @param org
     * @return
     */
    @Override
    public ResponseBase modifyOrganization(OrganizationDTO org) {
        ResponseBase base = new ResponseBase();
        try {
            if (org != null && StringUtils.isNotBlank(org.getOrgSeq())) {
                UserOrganization userOrganization =
                        Object2ObjectUtil.parseObject(org, UserOrganization.class);
                userOrganization.setUpdateUser(JwtTokenUtil.currUser());
                int i = organizationMapper.updateById(userOrganization);
                base.setCode(0);
                base.setMessage("组织机构修改完成");
            } else {
                base.setCode(0);
                base.setMessage("传入参数异常");
                return base;
            }
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|modifyOrganization->组织机构修改" + ex.getMessage());
            base.setCode(0);
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    /**
     * 组织机构删除
     *
     * @param orgId
     * @return
     */
    @Override
    public ResponseBase deleteOrganization(String orgId) {
        ResponseBase base = new ResponseBase();
        try {
            if (StringUtils.isNotBlank(orgId)) {
                Integer i = organizationMapper.deleteById(orgId);
                base.setCode(0);
                base.setMessage("组织结构删除完成");
            } else {
                base.setMessage("传入参数有误");
                base.setCode(0);
                return base;
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|deleteOrganization->组织结构删除" + ex.getMessage());
        }
        return base;
    }

    /**
     * 组织机构禁用
     *
     * @param orgId
     * @return
     */
    @Override
    public ResponseBase disableOrganization(String orgId) {
        ResponseBase base = new ResponseBase();
        try {
            if (StringUtils.isNotBlank(orgId)) {
                UserOrganization userOrganization = new UserOrganization();
                userOrganization.setOrgSeq(orgId);
                userOrganization.setStatus(0);
                userOrganization.setUpdateUser(JwtTokenUtil.currUser());
                userOrganization.setUpdateTime(LocalDateTime.now());
                int i = organizationMapper.updateById(userOrganization);
                base.setCode(0);
                base.setMessage("组织机构禁用完成");
            } else {
                base.setMessage("传入参数有误");
                base.setCode(0);
                return base;
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|disableOrganization->组织机构禁用" + ex.getMessage());
        }
        return base;
    }

    /**
     * 组织机构启用
     *
     * @param orgId
     * @return
     */
    @Override
    public ResponseBase enableOrganization(String orgId) {
        ResponseBase base = new ResponseBase();
        try {
            if (StringUtils.isNotBlank(orgId)) {
                UserOrganization userOrganization = new UserOrganization();
                userOrganization.setOrgSeq(orgId);
                userOrganization.setStatus(1);
                userOrganization.setUpdateUser(JwtTokenUtil.currUser());
                userOrganization.setUpdateTime(LocalDateTime.now());
                int i = organizationMapper.updateById(userOrganization);
                base.setCode(0);
                base.setMessage("组织机构启用");
            } else {
                base.setMessage("传入参数有误");
                base.setCode(0);
                return base;
            }
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|enableOrganization->组织机构启用[" + ex.getMessage() + "]");
        }
        return base;
    }

    /**
     * 组织机构信息查询
     *
     * @param para
     * @return
     */
    @Override
    public ResponseResultPage<OrganizationDTO> queryOrganizations(
            PageData<OrganizationRequest> para) {
        ResponseResultPage<OrganizationDTO> result = new ResponseResultPage<>();
        try {
            String tenantId = JwtTokenUtil.currTenant();
            Page<OrganizationDTO> page = new Page<>(para.getCurrent(), para.getSize());
            page.setAsc(para.getAscs());
            page.setDesc(para.getDescs());
            Page<OrganizationDTO> pageResult =
                    organizationMapper.queryOrganizations(page, para.getCondition());
            result.setCode(1);
            result.setMessage("组织机构信息查询完成");
            result.setRecords(pageResult.getRecords());
            result.setTotal(pageResult.getTotal());
            result.setSize(para.getSize());
            result.setCurrent(para.getCurrent());
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|queryOrganizations->组织机构信息查询[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 门店信息删除（同时删除组织机构信息）
     *
     * @param storeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase deleteStore(String storeId) {
        ResponseBase responseBase = new ResponseBase();
        try {
            // 删除门店信息，因为t_user_store_info没有status字段，所以直接删除
            storeMapper.deleteById(storeId);
            // 同步删除组织机构和组织机构明细信息
            organizationMapper.deleteById(storeId);
            organizationInfoMapper.deleteById(storeId);
            responseBase.setCode(1);
            responseBase.setMessage("门店删除成功");
        } catch (Exception e) {
            log.error("门店删除失败：{}", e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            responseBase.setCode(0);
            responseBase.setMessage("门店删除失败。");
        }
        return responseBase;
    }

    /**
     * 门店类型添加
     *
     * @param storeType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase createStoreType(StoreTypeDTO storeType) {
        ResponseBase responseBase = new ResponseBase();
        try {
            String userId = JwtTokenUtil.currUser();
            String tenantId = JwtTokenUtil.currTenant();
            UserStoreType userStoreType = new UserStoreType(storeType, tenantId);
            if (StringUtils.isBlank(storeType.getStoreType())) {
                userStoreType.setStoreType(UUID.randomUUID().toString());
            }
            userStoreType.setCreateTime(LocalDateTime.now());
            userStoreType.setCreateUser(userId);
            storeTypeMapper.insert(userStoreType);
            responseBase.setCode(1);
            responseBase.setMessage("门店类型添加成功");
        } catch (Exception e) {
            log.error("门店类型添加失败：{}", e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            responseBase.setCode(0);
            responseBase.setMessage("门店类型添加失败。");
        }
        return responseBase;
    }

    /**
     * 门店类型修改
     *
     * @param storeType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase modifyStoreType(StoreTypeDTO storeType) {
        ResponseBase responseBase = new ResponseBase();
        try {
            String userId = JwtTokenUtil.currUser();
            String tenantId = JwtTokenUtil.currTenant();
            UserStoreType userStoreType = new UserStoreType(storeType, tenantId);
            userStoreType.setUpdateUser(userId);
            storeTypeMapper.updateById(userStoreType);
            responseBase.setCode(1);
            responseBase.setMessage("门店类型修改成功");
        } catch (Exception e) {
            log.error("门店类型修改失败：{}", e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            responseBase.setCode(0);
            responseBase.setMessage("门店类型修改失败。");
        }
        return responseBase;
    }

    /**
     * 门店类型删除
     *
     * @param storeType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase deleteStoreType(String storeType) {
        ResponseBase responseBase = new ResponseBase();
        try {
            storeTypeMapper.deleteById(storeType);
            responseBase.setCode(1);
            responseBase.setMessage("门店类型删除成功");
        } catch (Exception e) {
            log.error("门店类型删除失败：{}", e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            responseBase.setCode(0);
            responseBase.setMessage("门店类型删除失败。");
        }
        return responseBase;
    }

    /**
     * 批量更新终端/门店配置参数
     *
     * @param params
     * @return
     */
    @Override
    public ResponseBase saveStoreParams(List<StoreParamDTO> params) {
        ResponseBase base = new ResponseBase();
        try {
            List<UserStoreParam> userStoreParams =
                    Object2ObjectUtil.parseList(params, UserStoreParam.class);
            Integer i = storeParamMapper.saveStoreParams(userStoreParams);
            base.setCode(1);
            base.setMessage("更新条终端/门店配置参数信息完成");
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|saveStoreParams->批量更新终端/门店配置参数" + ex.getMessage());
            base.setMessage(ex.getMessage());
            base.setCode(0);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return base;
    }

    /**
     * 门店参数查询(根据门店ID)
     *
     * @param storeId
     * @return
     */
    @Override
    public ResponseRecords<StoreParamDTO> queryStoreParams(String storeId) {
        ResponseRecords<StoreParamDTO> result = new ResponseRecords<>();
        try {
            List<StoreParamDTO> list = storeParamMapper.queryStoreParams(storeId);
            result.setCode(1);
            result.setMessage("查询共有" + list.size() + "条门店配置参数");
            result.setRecords(list);
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|queryStoreParams->门店参数查询" + ex.getMessage());
            result.setMessage(ex.getMessage());
            result.setCode(0);
        }
        return result;
    }

    /**
     * 根据门店ID获取门店信息
     *
     * @param storeMessage 门店id，对应字段org_seq
     * @return
     */
    @Override
    public ResponseRecords<UserStoreInfoDTO> queryStoreByUUId(StoreMessage storeMessage) {
        ResponseRecords<UserStoreInfoDTO> result = new ResponseRecords<>();
        try {
            // 返回的门店数量设为10个
            if (storeMessage.getLimit() == 0) {
                storeMessage.setLimit(10);
            }
            // UserStoreInfoDTO userStoreInfoDTO = storeMapper.queryStoreById(storeMessage);
            //			UserStoreInfo userStoreInfoDTO = storeMapper.selectById(storeId);
            List<UserStoreInfoDTO> list = storeMapper.queryStoreById(storeMessage);
            for (UserStoreInfoDTO shop : list) {
                Double distance =
                        MathUtil.getDistance(
                                Double.valueOf(storeMessage.getLng().doubleValue()),
                                Double.valueOf(storeMessage.getLat().doubleValue()),
                                Double.parseDouble(shop.getStoreLng()),
                                Double.parseDouble(shop.getStoreLat()));
                shop.setDistance(distance.intValue());
            }
            result.setCode(1);
            result.setRecords(list);
            result.setMessage("查询成功");
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|queryStoreById->查询门店信息[" + ex.getMessage() + "]");
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResult<StoreInfoDTO> queryStoreNameById(String orgSeq) {
        ResponseResult<StoreInfoDTO> result = new ResponseResult<>();
        try {
            StoreInfoDTO storeInfoDTO = storeMapper.queryStoreNameById(orgSeq);
            result.setCode(1);
            result.setMessage("查询成功");
            result.setResult(storeInfoDTO);
        } catch (Exception ex) {
            result.setCode(0);
            log.error("OrganizationServiceImpl|queryStoreNameById->查询门店信息[" + ex.getMessage() + "]");
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @Override
    public ResponseRecords<OrganizationChildDTO> queryOrganizationById(String orgSeq) {
        ResponseRecords<OrganizationChildDTO> result = new ResponseRecords<>();
        try { // 传入的orgSeq为战区的编码，查询的结果可能含有基地的信息，当查出基地编码后，
            List<OrganizationChildDTO> organizationDTO = organizationMapper.queryOrganizationById(orgSeq);
            List<OrganizationChildDTO> list = new ArrayList<>();
            for (OrganizationChildDTO organizationDTO1 : organizationDTO) {
                if (organizationDTO1.getOrgType().equals("3")) {
                    List<OrganizationChildDTO> organizationDTOS =
                            organizationMapper.queryOrganizationById(organizationDTO1.getOrgSeq());
                    for (OrganizationChildDTO childDTO : organizationDTOS) {
                        list.add(childDTO);
                    }
                } else {
                    list.add(organizationDTO1);
                }
            }
            result.setCode(1);
            result.setMessage("查询成功");
            result.setRecords(list);
        } catch (Exception ex) {
            result.setCode(0);
            log.error("OrganizationServiceImpl|queryOrganizationById->查询门店信息[" + ex.getMessage() + "]");
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @Override
    public ResponseRecords<OrganizationChildDTO> queryOrgChildById(String orgSeq) {
        ResponseRecords<OrganizationChildDTO> result = new ResponseRecords<>();
        List<OrganizationChildDTO> list = new ArrayList<>();

        UserOrganization userOrganization = organizationMapper.selectById(orgSeq);
        if ("4".equals(userOrganization.getOrgType())) {
            OrganizationChildDTO oc = new OrganizationChildDTO();
            BeanUtils.copyProperties(userOrganization, oc);
            list.add(oc);
        } else {
            List<String> orgSeqList = new ArrayList<>();
            orgSeqList.add(orgSeq);
            List<UserOrganization> uoList = queryRecursion(orgSeqList, null);
            if (uoList != null) {
                for (UserOrganization uo : uoList) {
                    OrganizationChildDTO oc = new OrganizationChildDTO();
                    BeanUtils.copyProperties(uo, oc);
                    list.add(oc);
                }
            }
        }
        result.setRecords(list);
        return result;
    }

    private List<UserOrganization> queryRecursion(
            List<String> orgSeqList, List<UserOrganization> paramList) {
        List<UserOrganization> reuo = null;
        if (!CollectionUtils.isEmpty(paramList) && "4".equals(paramList.get(0).getOrgType())) {
            return paramList;
        } else {
            QueryWrapper<UserOrganization> w = new QueryWrapper();
            w.in("up_org_seq", orgSeqList);
            List<UserOrganization> uoList = organizationMapper.selectList(w);
            if (uoList.size() > 0) {
                List<String> osList = new ArrayList<>();
                for (UserOrganization uo : uoList) {
                    osList.add(uo.getOrgSeq());
                }
                reuo = queryRecursion(osList, uoList);
            }
            return reuo;
        }
    }

    /**
     * 根据组织类型、终端ID获取终端菜单列表
     *
     * @param orgId 组织编码
     * @return List<StoreFunctionDTO>
     */
    @Override
    public ResponseRecords<StoreFunctionDTO> queryStoreFunctions(String orgId) {
        ResponseRecords<StoreFunctionDTO> result = new ResponseRecords<>();
        try {
            List<StoreFunctionDTO> list = organizationMapper.queryStoreFunctions(orgId);
            result.setCode(1);
            result.setRecords(list);
            result.setMessage("查询到" + list.size() + "条结果");
        } catch (Exception ex) {
            log.error(
                    "OrganizationServiceImpl|queryStoreFunctions->根据组织编码获取终端菜单列表[" + ex.getMessage() + "]");
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResultPage<UserStoreInfoDTO> queryStored(PageData<StoreMessage> para) {
        ResponseResultPage<UserStoreInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<UserStoreInfoDTO> page = new Page(para.getCurrent(), para.getSize());
            // 根据指定条件晒筛选出门店ids
            if (para.getCondition().getCircle() == 0) {
                para.getCondition().setCircle(5000000);
            }
            // 返回的门店数量设为10个
            if (para.getCondition().getLimit() == 0) {
                para.getCondition().setLimit(10);
            }
            Page<UserStoreInfoDTO> resultPage = storeMapper.queryStored(page, para.getCondition());
            //	List<UserStoreInfoDTO> list = storeMapper.queryStored(storeMessage);
            if (resultPage.getRecords() != null) {
                for (UserStoreInfoDTO shop : resultPage.getRecords()) {
                    if (shop.getStoreLat() != null && shop.getStoreLng() != null) {
                        Double distance =
                                MathUtil.getDistance(
                                        Double.valueOf(para.getCondition().getLng().doubleValue()),
                                        Double.valueOf(para.getCondition().getLat().doubleValue()),
                                        Double.parseDouble(shop.getStoreLng()),
                                        Double.parseDouble(shop.getStoreLat()));
                        shop.setDistance(distance.intValue());
                    }
                }
            }
            result.setCode(1);
            result.setSize(para.getSize());
            result.setCurrent(para.getCurrent());
            result.setTotal(resultPage.getTotal());
            result.setRecords(resultPage.getRecords());
            result.setMessage("查询到" + resultPage.getRecords().size() + "条门店信息");
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|queryStored->" + ex.getLocalizedMessage());
            result.setMessage(ex.getMessage());
            result.setCode(0);
        }
        return result;
    }

    @Override
    public ResponseResult<OrganizationDTO> queryTopById(String orgSeq) {
        ResponseResult<OrganizationDTO> result = organizationMapper.queryTopById(orgSeq);
        String OrgType = result.getResult().getOrgType();
        String UpOrgSeq = result.getResult().getUpOrgSeq();
        // 组织类型(1总部/2战区/3基地/4门店)',
        if (OrgType.equals(3)) {
            ResponseResult<OrganizationDTO> responseResult = organizationMapper.queryTopById(UpOrgSeq);
            return responseResult;
        } else {
            return result;
        }
    }

    /**
     * 门店查询（根据坐标位置查询门店列表）
     *
     * @param storeRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseRecords<OrganizationInfoDTO> queryStores(StoreRequest storeRequest) {
        ResponseRecords<OrganizationInfoDTO> result = new ResponseRecords<>();
        try {
            // 根据指定条件晒筛选出门店ids
            if (storeRequest.getCircle() == 0) {
                storeRequest.setCircle(5000000);
            }
            if (storeRequest.getLimit() == 0) {
                storeRequest.setLimit(100);
            }
            List<OrganizationInfoDTO> list =
                    storeMapper.queryStores(storeRequest, JwtTokenUtil.currTenant());
            for (OrganizationInfoDTO shop : list) {
                Double distance =
                        MathUtil.getDistance(
                                Double.valueOf(storeRequest.getLng().doubleValue()),
                                Double.valueOf(storeRequest.getLat().doubleValue()),
                                shop.getLongitude().doubleValue(),
                                shop.getLat().doubleValue());
                shop.setDistance(distance.intValue());
            }
            result.setCode(1);
            result.setRecords(list);
            result.setMessage("查询到" + list.size() + "条门店信息");
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|queryStores->" + ex.getLocalizedMessage());
            result.setMessage(ex.getMessage());
            result.setCode(0);
        }
        return result;
    }

    /**
     * 查询行政区划信息（根据ID）
     *
     * @param areaId
     * @return
     */
    @Override
    public ResponseResult<AreaInfoDTO> queryAreaInfoById(String areaId) {
        ResponseResult<AreaInfoDTO> result = new ResponseResult<>();
        try {
            AreaInfoDTO userArea = userAreaMapper.getAreaInfoById(areaId);
            result.setCode(1);
            result.setMessage("查询成功");
            result.setResult(userArea);
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 修改行政区划信息
     *
     * @param areaInfoDTO
     * @return
     */
    @Override
    public ResponseBase modifyAreaInfo(AreaInfoDTO areaInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        try {
            String userId = JwtTokenUtil.currUser();
            String tenantId = JwtTokenUtil.currTenant();
            if (StringUtils.isNotBlank(areaInfoDTO.getAreaSeq())) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("area_seq", areaInfoDTO.getAreaSeq());
                param.put("tenant_id", tenantId);
                List<UserArea> areas = userAreaMapper.selectByMap(param);
                if (areas != null && areas.size() > 0) {
                    for (UserArea u : areas) {
                        if (!areaInfoDTO.getAreaId().equals(u.getAreaId())) {
                            responseBase.setMessage("区域编号已存在");
                            responseBase.setCode(0);
                            return responseBase;
                        }
                    }
                }
            }

            areaInfoDTO.setUpdateUser(userId);
            int res = userAreaMapper.modifyAreaInfo(areaInfoDTO);
            if (res == 1) {
                responseBase.setCode(1);
                responseBase.setMessage("修改行政区划信息成功");
            } else {
                throw new RuntimeException("修改行政区划信息失败");
            }
        } catch (Exception e) {
            responseBase.setCode(0);
            responseBase.setMessage(e.getMessage());
        }
        return responseBase;
    }

    /**
     * 添加行政区划信息
     *
     * @param areaInfoDTO
     * @return
     */
    @Override
    public ResponseBase addAreaInfo(AreaInfoDTO areaInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        try {
            String userId = JwtTokenUtil.currUser();
            String tenantId = JwtTokenUtil.currTenant();
            UserArea userArea = Object2ObjectUtil.parseObject(areaInfoDTO, UserArea.class);
            if (StringUtils.isBlank(userArea.getAreaId())) {
                userArea.setAreaId(UUID.randomUUID().toString());
            }
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("area_seq", userArea.getAreaSeq());
            List<UserArea> userAreas = userAreaMapper.selectByMap(param);
            if (userAreas != null && userAreas.size() > 0) {
                responseBase.setMessage("【" + userArea.getAreaName() + "】已存在");
                responseBase.setCode(0);
                return responseBase;
            }
            userArea.setCreateUser(userId);
            userArea.setUpdateUser(userId);
            userArea.setTenantId(tenantId);
            int res = userAreaMapper.insert(userArea);
            if (res == 1) {
                responseBase.setCode(1);
                responseBase.setMessage("新增成功");
            } else {
                responseBase.setMessage("新增行政区域【" + userArea.getAreaName() + "】失败");
                responseBase.setCode(0);
                return responseBase;
            }
        } catch (Exception e) {
            responseBase.setCode(0);
            responseBase.setMessage(e.getMessage());
        }
        return responseBase;
    }

    /**
     * 查询行政区划列表
     *
     * @param areaRequest
     * @return
     */
    @Override
    public ResponseRecords<AreaInfoDTO> queryAreaInfos(AreaInfoRequest areaRequest) {
        ResponseRecords<AreaInfoDTO> result = new ResponseRecords<>();
        try {
            String tenantId = JwtTokenUtil.currTenant();
            List<AreaInfoDTO> list = userAreaMapper.queryAreaInfos(areaRequest, tenantId);
            if (list != null) {
                result.setCode(1);
                result.setMessage("查询成功");
                result.setRecords(list);
            } else {
                result.setCode(1);
                result.setMessage("未查询到行政区划信息");
            }
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage(e.getMessage());
            log.error("查询行政区划列表失败---" + e.getMessage());
        }
        return result;
    }

    /**
     * 删除终端/门店配置参数
     *
     * @param paramId
     * @return
     */
    @Override
    public ResponseBase deleteStoreParam(String paramId) {
        ResponseBase base = new ResponseBase();
        Integer count = storeParamMapper.deleteStoreParam(paramId);
        if (count > 0) {
            base.setCode(1);
            base.setMessage("删除终端/门店配置参数信息成功");
        } else {
            base.setCode(0);
            base.setMessage("删除终端/门店配置参数信息失败");
        }
        return base;
    }

    /**
     * 统计门店数量（根据门店所属行政区划） 按照所属行政区划统计门店并根据门店经营状态过滤数据
     *
     * @param areaSeq
     * @param status
     * @return
     */
    @Override
    public ResponseRecords<AreaInfoDTO> countStoreByArea(String areaSeq, String status) {
        ResponseRecords<AreaInfoDTO> result = new ResponseRecords<>();
        try {
            String tenantId = JwtTokenUtil.currTenant();
            List<AreaInfoDTO> list = userAreaMapper.countStoreByArea(areaSeq, status, tenantId);
            result.setCode(1);
            result.setMessage("统计成功");
            result.setRecords(list);
        } catch (Exception e) {
            log.error("统计门店数量异常---" + e.getLocalizedMessage());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 系统消息查询（根据门店ID查询系统发送给终端的消息列表）
     *
     * @param pageData
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResultPage<UserStoreMessageDTO> receiveMessages(
            PageData<StoreMessageRequest> pageData) throws RuntimeException {
        ResponseResultPage<UserStoreMessageDTO> result = new ResponseResultPage<>();
        if (pageData != null) {
            try {
                Page<UserStoreMessageDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
                Page<UserStoreMessageDTO> pageResult =
                        storeMessageMapper.receiveMessages(
                                page, pageData.getCondition(), JwtTokenUtil.currTenant());
                result.setRecords(pageResult.getRecords());
                result.setCode(1);
                result.setMessage("查询到" + pageResult.getRecords().size() + "条系统消息");
                result.setTotal(pageResult.getTotal());
                result.setCurrent(pageData.getCurrent());
                result.setSize(pageData.getSize());
            } catch (Exception ex) {
                log.error("OrganizationServiceImpl|receiveMessages->系统消息查询[" + ex.getMessage() + "]");
                result.setMessage(ex.getMessage());
            }
        } else {
            throw new RuntimeException("分页查询必须传入分页参数");
        }
        return result;
    }

    /**
     * 系统消息读取（将消息标记为已读取状态并记录读取时间）
     *
     * @param messageId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase readMessage(String messageId) {
        ResponseBase base = new ResponseBase();
        if (!StringUtils.isBlank(messageId)) {
            try {
                storeMessageMapper.readMessage(messageId);
                base.setCode(1);
                base.setMessage("门店系统消息已读取");
            } catch (Exception ex) {
                log.error("OrganizationServiceImpl|readMessage->系统消息读取[" + ex.getMessage() + "]");
                base.setMessage(ex.getMessage());
                base.setCode(0);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } else {
            base.setMessage("传入参数messageId有误");
        }
        return base;
    }

    /**
     * 根据区域编码获取门店信息
     *
     * @param pageData
     * @return
     */
    @Override
    public ResponseResultPage<OrganizationInfoDTO> queryStoreByAreaSeq(
            PageData<StoreRequest> pageData) {
        ResponseResultPage<OrganizationInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<OrganizationInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            Page<OrganizationInfoDTO> list =
                    storeMapper.queryStoreByAreaSeq(page, pageData.getCondition(), JwtTokenUtil.currTenant());
            result.setCurrent(pageData.getCurrent());
            result.setSize(pageData.getSize());
            result.setTotal(list.getTotal());
            result.setCode(1);
            result.setRecords(list.getRecords());
            result.setMessage("共查询到" + list.getRecords().size() + "条数据");
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setMessage(ex.getMessage());
            log.error(
                    "OrganizationServiceImpl|queryStoreByAreaSeq->根据区域编码获取门店信息[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 获取推荐店铺
     *
     * @param lng
     * @param lat
     * @return
     */
    @Override
    public ResponseRecords<OrganizationInfoDTO> getRecoStore(String lng, String lat) {
        ResponseRecords<OrganizationInfoDTO> result = new ResponseRecords<OrganizationInfoDTO>();
        try {
            String tenantId = JwtTokenUtil.currTenant();
            List<OrganizationInfoDTO> list = storeMapper.getRecoStore(lng, lat, tenantId);
            for (OrganizationInfoDTO shop : list) {
                Double distance =
                        MathUtil.getDistance(
                                Double.valueOf(lng),
                                Double.valueOf(lat),
                                shop.getLongitude().doubleValue(),
                                shop.getLat().doubleValue());
                shop.setDistance(distance.intValue());
            }
            result.setCode(1);
            result.setMessage("共查询到" + list.size() + "个门店信息");
            result.setRecords(list);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|getRecoStore->获取推荐店铺[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 获取门店
     *
     * @return
     */
    @Override
    public ResponseResultPage<StoreInfoDTO> queryStoreInfo(PageData<StoreInfoRequest> pageData) {
        ResponseResultPage<StoreInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<StoreInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            String[] arr = null;
            String gradeId = pageData.getCondition().getGradelevelId();
            if (gradeId != null) {
                arr = gradeId.split(",");
            }
            String[] zqarr = null;
            String zqOrgSeq = pageData.getCondition().getZqOrgSeq();
            if (zqOrgSeq != null) {
                zqarr = gradeId.split(",");
            }
            Page<StoreInfoDTO> pageResult =
                    storeMapper.queryStoreInfo(page, pageData.getCondition(), arr, zqarr);
            //            if (pageData.getCondition().getPlanId()!=null &&
            // pageData.getCondition().getCouponId()!=null){
            //                ResponseRecords<StoreInfoDTO> resultDto =
            // couponService.queryStoreByPlanId(pageData.getCondition().getPlanId(),pageData.getCondition().getCouponId());
            //                if(resultDto!=null && resultDto.getRecords()!=null){
            //                    for (StoreInfoDTO storeInfoDTO : resultDto.getRecords()){
            //                        ResponseResult<UserStoreInfoDTO> result1 =
            // organizationService.queryStoreById(storeInfoDTO.getOrgSeq());
            //
            //                    }
            //                }
            //            }
            result.setCode(1);
            result.setMessage("共有" + pageResult.getRecords().size() + "条门店信息");
            result.setRecords(pageResult.getRecords());
            result.setSize(pageData.getSize());
            result.setCurrent(pageData.getCurrent());
            result.setTotal(pageResult.getTotal());
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|queryStoreInfo->获取门店[" + ex.getMessage() + "]");
            result.setCode(0);
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    /**
     * 获取下级所有连锁门店--根据orgSeq
     *
     * @param orgSeq
     * @return
     */
    @Override
    public ResponseRecords<StoreInfoDTO> getStoresByUpOrgSeq(String orgSeq) {
        ResponseRecords<StoreInfoDTO> records = new ResponseRecords<StoreInfoDTO>();
        try {
            List<StoreInfoDTO> storeList = storeMapper.getStoresByUpOrgSeq(orgSeq);
            records.setCode(1);
            records.setMessage("获取成功");
            records.setRecords(storeList);
        } catch (Exception ex) {
            log.error(
                    "OrganizationServiceImpl|getStoresByUpOrgSeq->获取下级所有连锁门店异常[" + ex.getMessage() + "]");
            records.setCode(0);
            records.setMessage(ex.getMessage());
        }
        return records;
    }

    /**
     * 根据门店id查找门店
     *
     * @param orgSeq
     * @return
     */
    @Override
    public ResponseResult<StoreInfoDTO> getStoreInfo(String orgSeq) {
        ResponseResult<StoreInfoDTO> result = new ResponseResult<>();
        try {
            StoreInfoDTO storeDTO = storeMapper.getStoreInfo(orgSeq);
            result.setCode(1);
            result.setMessage("根据门店id查找门店完成");
            result.setResult(storeDTO);
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error(
                    "OrganizationServiceImpl|getStoreInfoByOrgSeq->根据门店id查找门店[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 系统消息数量查询（按照消息类型、读取状态汇总）
     *
     * @param storeId
     * @return
     */
    @Override
    public ResponseRecords<UserStoreMessageCountDTO> countMessages(String storeId) {
        ResponseRecords<UserStoreMessageCountDTO> result = new ResponseRecords<>();
        try {
            List<UserStoreMessageCountDTO> list = storeMessageMapper.countMessages(storeId);
            result.setCode(1);
            result.setRecords(list);
            result.setMessage("分类查询成功");
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("OrganizationServiceImpl|countMessages->[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 门店类型查询
     *
     * @param para
     * @return
     */
    @Override
    public ResponseRecords<StoreTypeDTO> queryStoreTypes(StoreTypeRequest para) {
        ResponseRecords<StoreTypeDTO> result = new ResponseRecords<StoreTypeDTO>();
        String tenantId = JwtTokenUtil.currTenant();
        List<StoreTypeDTO> storeTypeDTOS = storeTypeMapper.queryStoreTypes(para, tenantId);
        result.setMessage("查询到" + storeTypeDTOS.size() + "条门店类型信息");
        result.setCode(1);
        if (storeTypeDTOS.size() > 0) {
            result.setRecords(storeTypeDTOS);
        } else {
            result.setRecords(new ArrayList<>());
        }
        return result;
    }

    /**
     * 终端消息保存推送
     */
    @Transactional
    @Override
    public ResponseBase sendMessages(List<String> deviceIds, UserStoreMessageDTO mess) {
        ResponseBase responseBase = new ResponseBase();
        try {
            UserStoreMessage userStoreMessage =
                    Object2ObjectUtil.parseObject(mess, UserStoreMessage.class);
            // 消息入库
            for (String deviceId : deviceIds) {
                userStoreMessage.setDeviceId(deviceId);
                userStoreMessage.setMessageId(UUID.randomUUID().toString());
                userStoreMessage.setCreateUser(JwtTokenUtil.currUser());
                userStoreMessage.setCreateTime(LocalDateTime.now());
                userStoreMessage.setTenantId(JwtTokenUtil.currTenant());
                storeMessageMapper.insert(userStoreMessage);
            }
            // 系统推送时通过rpc调用推送接口
            if (mess.getPushmethod() == 2) {
                MessageCustom mc = new MessageCustom();
                Map<String, Object> map =
                        com.alibaba.fastjson.JSONObject.parseObject(mess.getMessageContents());
                mc.setTitle(map.get("title") == null ? "" : map.get("title").toString());
                mc.setContent(map.get("content") == null ? "" : map.get("content").toString());
                map.remove("title");
                map.remove("content");
                mc.setMessageMap(map);
                ResponseBase res = new ResponseBase();
                if (res.getCode() == 0) {
                    responseBase.setCode(0);
                    responseBase.setMessage("消息推送失败");
                    return responseBase;
                }
            }
            responseBase.setCode(1);
            responseBase.setMessage("终端消息发送成功");

        } catch (Exception e) {
            log.error("OrganizationServiceImpl|sendMessages->终端消息发送异常-[" + e.getMessage() + "]");
            responseBase.setCode(0);
            responseBase.setMessage("终端消息发送异常");
        }
        return responseBase;
    }

    @Override
    public ResponseRecords<StoreInfoDTO> getStoresByUserId() {
        ResponseRecords<StoreInfoDTO> records = new ResponseRecords<StoreInfoDTO>();
        try {
            List<StoreInfoDTO> storeList =
                    storeMapper.getStoresByUserId(JwtTokenUtil.currUser(), JwtTokenUtil.currTenant());
            records.setCode(1);
            records.setMessage("获取成功");
            records.setRecords(storeList);
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|getStoresByUserId->获取下级所有连锁门店异常[" + ex.getMessage() + "]");
            records.setCode(0);
            records.setMessage(ex.getMessage());
        }
        return records;
    }

    @Override
    public ResponseRecords<OrganizationDTO> getOrgTree() {
        ResponseRecords<OrganizationDTO> responseResult = new ResponseRecords<>();
        List<OrganizationDTO> upList = organizationMapper.getUpOrg();
        for (OrganizationDTO upOrg : upList) {
            upOrg.setChildren(getSonOrg(upOrg.getOrgSeq()));
        }
        responseResult.setCode(1);
        responseResult.setMessage("获取成功!");
        responseResult.setRecords(upList);
        return responseResult;
    }

    public List<OrganizationDTO> getSonOrg(String orgSeq) {
        List<OrganizationDTO> sonList = organizationMapper.getSonOrg(orgSeq);
        for (OrganizationDTO dto : sonList) {
            if (dto.getLevel() != null && dto.getLevel() == 4) {
                continue;
            }
            dto.setChildren(getSonOrg(dto.getOrgSeq()));
        }
        return sonList;
    }

    @Override
    public ResponseRecords<OrganizationDTO> getActiveOrgTree(String orgSeq) {
        ResponseRecords<OrganizationDTO> responseResult = new ResponseRecords<>();
        List<OrganizationDTO> upList = organizationMapper.getActiveUpOrg(orgSeq);
        for (OrganizationDTO upOrg : upList) {
            upOrg.setChildren(getSonOrg(upOrg.getOrgSeq()));
        }
        responseResult.setCode(1);
        responseResult.setMessage("获取成功!");
        responseResult.setRecords(upList);
        return responseResult;
    }

    @Override
    public ResponseResult<OrganizationChildDTO> queryZqOrgSeq(String orgSeq) {
        ResponseResult<OrganizationChildDTO> result = new ResponseResult<>();
        OrganizationChildDTO organizationChildDTO = organizationMapper.queryZqOrgSeq(orgSeq);
        if (organizationChildDTO != null && organizationChildDTO.getZqOrgSeq() != null) {
            result.setResult(organizationChildDTO);
            result.setMessage("查询成功");
            result.setCode(1);
        } else {
            result.setMessage("查询失败");
            result.setCode(0);
        }
        return result;
    }

    @Override
    public ResponseResult<UserStoreInfoDTO> queryStoreById(String orgSeq) {
        ResponseResult<UserStoreInfoDTO> result = new ResponseResult<>();
        UserStoreInfoDTO userStoreInfoDTO = organizationMapper.queryStoreById(orgSeq);
        if (userStoreInfoDTO != null) {
            result.setResult(userStoreInfoDTO);
            result.setMessage("查询成功");
            result.setCode(1);
        } else {
            result.setMessage("查询失败");
            result.setCode(0);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase createStore(StoreInfoDTO store) {
        ResponseBase responseBase = new ResponseBase();
        try {
            // 新增组织机构
            String userId = JwtTokenUtil.currUser();
            UserOrganization organization = new UserOrganization();
            organization.setOrgSeq(UUID.randomUUID().toString());
            organization.setOrgName(store.getStoreName());
            organization.setOrgType("3");
            organization.setStatus(1);
            organization.setUpOrgSeq(store.getUpOrgSeq());
            organization.setCreateTime(LocalDateTime.now());
            organization.setCreateUser(userId);
            organization.setUpdateTime(LocalDateTime.now());
            organization.setUpdateUser(userId);
            organizationMapper.insert(organization);

            // 新增门店信息
            UserStoreInfo storeInfo = Object2ObjectUtil.parseObject(store, UserStoreInfo.class);
            storeInfo.setUuid(UUID.randomUUID().toString());
            storeInfo.setOrgSeq(organization.getOrgSeq());
            storeInfo.setStatus(1);
            storeInfo.setCreateUser(userId);
            storeInfo.setCreateTime(LocalDateTime.now());
            storeInfo.setUpdateUser(userId);
            storeInfo.setUpdateTime(LocalDateTime.now());
            storeMapper.insert(storeInfo);
            responseBase.setCode(1);
            responseBase.setMessage("门店添加成功");
        } catch (Exception e) {
            log.error("门店添加失败：{}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            responseBase.setCode(0);
            responseBase.setMessage("门店添加失败");
        }
        return responseBase;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBase modifyStore(StoreInfoDTO store) {
        ResponseBase responseBase = new ResponseBase();
        try {
            String userId = JwtTokenUtil.currUser();
            UserStoreInfo storeInfo = Object2ObjectUtil.parseObject(store, UserStoreInfo.class);
            storeInfo.setUpdateUser(userId);
            storeInfo.setUpdateTime(LocalDateTime.now());
            storeMapper.updateById(storeInfo);

            UserOrganization org = new UserOrganization();
            org.setOrgSeq(store.getOrgSeq());
            org.setOrgName(store.getStoreName());
            org.setUpdateUser(userId);
            org.setUpdateTime(LocalDateTime.now());
            organizationMapper.updateById(org);

            responseBase.setCode(1);
            responseBase.setMessage("门店修改成功");
        } catch (Exception e) {
            log.error("门店修改失败：{}", e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚
            responseBase.setCode(0);
            responseBase.setMessage("门店修改失败。");
        }
        return responseBase;
    }

    /**
     * 获取组织树
     *
     * @return
     */
    @Override
    public ResponseRecords<OrganizationDTO> getOrgTreeQuery(String upOrgSeq) {
        ResponseRecords<OrganizationDTO> responseResult = new ResponseRecords<>();
        List<OrganizationDTO> upList = organizationMapper.getUpOrgQuery(upOrgSeq);
        for (OrganizationDTO organizationDTO : upList) {
            // 查询是否有子节点
            organizationDTO.setHasChildren(queryHasChildren(organizationDTO.getOrgSeq()));
        }
        responseResult.setCode(1);
        responseResult.setMessage("获取成功!");
        responseResult.setRecords(upList);
        return responseResult;
    }

    /**
     * 查询当前组织节点是否有子节点
     *
     * @return
     */
    public Boolean queryHasChildren(String upOrgSeq) {
        QueryWrapper<UserOrganization> qw = new QueryWrapper();
        qw.eq(upOrgSeq != null && !upOrgSeq.equals(""), "up_org_seq", upOrgSeq);
        Integer integer = organizationMapper.selectCount(qw);
        if (integer > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getStoreArea(String orgSeq) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("org_seq", orgSeq);
        BigDecimal managerArea = storeMapper.selectOne(qw).getManagerArea();
        Integer area = 0;
        if (managerArea != null) {
            area = managerArea.intValue();
        }
        return area;
    }

    @Override
    public ResponseResultPage<UserStoreInfoDTO> queryOftenStores(PageData<StoreMessage> para) {
        ResponseResultPage<UserStoreInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<UserStoreInfoDTO> page = new Page(para.getCurrent(), para.getSize());
            // 根据指定条件晒筛选出门店ids
            if (para.getCondition().getCircle() == 0) {
                para.getCondition().setCircle(5000000);
            }
            // 返回的门店数量设为10个
            if (para.getCondition().getLimit() == 0) {
                para.getCondition().setLimit(10);
            }

            PageData<MemberReqDetail> pageData = new PageData<>();

            pageData.setCurrent(para.getCurrent());
            pageData.setSize(para.getSize());
            MemberReqDetail memberReqDetail = new MemberReqDetail();
            memberReqDetail.setMemberId(para.getCondition().getMemberId());
            pageData.setCondition(memberReqDetail);
            //根据会员id  查出足迹表中所有的门店orgseq
            ResponseResultPage<String> resultPage1 = memberService.queryStoreIdsBymId(pageData);
            List<String> orgSeqList = resultPage1.getRecords();
            if (ToolUtil.notEmpty(orgSeqList)) {
                Page<UserStoreInfoDTO> resultPage = storeMapper.queryOftenStores(page, para.getCondition(),orgSeqList);
                //	List<UserStoreInfoDTO> list = storeMapper.queryStored(storeMessage);
                if (ToolUtil.notEmpty(resultPage) && ToolUtil.notEmpty(resultPage.getRecords())) {
                    for (UserStoreInfoDTO shop : resultPage.getRecords()) {
                        if (shop.getStoreLat() != null && shop.getStoreLng() != null) {
                            Double distance =
                                    MathUtil.getDistance(
                                            Double.valueOf(para.getCondition().getLng().doubleValue()),
                                            Double.valueOf(para.getCondition().getLat().doubleValue()),
                                            Double.parseDouble(shop.getStoreLng()),
                                            Double.parseDouble(shop.getStoreLat()));
                            shop.setDistance(distance.intValue());
                        }
                    }
                    result.setCode(1);
                    result.setSize(para.getSize());
                    result.setCurrent(para.getCurrent());
                    result.setTotal(resultPage.getTotal());
                    result.setRecords(resultPage.getRecords());
                    result.setMessage("查询到" + resultPage.getRecords().size() + "条门店信息");
                    return result;
                } else {
                    result.setCode(0);
                    result.setMessage("未查到常用专卖店信息");
                }
            } else {
                result.setCode(0);
                result.setMessage("未查到常用专卖店信息");
                return result;
            }
        } catch (Exception ex) {
            log.error("OrganizationServiceImpl|queryOftenStores->" + ex.getLocalizedMessage());
            result.setMessage("常用专卖店信息查询异常" + ex.getMessage());
            result.setCode(0);
        }
        return result;
    }
}
