package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.PrivilegeService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@RestSchema(schemaId = "privilege")
@RequestMapping(path = "/v1/privilege")
public class PrivilegeApi {
	@Autowired
	private PrivilegeService privilegeService;

	/**
	 * 添加角色
	 *
	 * @param role
	 * @return
	 */
	@PostMapping(path = "createRole")
	public ResponseBase createRole(@RequestBody RoleDTO role) {
		return privilegeService.createRole(role);
	}

	/**
	 * 修改角色
	 *
	 * @param role
	 * @return
	 */
	@PutMapping(path = "modifyRole")
	public ResponseBase modifyRole(@RequestBody RoleDTO role) {
		return privilegeService.modifyRole(role);
	}

	/**
	 * 删除角色
	 *
	 * @param roleId
	 * @return
	 */
	@DeleteMapping(path = "deleteRole")
	public ResponseBase deleteRole(
			@NotEmpty(message = "参数roleId不能为空") @ApiParam(name = "roleId", value = "角色id", required = true) String roleId) {
		ResponseBase responseBase = new ResponseBase();
		if (StringUtils.isBlank(roleId)) {
			responseBase.setCode(0);
			responseBase.setMessage("参数roleId不能为空");
			return responseBase;
		}
		return privilegeService.deleteRole(roleId);
	}

	/**
	 * 获取所有角色
	 *
	 * @return
	 */
	@PostMapping(path = "getAllRoles")
	public ResponseRecords<RoleDTO> getAllRoles(@RequestParam(name = "roleName", required = false) String roleName, @RequestParam(name = "roleType", required = false) String roleType) {
		return privilegeService.getAllRoles(roleName, roleType);
	}

	/**
	 * 菜单添加
	 *
	 * @author  yanghu
	 * update 20200402
	 * 增加后台验证
	 * @param menu
	 * @return
	 */
	@PostMapping(path = "createMenu")
	public ResponseBase createMenu(@RequestBody MenuDTO menu) {
		return privilegeService.createMenu(menu);
	}

	/**
	 * 菜单修改
	 *
	 * @param menu
	 * @return
	 */
	@PutMapping(path = "modifyMenu")
	public ResponseBase modifyMenu(@RequestBody MenuDTO menu) {
		return privilegeService.modifyMenu(menu);
	}

	/**
	 * 菜单删除（逻辑删除） 状态设置为-1
	 *
	 * @param menuId
	 * @return
	 */
	@DeleteMapping(path = "deleteMenu")
	public ResponseBase deleteMenu(
			@NotEmpty(message = "参数menuId不能为空") @ApiParam(name = "menuId", value = "菜单id", required = true) String menuId) {
		ResponseBase responseBase = new ResponseBase();
		if (StringUtils.isBlank(menuId)) {
			responseBase.setCode(0);
			responseBase.setMessage("参数menuId不能为空");
			return responseBase;
		}
		return privilegeService.deleteMenu(menuId);
	}
	
	/**
	 * 禁用或启用菜单
	 */
	@PostMapping(path = "enableOrDisablemenus")
	public ResponseBase enableOrDisablemenus(
			@NotEmpty(message = "参数menuId不能为空") @ApiParam(name = "menuId", value = "菜单id", required = true) String menuId,
			@NotEmpty(message = "参数status不能为空") @ApiParam(name = "status", value = "状态值", required = true) String status) {
		ResponseBase responseBase = new ResponseBase();
		if (StringUtils.isBlank(menuId)) {
			responseBase.setCode(0);
			responseBase.setMessage("参数menuId不能为空");
			return responseBase;
		}
		return privilegeService.enableOrDisablemenus(menuId,status);
	}


	/**
	 * 角色菜单配置（设置角色菜单关联关系）
	 *
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@PostMapping(path = "setRoleMenus")
	public ResponseBase setRoleMenus(
			@NotEmpty(message = "参数roleId不能为空") @ApiParam(name = "roleId", value = "角色id", required = true) String roleId,
			@RequestBody MenuIdsAndBtnIdsDTO menuIdsAndBtnIdsDTO) {
		ResponseBase responseBase = new ResponseBase();
		List<String> menuIds = menuIdsAndBtnIdsDTO.getMenuIds();
		List<String> btnIds = menuIdsAndBtnIdsDTO.getBtnIds();
		if (StringUtils.isBlank(roleId)) {
			responseBase.setCode(0);
			responseBase.setMessage("角色ID不能为空");
			return responseBase;
		}
		if (menuIds == null || menuIds.size() <= 0) {
			responseBase.setCode(0);
			responseBase.setMessage("角色菜单ID不能为空");
			return responseBase;
		}
		return privilegeService.setRoleMenus(roleId, menuIds,btnIds);
	}


	/**
	 * 根据角色id查询菜单树
	 *
	 * @param roleId
	 * @return
	 */
	@GetMapping(path = "getResourceZtree")
	public ResponseRecords<ResourceTree> getResourceTree(@RequestParam String roleId) {
		return privilegeService.getResourceTree(roleId);
	}


	/**
	 * 菜单设置按钮
	 *
	 * @param menuButtonDTO
	 * @return
	 */
	@PostMapping("setMenuBtn")
	public ResponseBase setMenuBtn(@RequestBody MenuButtonDTO menuButtonDTO) {
		return privilegeService.setMenuBtn(menuButtonDTO);
	}

	/**
	 * 登录后获取用户所有菜单
	 *
	 * @return
	 */
	@GetMapping("getUserAllMenus")
	public ResponseRecords<MenuDTO> getUserAllMenus() {
		return privilegeService.getUserAllMenus();
	}

	/**
	 * 获取菜单树
	 * @return
	 */
	@GetMapping("getMenuTree")
	public ResponseRecords<MenuDTO> getMenuTree() {
		return privilegeService.getMenuTree();
	}

	/**
	 * 根据orgSeq获取组织机构树
	 * @param orgSeq
	 * @return
	 */
	@GetMapping(path="getOrganizationTreeByOrgSeq")
	public ResponseRecords<OrganizationDTO> getOrganizationTreeByOrgSeq(@RequestParam(value ="orgSeq",required = true) String orgSeq) {
		return privilegeService.getOrganizationTreeByOrgSeq(orgSeq);
	}
}
