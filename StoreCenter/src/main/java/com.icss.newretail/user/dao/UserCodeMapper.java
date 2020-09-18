package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.UserAuthCodeReq;
import com.icss.newretail.user.entity.UserAuthCode;
import org.apache.ibatis.annotations.Param;

public interface UserCodeMapper extends BaseMapper<UserAuthCode> {

    int insertCode(@Param("userAuthCodeReq")UserAuthCodeReq userAuthCodeReq);

    UserAuthCode selectByPhAndCode(@Param("phone") String phone);

}
