package com.icss.newretail.user.dao;

import com.icss.newretail.user.entity.UserLoginInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * PC登录信息表 Mapper 接口
 * </p>
 *
 * @author mx
 * @since 2020-06-11
 */
@Repository
public interface UserLoginInfoMapper extends BaseMapper<UserLoginInfo> {

	Integer updateUserLoginInfo(UserLoginInfo userLoginInfo);

}
