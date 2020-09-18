package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.TenantTitleDTO;
import com.icss.newretail.user.dao.TenantTitleMapper;
import com.icss.newretail.user.entity.Tenant;
import com.icss.newretail.user.entity.TenantTitle;
import com.icss.newretail.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.TenantDTO;
import com.icss.newretail.model.TenantRequest;
import com.icss.newretail.service.user.TenantService;
import com.icss.newretail.user.dao.TenantMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 平台租户 服务实现类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-27
 */
@Slf4j
@Service
public class TenantServiceImpl implements TenantService {
  
  @Autowired
  private TenantMapper tenantMapper;
  @Autowired
  private TenantTitleMapper tenantTitleMapper;

  /**
   * 创建租户（tenant_code不能为空不能重复）
   *
   * @param para
   * @return
   */
  @Override
  @Transactional(rollbackFor = { Exception.class })
  public ResponseBase createTenant(TenantDTO para) {
    ResponseBase base = new ResponseBase();
    try {
      String userId = JwtTokenUtil.currUser();
      Tenant tenant = new Tenant(para);
      //tenant_code不能重复
      TenantDTO tenantDTO = tenantMapper.getByTenantCode(tenant.getTenantCode());
      if(tenantDTO != null){
        base.setCode(0);
        base.setMessage("tenant_code不能重复");
        return base;
      }
      if(StringUtils.isBlank(tenant.getTenantId())){
        tenant.setTenantId(UUID.randomUUID().toString());
      }
      //设置创建时间、创建人
      tenant.setCreateTime(LocalDateTime.now());
      tenant.setCreateUser(userId);
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      if(StringUtils.isNotBlank(para.getExpireTime())){
        tenant.setExpireTime(LocalDateTime.parse(para.getExpireTime(), df));
      }
      //保存平台租户标题
      List<TenantTitleDTO> titleDTOS = para.getTenantTitles();
      for (TenantTitleDTO dto : titleDTOS){
        TenantTitle title = new TenantTitle(dto);
        if(StringUtils.isBlank(title.getUuid())){
          title.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        }
        title.setCreateTime(LocalDateTime.now());
        title.setCreateUser(userId);
        title.setTenantId(tenant.getTenantId());
        tenantTitleMapper.insert(title);
      }
      tenantMapper.insert(tenant);
      base.setCode(1);
      base.setMessage("租户创建成功。");
    }catch (Exception e){
      base.setCode(0);
      base.setMessage(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
      log.error("TenantServiceImpl|createTenant|" + e.getMessage());
    }
    return base;
  }

  /**
   * 租户信息更新
   *
   * @param para
   * @return
   */
  @Override
  @Transactional(rollbackFor = { Exception.class })
  public ResponseBase modifyTenant(TenantDTO para) {
    ResponseBase base = new ResponseBase();
    try {
      String userId = JwtTokenUtil.currUser();
      Tenant tenant = new Tenant(para);
      //tenant_code不能重复
      if (StringUtils.isNotBlank(para.getTenantCode())) {
        TenantDTO tenantDTO = tenantMapper.getByTenantCode(tenant.getTenantCode());
        if(tenantDTO != null && !tenantDTO.getTenantId().equals(para.getTenantId())){
          base.setCode(0);
          base.setMessage("tenant_code不能重复");
          return base;
        }
      }
      tenant.setUpdateUser(userId);
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      if(StringUtils.isNotBlank(para.getExpireTime())){
        tenant.setExpireTime(LocalDateTime.parse(para.getExpireTime(), df));
      }
      //更新平台租户标题
      List<TenantTitleDTO> titleDTOS = para.getTenantTitles();
      if(titleDTOS!=null && titleDTOS.size()>0){
        tenantTitleMapper.deleteByTenantId(tenant.getTenantId());
      }
      for (TenantTitleDTO dto : titleDTOS){
        TenantTitle title = new TenantTitle(dto);
        if(StringUtils.isBlank(title.getUuid())){
          title.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        }
        title.setCreateTime(LocalDateTime.now());
        title.setCreateUser(userId);
        title.setTenantId(tenant.getTenantId());
        title.setUpdateUser(userId);
        tenantTitleMapper.insert(title);
      }
      tenantMapper.updateById(tenant);
      base.setCode(1);
      base.setMessage("租户更新成功。");
    }catch (Exception e){
      base.setCode(0);
      base.setMessage(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
      log.error("TenantServiceImpl|modifyTenant|" + e.getMessage());
    }
    return base;
  }

  /**
   * 查询租户信息（单个）
   *
   * @param tenantId
   * @return
   */
  @Override
  public ResponseResult<TenantDTO> queryTenantById(String tenantId) {
    ResponseResult<TenantDTO> result = new ResponseResult<TenantDTO>();
    TenantDTO tenantDTO = tenantMapper.queryTenantById(tenantId);
    result.setCode(1);
    result.setMessage("租户信息查询成功");
    result.setResult(tenantDTO);
    return result;
  }

  /**
   * 查询租户信息（列表）
   *
   * @param para
   * @return
   */
  @Override
  public ResponseRecords<TenantDTO> queryTenants(TenantRequest para) {
    ResponseRecords<TenantDTO> result = new ResponseRecords<TenantDTO>();
    List<TenantDTO> tenantDTOS = tenantMapper.queryTenants(para);
    result.setCode(1);
    result.setMessage("共查询到"+tenantDTOS.size()+"条租户信息");
    result.setRecords(tenantDTOS);
    return result;
  }

  /**
   * 根据租户代码获取租户信息
   *
   * @param tenantCode
   * @return
   */
  @Override
  public ResponseResult<TenantDTO> queryTenantByCode(String tenantCode) {
    ResponseResult<TenantDTO> result = new ResponseResult<TenantDTO>();
    TenantDTO tenantDTO = tenantMapper.getByTenantCode(tenantCode);
    result.setCode(1);
    result.setMessage("查询成功");
    result.setResult(tenantDTO);
    return result;
  }

}
