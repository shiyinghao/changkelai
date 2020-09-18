package com.icss.newretail.service.user;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.TenantDTO;
import com.icss.newretail.model.TenantRequest;

/**
 * <p>
 * 平台租户 服务类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-27
 */
public interface TenantService{

  /**
   * 创建租户
   * 
   * @param para
   * @return
   */
  public ResponseBase createTenant(TenantDTO para);

  /**
   * 更新租户
   * 
   * @param para
   * @return
   */
  public ResponseBase modifyTenant(TenantDTO para);

  /**
   * 查询租户明细
   * 
   * @param tenantId
   * @return
   */
  public ResponseResult<TenantDTO> queryTenantById(String tenantId);

  /**
   * 查询租户列表
   * 
   * @param para
   * @return
   */
  public ResponseRecords<TenantDTO> queryTenants(TenantRequest para);

  /**
   * 根据租户代码获取明细
   * 
   * @param tenantCode
   * @return
   */
  public ResponseResult<TenantDTO> queryTenantByCode(String tenantCode);
  

}
