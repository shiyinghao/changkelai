package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.service.user.OrganizationService;
import com.icss.newretail.user.dao.OrganizationMapper;
import com.icss.newretail.user.entity.UserOrganization;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

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
//            if (dto.getLevel() != null && dto.getLevel() == 4) {
//                continue;
//            }
            dto.setChildren(getSonOrg(dto.getOrgSeq()));
        }
        return sonList;
    }


}
