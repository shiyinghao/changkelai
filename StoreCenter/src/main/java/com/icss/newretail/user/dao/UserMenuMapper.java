package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.user.entity.UserMenu;
import com.icss.newretail.user.entity.UserRoleBtn;
import com.icss.newretail.user.entity.UserRoleMenuRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMenuMapper extends BaseMapper<UserMenu> {
	Integer deleteMenu(@Param("menuId") String menuId);
	
	Integer enableOrDisablemenus(@Param("menuId") String menuId, @Param("status") String status);
	
	List<MenuDTO> getAllMenus(@Param("appId") String appId, @Param("roleId") String roleId);

	List<MenuDTO> queryMenus(@Param("moduleId") String moduleId, @Param("upResourceReq") String upResourceReq,
                             @Param("resourceLevel") String resourceLevel);

	/**
	 * 根据用户ID查询用户菜单
	 *
	 * @param userId
	 * @return
	 */
	List<MenuDTO> getUserMenuByUserId(String userId);

	/**
	 * 根据菜单ID集合查询菜单
	 *
	 * @param list
	 * @return
	 */
	List<UserMenu> getUserMenuByMenuIds(List<String> list);

	/**
	 * 批量新增角色用户关联关系
	 *
	 * @param list
	 * @return
	 */
	int batchAddRoleMenuRelaction(List<UserRoleMenuRelation> list);

	/**
	 * 根据用户角色ID查询菜单
	 *
	 * @param roleId
	 * @return
	 */
	List<UserMenu> getUserMenuByRoleId(String roleId);

	int batchDelUserMenuByRodeIdAndMenuId(@Param("roleId") String roleId);

	List<MenuDTO> getUserMenus(UserMenuRequest para);

	String queryLatestResourceReq(@Param("resourceLevel") Integer resourceLevel, @Param("upResourceReq") String upResourceReq);

	List<MenuDTO> myMenuTracks(@Param("userId") String userId, @Param("tenantId") String tenantId,
                               @Param("num") int num);


	Page<MenuDTO> queryMenusPageByMenuRequest(@Param("page") Page<MenuDTO> page, @Param("menuRequest") MenuRequest condition, @Param("flag") String flag);

	MenuDTO queryMenuByMenuReq(@Param("id") String id);

	List<MenuDTO> queryMenusByMenuRequest(MenuRequest menuRequest);

	MenuDTO queryMenuByMenuId(@Param("uuid") String uuid);

	String getAppModuleRelation(@Param("appId") String appId, @Param("moduleId") String moduleId);

	List<MenuDTO> getUpMenuByUserId(@Param("userId") String userId);

	List<MenuDTO> getSonMenuByUserId(@Param("userId") String userId, @Param("upId") String upId);

	List<ResourceTree> getResourceTree(@Param("roleId") String roleId);

	List<MenuDTO> getMenuTree();

	int batchDelUserMenuBtnByRodeId(@Param("roleId") String roleId);

	/**
	 * 批量新增角色用户与按钮关联关系
	 *
	 * @param list
	 * @return
	 */
	int batchAddRoleMenuBtn(List<UserRoleBtn> roleMenuButtonList);
	
	
	List<MenuDTO> getBtnMenuByUserId(@Param("userId") String userId, @Param("upId") String upId);


	List<OrganizationDTO> getOrganizationTreeByOrgSeq(@Param("orgSeq") String orgSeq);
}
