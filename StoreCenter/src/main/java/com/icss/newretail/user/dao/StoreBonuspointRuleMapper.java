package com.icss.newretail.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.StoreBonuspointRuleDTO;
import com.icss.newretail.user.entity.StoreBonuspointRule;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 门店积分绑定表 Mapper 接口
 * </p>
 *
 * @author yanghu
 * @since 2020-04-02
 */
@Repository
public interface StoreBonuspointRuleMapper extends BaseMapper<StoreBonuspointRule> {


    StoreBonuspointRuleDTO queryStoreBonuspointOne();

}
