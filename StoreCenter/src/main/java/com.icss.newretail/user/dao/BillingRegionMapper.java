package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.BillingRecordDTO;
import com.icss.newretail.model.BillingRegionDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.user.entity.BillingRegion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 店铺开票拨号表 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-05-26
 */
public interface BillingRegionMapper extends BaseMapper<BillingRegion> {

    Page<BillingRegionDTO> queryPage(Page<BillingRegionDTO> page, PageData<BillingRecordDTO> para);
}
