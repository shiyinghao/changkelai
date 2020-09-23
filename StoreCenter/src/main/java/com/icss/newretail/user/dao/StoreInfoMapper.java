package com.icss.newretail.user.dao;

import com.icss.newretail.user.entity.StoreInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 门店信息 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-04-14
 */
@Repository
public interface StoreInfoMapper extends BaseMapper<StoreInfo> {

    Integer updateGradelevel(String orgSeq);

    Integer updateGradelevelOne(@Param("storeGradelevel") String storeGradelevel, @Param("orgSeq") String orgSeq);
}
