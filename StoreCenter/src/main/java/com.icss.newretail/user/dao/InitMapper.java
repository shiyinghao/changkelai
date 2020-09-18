package com.icss.newretail.user.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: jc
 * @CreateDate: 2019/11/8 10:16
 */
public interface InitMapper {
	void initApp(String tenantId);

	void initModule(String tenantId);

	void initAppModuleRelation(String tenantId);

	void initMenu(String tenantId);

	void initRoleMenuRelation(@Param("roleId") String roleId, @Param("tenantId")String tenantId);
}
