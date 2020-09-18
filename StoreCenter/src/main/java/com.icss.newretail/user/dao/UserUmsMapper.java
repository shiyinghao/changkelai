package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.UserUmsReponse;
import com.icss.newretail.user.entity.UserUms;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-04-26
 */
public interface UserUmsMapper extends BaseMapper<UserUms> {

    UserUms selectByVertCode(@Param("phone") String phone);

    Page<UserUmsReponse> getUmsList(@Param("page") Page<UserUmsReponse> page, @Param("para") UserUmsReponse para);
}
