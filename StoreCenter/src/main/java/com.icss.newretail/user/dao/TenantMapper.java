package com.icss.newretail.user.dao;

import com.icss.newretail.model.TenantDTO;
import com.icss.newretail.model.TenantRequest;
import com.icss.newretail.user.entity.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 平台租户 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-27
 */
@Repository
public interface TenantMapper extends BaseMapper<Tenant> {

  TenantDTO getByTenantCode(String tenantCode);

  TenantDTO queryTenantById(String tenantId);

  List<TenantDTO> queryTenants(@Param("request") TenantRequest request);
  
  String queryLastTenantId();
}
