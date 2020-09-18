package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.RoleDTO;
import com.icss.newretail.user.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

	List<RoleDTO> getAllRoles(@Param("roleName") String roleName, @Param("roleType") String roleType);

	/**
	 * 根据用户ID 获取用户角色
	 * * @param userId
	 *
	 * @return
	 */
	List<RoleDTO> getUserRolesByUserId(@Param("userId") String userId);

	/**
	 * 根据角色ID 获取用户角色
	 *
	 * @param roleId
	 * @return
	 */
	RoleDTO getUserRoleByRoleId(String roleId);

}
