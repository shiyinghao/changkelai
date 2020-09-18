package com.icss.newretail.user.dao;

import com.icss.newretail.model.ActivationCountDTO;
import com.icss.newretail.user.entity.ShopLoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商家端登录记录表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-07-28
 */
@Repository
public interface ShopLoginRecordMapper extends BaseMapper<ShopLoginRecord> {

	ActivationCountDTO queryCount(@Param("beginDate")String beginDate,@Param("endDate")String endDate);

}
