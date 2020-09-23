package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.LoginInRequest;
import com.icss.newretail.model.LoginRequest;
import com.icss.newretail.model.LoginUserDTO;
import com.icss.newretail.model.LoginUserPasswordDTO;
import com.icss.newretail.model.OrgList;
import com.icss.newretail.model.RoleList;
import com.icss.newretail.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 登录 Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-04-08
 */
@Repository
public interface LoginMapper extends BaseMapper<UserInfo> {

	LoginUserDTO loginIn(LoginInRequest loginRequest);

	void updateLoginTime(UserInfo userInfo);

	LoginUserDTO getUserByAccount(LoginRequest loginRequest);

	void updateWrongCount(UserInfo userInfo);

	void lockAndUnlockUser(UserInfo userInfo);

	List<OrgList> findOrgByUserId(String userId);

	List<RoleList> findRoleByUserId(String userId);

	LoginUserDTO login(LoginRequest loginRequest);

	LoginUserDTO telLogin(LoginUserPasswordDTO para);

	String queryAccount(@Param("authId") String authId, @Param("phone") String phone);

}
