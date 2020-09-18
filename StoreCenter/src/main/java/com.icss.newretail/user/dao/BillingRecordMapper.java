package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.BillingRecordDTO;
import com.icss.newretail.model.FeedbackDTO;
import com.icss.newretail.user.entity.BillingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 店铺开票记录 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-05-26
 */
public interface BillingRecordMapper extends BaseMapper<BillingRecord> {

    Page<BillingRecordDTO> queryBillingRecord(@Param("pg") Page<BillingRecordDTO> page,@Param("para") BillingRecordDTO condition);

    BillingRecordDTO queryBillingRecordById(@Param("billId") String billId);
}
