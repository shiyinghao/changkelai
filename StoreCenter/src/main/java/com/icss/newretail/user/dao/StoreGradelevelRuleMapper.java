package com.icss.newretail.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.StoreGradelevelRuleDTO;
import com.icss.newretail.model.StoreRuleDTO;
import com.icss.newretail.user.entity.StoreGradelevelRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 门店等级规则 Mapper 接口
 * </p>
 *
 * @author yanghu
 * @since 2020-04-02
 */
@Repository
public interface StoreGradelevelRuleMapper extends BaseMapper<StoreGradelevelRule> {

    List<StoreGradelevelRuleDTO> queryStoreRule(@Param("store") StoreRuleDTO storeRuleDTO);

    List<StoreGradelevelRuleDTO> queryStoreGradelevelRule();

    StoreGradelevelRuleDTO querygoodById(String storeLevelId);

    StoreGradelevelRule maxGradelevel();

    StoreGradelevelRule queryMaxGradeleve();
}
