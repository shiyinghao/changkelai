package com.icss.newretail.user.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.user.entity.TenantSaascall;

@Repository
public interface TenantSaascallMapper extends BaseMapper<TenantSaascall> {
	TenantSaascall getTenantSaascallByOrderId(String orderId);
}
