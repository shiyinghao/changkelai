package com.icss.newretail.user.dao;

import com.icss.newretail.user.entity.TenantTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 平台租户标题 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-28
 */
public interface TenantTitleMapper extends BaseMapper<TenantTitle> {

  void deleteByTenantId(String tenantId);
}
