package com.icss.newretail.user.dao;

import com.icss.newretail.model.StoreBonuspointReq;
import com.icss.newretail.user.entity.StoreBonuspoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>
 * 门店积分表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-04-05
 */
@Repository
public interface StoreBonuspointMapper extends BaseMapper<StoreBonuspoint> {


    Integer updateStoreScore(@Param("uuid") String uuid,@Param("currentScore") BigDecimal currentScore, @Param("totalScore")BigDecimal totalScore);
}
