package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.PrivilegeService;
import com.icss.newretail.user.dao.MenuButtonMapper;
import com.icss.newretail.user.dao.UserAppMapper;
import com.icss.newretail.user.dao.UserMenuMapper;
import com.icss.newretail.user.dao.UserModuleMapper;
import com.icss.newretail.user.dao.UserOperatorLogMapper;
import com.icss.newretail.user.dao.UserOperatorTypeMapper;
import com.icss.newretail.user.dao.UserRoleMapper;
import com.icss.newretail.user.entity.*;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PrivilegeServiceImpl implements PrivilegeService {
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private UserMenuMapper userMenuMapper;
	@Autowired
	private UserModuleMapper userModuleMapper;
	@Autowired
	private UserAppMapper userAppMapper;
	@Autowired
	private UserOperatorTypeMapper operatorTypeMapper;
	@Autowired
	private UserOperatorLogMapper operatorLogMapper;

	@Autowired
	private MenuButtonMapper menuButtonMapper;

	/**
	 * 添加角色
	 *
	 * @param role
	 * @return
	 */
	@Override
	public ResponseBase createRole(RoleDTO role) {
		ResponseBase base = new ResponseBase();
		try {
			role.setRoleId(UUID.randomUUID().toString());
			role.setRoleReq(System.currentTimeMillis() + "");
			UserRole userRole = Object2ObjectUtil.parseObject(role, UserRole.class);
			userRole.setCreateTime(new Date());
			userRole.setUpateTime(new Date());
			userRole.setCreateUser(JwtTokenUtil.currUser());
			userRole.setUpdateUser(JwtTokenUtil.currUser());
			int count = userRoleMapper.insert(userRole);
			if (count > 0) {
				base.setCode(1);
				base.setMessage("用户角色添加成功");
			} else {
				base.setMessage("用户角色添加失败");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|createRole->添加角色" + ex.getLocalizedMessage());
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	/**
	 * 修改角色
	 *
	 * @param role
	 * @return
	 */
	@Override
	public ResponseBase modifyRole(RoleDTO role) {
		ResponseBase base = new ResponseBase();
		try {
			role.setUpateTime(new Date());
			role.setUpdateUser(JwtTokenUtil.currUser());
			UserRole userRole = Object2ObjectUtil.parseObject(role, UserRole.class);
			Integer i = userRoleMapper.updateById(userRole);
			if (i > 0) {
				base.setCode(1);
				base.setMessage("角色修改成功");
			} else {
				base.setMessage("角色修改失败");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("PrivilegeServiceImpl|modifyRole->修改角色" + ex.getLocalizedMessage());
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	/**
	 * 删除角色
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public ResponseBase deleteRole(String roleId) {
		ResponseBase base = new ResponseBase();
		try {
			int i = userRoleMapper.deleteById(roleId);
			if (i > 0) {
				base.setCode(1);
				base.setMessage("角色删除成功");
			} else {
				base.setMessage("角色删除失败");
			}
		} catch (Exception ex) {
			log.info("PrivilegeServiceImpl|deleteRole->删除角色:" + ex.getLocalizedMessage());
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	@Override
	public ResponseResult<RoleDTO> queryRolesByUserId(String s) {
		return null;
	}

	/**
	 * 获取所有角色
	 *
	 * @return
	 */
	@Override
	public ResponseRecords<RoleDTO> getAllRoles(String roleName, String roleType) {
		ResponseRecords<RoleDTO> result = new ResponseRecords<>();
		try {
			List<RoleDTO> roleDTOList = userRoleMapper.getAllRoles(roleName, roleType);
			result.setCode(1);
			result.setMessage("共有" + roleDTOList.size() + "条角色信息");
			result.setRecords(roleDTOList);
		} catch (Exception ex) {
			result.setCode(0);
			log.error("PrivilegeServiceImpl|getAllRoles->获取所有角色:" + ex.getMessage());
			result.setMessage(ex.getMessage());
			result.setRecords(new ArrayList<>());
		}
		return result;
	}

	/**
	 * 菜单添加
	 *
	 * @param menu
	 * @return
	 */
	@Override
	public ResponseBase createMenu(MenuDTO menu) {
		ResponseBase base = new ResponseBase();
		try {
			UserMenu userMenu = Object2ObjectUtil.parseObject(menu, UserMenu.class);
			if (StringUtils.isBlank(userMenu.getUpMenuId())) {
				userMenu.setResourceLevel(1);
			} else {
				UserMenu upMenu = userMenuMapper.selectById(userMenu.getUpMenuId());
				userMenu.setResourceLevel(upMenu.getResourceLevel() + 1);
			}
			userMenu.setMenuId(UUID.randomUUID().toString());
			userMenu.setCreateUser(JwtTokenUtil.currUser());
			userMenu.setUpdateUser(JwtTokenUtil.currUser());
			userMenu.setCreateTime(LocalDateTime.now());
			userMenu.setUpdateTime(LocalDateTime.now());
			userMenu.setStatus(1);
			userMenuMapper.insert(userMenu);  //添加菜单
			base.setMessage("菜单添加成功");
			base.setCode(1);
		} catch (Exception ex) {
			base.setCode(0);
			log.error("PrivilegeServiceImpl|createMenu->添加菜单:" + ex.getLocalizedMessage());
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	// 1.生成菜单主键 2.根据菜单等级生成相应菜单编码
	@Transactional
	public UserMenu getPrefectMenu(MenuDTO menu) throws Exception {
		//根据appId和moduleId获取模块应用关系id
		String appModuleRelationId = userMenuMapper.getAppModuleRelation(menu.getAppId(), menu.getModuleId());
		UserMenu userMenu = Object2ObjectUtil.parseObject(menu, UserMenu.class);
		if (appModuleRelationId != null) {
			userMenu.setAppModuleRelationId(appModuleRelationId);
			userMenu.setMenuId(UUID.randomUUID().toString());
			userMenu.setCreateUser(JwtTokenUtil.currUser());
			//判断传过来的资源等级为null设置资源等级为1
			if (StringUtils.isBlank(menu.getUpResourceReq())) {
				userMenu.setResourceLevel(1);
			} else {
				//判断添加的菜单对象的上级菜单编码对应的菜单对象是否为二级菜单
				MenuDTO m = userMenuMapper.queryMenuByMenuReq(menu.getUpResourceReq());
				if (m.getResourceLevel() == 1) {
					userMenu.setResourceLevel(2);
				} else {
					throw new Exception("不能添加三级菜单");
				}
			}
			String latestResourceReq = userMenuMapper.queryLatestResourceReq(menu.getResourceLevel(), menu.getUpResourceReq());
			String prefectResourceReq = null;
			if (latestResourceReq == null) {
				if (menu.getResourceLevel() != null && menu.getResourceLevel() == 1) {
					prefectResourceReq = "101";
				} else {
					prefectResourceReq = menu.getUpResourceReq() + "001";
				}
			} else {
				prefectResourceReq = (Integer.parseInt(latestResourceReq) + 1) + "";
			}
			userMenu.setResourceReq(prefectResourceReq);
		} else {
			throw new Exception("appModuleRelationId不存在");
		}
		return userMenu;
	}

	/**
	 * 菜单修改
	 *
	 * @param menu
	 * @return
	 */
	@Override
	public ResponseBase modifyMenu(MenuDTO menu) {
		ResponseBase base = new ResponseBase();
		try {
			UserMenu userMenu = Object2ObjectUtil.parseObject(menu, UserMenu.class);
			userMenu.setUpdateUser(JwtTokenUtil.currUser());
			userMenu.setUpdateTime(LocalDateTime.now());
			Integer i = userMenuMapper.updateById(userMenu);
			base.setCode(1);
			base.setMessage("菜单信息修改成功");
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|modifyMenu->修改菜单:" + ex.getLocalizedMessage());
			base.setMessage(ex.getMessage());
			base.setCode(0);
		}
		return base;
	}

	/**
	 * 菜单删除（逻辑删除） 状态设置为-1
	 *
	 * @param menuId
	 * @return
	 */
	@Override
	public ResponseBase deleteMenu(String menuId) {
		ResponseBase base = new ResponseBase();
		try {
			Integer count = userMenuMapper.deleteMenu(menuId);
			if (count > 0) {
				base.setCode(1);
				base.setMessage("菜单逻辑删除成功");
			} else {
				base.setMessage("菜单逻辑删除失败");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|deleteMenu->删除菜单(逻辑删除,将菜单状态设置为-1)");
			base.setMessage(ex.getMessage());
			base.setCode(0);
		}
		return base;
	}
	
	/**
	 * 禁用启用菜单 0禁用 1启用
	 */
	public ResponseBase enableOrDisablemenus(String menuId,String status){
		ResponseBase base = new ResponseBase();
		try {
			Integer count = userMenuMapper.enableOrDisablemenus(menuId,status);
			if (count > 0) {
				base.setCode(1);
				base.setMessage("菜单禁用或启用成功");
			} else {
				base.setMessage("菜单禁用或启用失败");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|enableOrDisablemenus->菜单禁用或启用(0禁用 1启用)");
			base.setMessage(ex.getMessage());
			base.setCode(0);
		}
		return base;
	}

	/**
	 * 菜单查询（全部）
	 *
	 * @return
	 */
	@Override
	public ResponseRecords<MenuDTO> getAllMenus(String appId, String roleId) {
		ResponseRecords<MenuDTO> result = new ResponseRecords<>();
		try {
			List<MenuDTO> list = userMenuMapper.getAllMenus(appId, roleId);
			result.setCode(1);
			result.setRecords(list);
			result.setMessage("共有" + list.size() + "条菜单信息");
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|getAllMenus->获取所有菜单:" + ex.getMessage());
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	/**
	 * 菜单查询（根据应用Id查询）
	 *
	 * @param
	 * @return
	 */
	@Override
	public ResponseRecords<MenuDTO> queryMenus(String moduleId, String upResourceReq, String resourceLevel) {
		ResponseRecords<MenuDTO> result = new ResponseRecords<>();
		try {
			List<MenuDTO> list = userMenuMapper.queryMenus(moduleId, upResourceReq, resourceLevel);
			if (list.size() > 0) {
				result.setCode(1);
				result.setMessage("根据条件,共有" + list.size() + "条菜单信息");
				result.setRecords(list);
			} else {
				result.setMessage("没有菜单信息");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|queryMenusByAppId->根据应用appId获取对应菜单:" + ex.getLocalizedMessage());
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	/**
	 * 角色菜单配置（设置角色菜单关联关系）
	 *
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public ResponseBase setRoleMenus(String roleId, List<String> menuIds ,List<String> btnIds) {
		ResponseBase responseBase = new ResponseBase();
		try {
			RoleDTO userRole = userRoleMapper.getUserRoleByRoleId(roleId);
			if (userRole == null) {
				throw new RuntimeException("角色不存在");
			}
			List<UserMenu> userMenus = userMenuMapper.getUserMenuByMenuIds(menuIds);
			if (userMenus == null || userMenus.size() <= 0) {
				throw new RuntimeException("未查询到对应菜单信息");
			}
			List<UserRoleMenuRelation> roleMenuRelationList = new ArrayList<>();
			for (UserMenu userMenu : userMenus) {
				UserRoleMenuRelation roleMenuRelation = new UserRoleMenuRelation();
				roleMenuRelation.setRoleMenuRelation(UUID.randomUUID().toString());
				roleMenuRelation.setMenuId(userMenu.getMenuId());
				roleMenuRelation.setRoleId(userRole.getRoleId());
				roleMenuRelation.setRoleReq(userRole.getRoleReq());
				roleMenuRelation.setStatus(1);
				roleMenuRelation.setCreateUser(JwtTokenUtil.currUser());
				roleMenuRelation.setUpdateUser(JwtTokenUtil.currUser());
				roleMenuRelation.setCreateTime(LocalDateTime.now());
				roleMenuRelation.setUpdateTime(LocalDateTime.now());
				roleMenuRelationList.add(roleMenuRelation);
			}
			userMenuMapper.batchDelUserMenuByRodeIdAndMenuId(roleId);
			int res = userMenuMapper.batchAddRoleMenuRelaction(roleMenuRelationList);

			int rep = 0;
			if (btnIds != null && btnIds.size() > 0) {
				List<MenuButton> menuButtons = menuButtonMapper.getMenuButtonByMenuIds(btnIds);
				if (menuButtons == null || menuButtons.size() <= 0) {
					throw new RuntimeException("未查询到对应菜单按钮信息");
				}

				List<UserRoleBtn> roleMenuButtonList = new ArrayList<>();
				for (MenuButton menuBtn : menuButtons) {
					UserRoleBtn userRoleBtn = new UserRoleBtn();
					userRoleBtn.setUuid(UUID.randomUUID().toString());
					userRoleBtn.setRoleId(userRole.getRoleId());
					userRoleBtn.setBtnId(menuBtn.getUuid());
					userRoleBtn.setCreateUser(JwtTokenUtil.currUser());
					userRoleBtn.setUpdateUser(JwtTokenUtil.currUser());
					userRoleBtn.setCreateTime(LocalDateTime.now());
					userRoleBtn.setUpdateTime(LocalDateTime.now());
					roleMenuButtonList.add(userRoleBtn);
				}

				userMenuMapper.batchDelUserMenuBtnByRodeId(roleId);
				rep = userMenuMapper.batchAddRoleMenuBtn(roleMenuButtonList);
			}
			if (res > 0 || rep > 0) {
				responseBase.setCode(1);
				responseBase.setMessage("新增角色菜单按钮关联关系成功");
			} else {
				throw new RuntimeException("新增角色菜单按钮关联关系失败");
			}
		} catch (Exception e) {
			responseBase.setCode(0);
			responseBase.setMessage(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
			log.error("PrivilegeServiceImpl|setRoleMenus|" + e.getMessage());
		}
		return responseBase;
	}

	/**
	 * 角色菜单配置查询（获取角色关联菜单）
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public ResponseBase getRoleMenus(String roleId) {
		ResponseRecords<UserMenu> responseResult = new ResponseRecords<>();
		try {
			List<UserMenu> list = userMenuMapper.getUserMenuByRoleId(roleId);
			responseResult.setCode(1);
			responseResult.setRecords(list);
		} catch (Exception e) {
			responseResult.setCode(0);
			responseResult.setMessage(e.getMessage());
			log.error("PrivilegeServiceImpl|getRoleMenus|" + e.getMessage());
		}
		return responseResult;
	}

	/**
	 * 添加应用
	 *
	 * @param module
	 * @return
	 */
	@Override
	public ResponseBase createModule(ModuleDTO module) {
		ResponseBase base = new ResponseBase();
		try {
			UserModule userModule = new UserModule(module);
			userModule.setModuleId(UUID.randomUUID().toString());
			int count = userModuleMapper.insert(userModule);
			if (count > 0) {
				base.setCode(1);
				base.setMessage("模块添加成功");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|createModule->添加应用[" + ex.getLocalizedMessage() + "]");
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	/**
	 * 修改应用
	 *
	 * @param module
	 * @return
	 */
	@Override
	public ResponseBase modifyModule(ModuleDTO module) {
		ResponseBase base = new ResponseBase();
		try {
			UserModule userModule = new UserModule(module);
			int count = userModuleMapper.updateById(userModule);
			if (count > 0) {
				base.setCode(1);
				base.setMessage("修改应用成功");
			} else {
				log.info("修改userModule=" + userModule + "，失败");
				base.setMessage("修改应用失败");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|modifyModule->修改应用[" + ex.getLocalizedMessage() + "]");
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	/**
	 * 删除应用
	 *
	 * @param moduleId
	 * @return
	 */
	@Override
	public ResponseBase deleteModule(String moduleId) {
		ResponseBase base = new ResponseBase();
		try {
			int count = userModuleMapper.deleteById(moduleId);
			if (count > 0) {
				base.setCode(1);
				base.setMessage("应用删除成功");
			} else {
				log.info("删除moduleId=" + moduleId + "的应用失败");
				base.setMessage("应用删除失败");
			}
		} catch (Exception ex) {
			log.error("PrivilegeServiceImpl|deleteModule->删除应用[" + ex.getLocalizedMessage() + "]");
			base.setMessage(ex.getMessage());
		}
		return base;
	}

	/**
	 * 查询模块明细
	 *
	 * @return
	 */
	@Override
	public ResponseBase queryModuleById(String moduleId) {
		ResponseResult<ModuleDTO> result = new ResponseResult<>();
		try {
			ModuleDTO userModule = userModuleMapper.queryModuleById(moduleId);
			result.setCode(1);
			result.setResult(userModule);
			if (userModule != null) {
				result.setMessage("查询成功");
				log.info("根据moduleId查询模块明细成功--" + userModule);
			} else {
				result.setMessage("查询失败");
			}
		} catch (Exception ex) {
			result.setMessage(ex.getMessage());
			log.error("PrivilegeServiceImpl|queryModuleById->查询模块明细[" + ex.getLocalizedMessage() + "]");
		}
		return result;
	}

	/**
	 * 查询应用下用户有权限的模块
	 *
	 * @return
	 */
	@Override
	public ResponseRecords<ModuleDTO> queryModules(String appId, String userId) {
		ResponseRecords<ModuleDTO> result = new ResponseRecords<>();
		try {
			List<ModuleDTO> list = userModuleMapper.queryModules(appId, userId);
			result.setCode(1);
			result.setMessage("共有" + list.size() + "个应用模块");
			result.setRecords(list);
			log.info("共有" + list.size() + "个应用模块");
		} catch (Exception ex) {
			result.setMessage(ex.getMessage());
			log.error("PrivilegeServiceImpl|queryModules->查询应用下用户有权限的模块[" + ex.getLocalizedMessage() + "]");
		}
		return result;
	}

	/**
	 * 查询用户应用
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public ResponseRecords<App> queryAppsByUserId(String userId) {
		ResponseRecords<App> result = new ResponseRecords<App>();
		try {
			List<App> userApps = userAppMapper.getAppsByUserId(userId);
			result.setCode(1);
			result.setMessage("查询共有" + userApps.size() + "个应用");
			result.setRecords(userApps);
			log.info("查询共有" + userApps.size() + "个应用");
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("PrivilegeServiceImpl|queryAppsByUserId->查询用户应用[" + ex.getLocalizedMessage() + "]");
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	@Override
	public ResponseRecords<ResourceTree> getResourceTree(String roleId) {
		ResponseRecords<ResourceTree> responseRecords = new ResponseRecords<>();
		List<ResourceTree> list = userMenuMapper.getResourceTree(roleId);
		responseRecords.setRecords(list);
		responseRecords.setCode(1);
		responseRecords.setMessage("查询成功");
		return responseRecords;
	}

	/**
	 * 根据应用id和菜单资源编码或模块id查询菜单分页
	 *
	 * @param pageData
	 * @return
	 */
	@Override
	public ResponseResultPage<MenuDTO> queryMenusPageByMenuRequest(PageData<MenuRequest> pageData) {
		ResponseResultPage<MenuDTO> result = new ResponseResultPage<>();
		try {
			Page<MenuDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
			Page<MenuDTO> menuResult = null;
			if (StringUtils.isBlank(pageData.getCondition().getId()) || pageData.getCondition().getId().equals("1")) {
				menuResult = userMenuMapper.queryMenusPageByMenuRequest(page, pageData.getCondition(), "1");
			} else {
				//根据flag判断id类型  "2"->一级菜单资源编码 "3"->二级菜单资源编码*/
				String flag = "2";
				MenuDTO menu = userMenuMapper.queryMenuByMenuReq(pageData.getCondition().getId());
				if (menu.getResourceLevel() == 2) {
					flag = "3";
				}
				menuResult = userMenuMapper.queryMenusPageByMenuRequest(page, pageData.getCondition(), flag);
			}
			result.setCode(1);
			result.setMessage("查询成功");
			result.setTotal(menuResult.getTotal());
			result.setCurrent(pageData.getCurrent());
			result.setRecords(menuResult.getRecords());
			result.setSize(pageData.getSize());
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(0);
			result.setMessage(ex.getMessage());
			log.error("PrivilegeServiceImpl|queryMenusPageByMenuRequest->根据应用id和菜单id或模块id查询菜单分页[" + ex.getMessage() + "]");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
		}
		return result;
	}

	/**
	 * 根据operate(操作类型)/id(菜单资源编码)/appId(应用ID)/uuid(菜单主键) 查询菜单
	 *
	 * @return
	 */
	@Override
	@Transactional
	public ResponseResult<Map<String, Object>> queryMenusByMenuRequest(MenuRequest menuRequest) {
		ResponseResult<Map<String, Object>> result = new ResponseResult<>();
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			if (menuRequest.getOperate().equals("edit")) {
				MenuDTO menu = userMenuMapper.queryMenuByMenuId(menuRequest.getUUID());
				List<MenuDTO> menus = userMenuMapper.queryMenusByMenuRequest(menuRequest);
				data.put("menu", menu);
				data.put("menus", menus);
			} else {
				MenuDTO menu = userMenuMapper.queryMenuByMenuReq(menuRequest.getId());
				data.put("menu", menu);
			}
			result.setCode(1);
			result.setMessage("数据获取成功");
			result.setResult(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setCode(0);
			result.setMessage(ex.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("PrivilegeServiceImpl|queryMenusByMenuRequest->根据operate(操作类型)/id(菜单资源或者模块ID)/appId(应用ID)/uuid(菜单主键) 查询模块数据或者模块和菜单数据[" + ex.getLocalizedMessage() + "]");
		}
		return result;
	}

	/**
	 * 用户菜单关联角色权限、用户操作记录，去重并按照时间倒序返回用户有权限的、可用菜单列表 t_user_operator_log 用户操作记录
	 *
	 * @param userId 为空则根据tocken解析
	 * @param num    默认8个
	 * @return
	 */
	@Override
	public ResponseRecords<MenuDTO> myMenuTracks(String userId, int num) {
		ResponseRecords<MenuDTO> responseResult = new ResponseRecords<>();
		// userId 为空则根据tocken解析
		if (StringUtils.isBlank(userId)) {
			userId = JwtTokenUtil.currUser();
		}
		String tenantId = JwtTokenUtil.currTenant();
		// 默认8个
		if (num == 0) {
			num = 8;
		}
		List<MenuDTO> dtos = userMenuMapper.myMenuTracks(userId, tenantId, num);
		responseResult.setRecords(dtos);
		responseResult.setCode(1);
		responseResult.setMessage("共查询到" + dtos.size() + "条记录");

		return responseResult;
	}

	/**
	 * 保存用户菜单访问记录
	 *
	 * @param userId
	 * @param menuId
	 * @return
	 */
	@Transactional
	public ResponseBase trackMenuVisitor(String userId, String menuId) {
		ResponseBase responseBase = new ResponseBase();
		String tenantId = JwtTokenUtil.currTenant();
		if (StringUtils.isBlank(userId)) {
			userId = JwtTokenUtil.currUser();
		}
		// 获取操作类型
		UserOperatorType operatorType = operatorTypeMapper.getByOperatorTypeName("菜单访问");
		UserOperatorLog operatorLog = new UserOperatorLog(operatorType, userId, menuId, tenantId);
		operatorLog.setOperationId(UUID.randomUUID().toString());
		int result = operatorLogMapper.insert(operatorLog);
		if (result > 0) {
			responseBase.setCode(1);
			responseBase.setMessage("用户菜单访问记录保存成功");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("用户菜单访问记录保存失败");
		}
		return responseBase;
	}

	/**
	 * 菜单设置按钮
	 *
	 * @param menuButtonDTO
	 * @return
	 */
	@Override
	public ResponseBase setMenuBtn(MenuButtonDTO menuButtonDTO) {
		ResponseBase responseBase = new ResponseBase();
		MenuButton menuButton = menuButtonMapper.selectOne(
				new QueryWrapper<MenuButton>().eq("btn_code", menuButtonDTO.getBtnCode()).eq("status", 1));
		if (menuButton != null) {
			responseBase.setCode(0);
			responseBase.setMessage("按钮编码重复");
			return responseBase;
		}
		menuButton = new MenuButton();
		BeanUtils.copyProperties(menuButtonDTO, menuButton);
		menuButton.setUuid(UUID.randomUUID().toString());
		menuButton.setStatus(1);
		menuButton.setCreateUser(JwtTokenUtil.currUser());
		menuButton.setUpdateUser(JwtTokenUtil.currUser());
		menuButton.setCreateTime(LocalDateTime.now());
		menuButton.setUpdateTime(LocalDateTime.now());
		Integer res = menuButtonMapper.insert(menuButton);
		if (res > 0) {
			responseBase.setCode(1);
			responseBase.setMessage("按钮保存成功");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("按钮保存失败");
		}
		return responseBase;
	}

	@Override
	public ResponseRecords<MenuDTO> getUserAllMenus() {
		ResponseRecords<MenuDTO> responseRecords = new ResponseRecords<>();
		String userId = JwtTokenUtil.currUser();
		try {
			List<MenuDTO> upMenuList = userMenuMapper.getUpMenuByUserId(userId);
			for (MenuDTO menuDTO : upMenuList) {
				menuDTO.setChildren(getSonMenus(userId, menuDTO.getMenuId()));
				//menuDTO.setBtnChildren(getBtnMenus(userId, menuDTO.getMenuId()));
			}
			responseRecords.setRecords(upMenuList);
			responseRecords.setCode(1);
			responseRecords.setMessage("查询成功");
		} catch (Exception e) {
			log.error("查询失败:{}", e.getMessage());
			responseRecords.setCode(0);
			responseRecords.setMessage("查询失败");
		}
		return responseRecords;
	}

	public List<MenuDTO> getSonMenus(String userId, String upId) {
		List<MenuDTO> sonMenuList = userMenuMapper.getSonMenuByUserId(userId, upId);
		for (MenuDTO menuDTO : sonMenuList) {
			menuDTO.setChildren(getBtnMenus(userId, menuDTO.getMenuId()));
			//menuDTO.setChildren(getBtnMenus(userId, menuDTO.getMenuId()));
		}
		return sonMenuList;
	}

	@Override
	public ResponseRecords<MenuDTO> getMenuTree() {
		ResponseRecords<MenuDTO> responseRecords = new ResponseRecords<>();
		List<MenuDTO> list = userMenuMapper.getMenuTree();
		responseRecords.setCode(1);
		responseRecords.setRecords(list);
		responseRecords.setMessage("查询成功");
		return responseRecords;
	}

	public List<MenuDTO> getBtnMenus(String userId, String upId) {
		List<MenuDTO> sonBtnMenuList = userMenuMapper.getBtnMenuByUserId(userId, upId);
		for (MenuDTO menuDTO : sonBtnMenuList) {
			menuDTO.setChildren(getBtnMenus(userId, menuDTO.getMenuId()));
		}
		return sonBtnMenuList;
	}

	@Override
	public ResponseRecords<OrganizationDTO> getOrganizationTreeByOrgSeq(String orgSeq) {
		ResponseRecords<OrganizationDTO> responseRecords = new ResponseRecords<>();
		List<OrganizationDTO> list = userMenuMapper.getOrganizationTreeByOrgSeq(orgSeq);
		responseRecords.setCode(1);
		responseRecords.setRecords(list);
		responseRecords.setMessage("查询成功");
		return responseRecords;
	}
	
}
