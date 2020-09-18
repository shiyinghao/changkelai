package com.icss.newretail.user.dao;

import com.icss.newretail.user.entity.StoreUseRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 门店使用时长次数记录表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-07-29
 */
@Repository
public interface StoreUseRecordMapper extends BaseMapper<StoreUseRecord> {

	int updateStoreUseRecord(StoreUseRecord storeUseRecord);

}
