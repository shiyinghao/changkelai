package com.icss.newretail.service.user;

import com.icss.newretail.model.App;
import com.icss.newretail.model.MenuButtonDTO;
import com.icss.newretail.model.MenuDTO;
import com.icss.newretail.model.MenuRequest;
import com.icss.newretail.model.ModuleDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResourceTree;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.RoleDTO;

import java.util.List;
import java.util.Map;

public interface PrivilegeService {

    ResponseRecords<MenuDTO> getMenuTree();

    public ResponseBase createMenu(MenuDTO menu);

    public ResponseBase modifyMenu(MenuDTO menu);

    public ResponseBase enableOrDisablemenus(String menuId, String status);

    public ResponseBase deleteMenu(String menuId);

    // Role ===================================================
    public ResponseRecords<RoleDTO> getAllRoles(String roleName, String roleType);

    public ResponseBase createRole(RoleDTO role);

    public ResponseBase modifyRole(RoleDTO role);

    public ResponseBase deleteRole(String roleId);

    public ResponseResult<RoleDTO> queryRolesByUserId(String userId);

    public ResponseRecords<MenuDTO> getAllMenus(String appId, String roleId);

    public ResponseBase queryMenus(String moduleId, String upResourceReq, String resourceLevel);

    /**
     * 用户菜单关联角色权限、用户操作记录，去重并按照时间倒序返回用户有权限的、可用菜单列表
     * t_user_operator_log 用户操作记录
     *
     * @param userId 为空则根据tocken解析
     * @param num    默认8个
     * @return
     */
    public ResponseRecords<MenuDTO> myMenuTracks(String userId, int num);

    /**
     * 用户菜单访问记录保存t_user_operator_log 类型参照t_user_operator_type
     *
     * @param userId
     * @param menuId
     * @return
     */
    public ResponseBase trackMenuVisitor(String userId, String menuId);

    // RoleMenu ===================================================
    public ResponseBase setRoleMenus(String roleId, List<String> menuIds, List<String> btnIds);

    public ResponseBase getRoleMenus(String roleId);


    // Module
    public ResponseBase createModule(ModuleDTO module);

    public ResponseBase modifyModule(ModuleDTO module);

    public ResponseBase deleteModule(String moduleId);

    public ResponseBase queryModuleById(String moduleId);

    public ResponseRecords<ModuleDTO> queryModules(String appId, String userId);

    // App 应用
    public ResponseRecords<App> queryAppsByUserId(String userId);

    ResponseRecords<ResourceTree> getResourceTree(String roleId);

    ResponseResultPage<MenuDTO> queryMenusPageByMenuRequest(PageData<MenuRequest> pageData);

    ResponseResult<Map<String, Object>> queryMenusByMenuRequest(MenuRequest menuRequest);

    ResponseBase setMenuBtn(MenuButtonDTO menuButtonDTO);

    ResponseRecords<MenuDTO> getUserAllMenus();

    ResponseRecords<OrganizationDTO> getOrganizationTreeByOrgSeq(String orgSeq);
}
