package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.UserService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestSchema(schemaId = "user")
@RequestMapping(path = "/v1/user")
public class UserApi {
    @Autowired
    private UserService userService;

    @PostMapping(path = "createUser")
    public ResponseBase createUser(@RequestBody UserInfoDTO user) {
        return userService.createUser(user);
    }

    /**
     * 2019-3-20：需要确认一下如果修改组织机构，除了修改数据库信息，还有没有其他的注意事项。
     * 可以以先直接使用修改用户的功能，将来如果有特殊的诉求，再将组织机构修改独立出去。
     *
     * @param user
     * @return
     */
    @PutMapping(path = "modifyUser")
    public ResponseBase modifyUser(@RequestBody UserInfoDTO user) {
        return userService.modifyUser(user);
    }


    /**
     * 设置用户角色
     *
     * @param userId
     * @param roleCodes
     * @return
     */
    @PostMapping(path = "setUserRoles")
    public ResponseBase setUserRoles(
            @NotEmpty(message = "参数userId不能为空") @ApiParam(name = "userId", value = "用户id", required = true) String userId,
            @RequestBody List<String> roleCodes) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(userId)) {
            responseBase.setCode(0);
            responseBase.setMessage("参数用户ID不能为空");
            return responseBase;
        }
        if (roleCodes == null || roleCodes.size() <= 0) {
            responseBase.setCode(0);
            responseBase.setMessage("参数角色信息不能为空");
            return responseBase;
        }
        return userService.setUserRoles(userId, roleCodes);
    }

    /**
     * 获取用户角色列表
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "getUserRoles")
    public ResponseRecords<RoleDTO> getUserRoles(
            @NotEmpty(message = "参数userId不能为空") @ApiParam(name = "userId", value = "用户id", required = true) String userId) {
        ResponseRecords<RoleDTO> responseBase = new ResponseRecords<RoleDTO>();
        if (StringUtils.isBlank(userId)) {
            responseBase.setCode(0);
            responseBase.setMessage("参数用户ID不能为空");
            return responseBase;
        }
        return userService.getUserRoles(userId);
    }


    /**
     * 删除用户 停用
     *
     * @param userId
     * @return
     */
    @DeleteMapping(path = "deleteUser")
    public ResponseBase deleteUser(@RequestParam(name = "userId") String userId) {
        return userService.deleteUser(userId);
    }

    // 处于安全性考虑，密码必须采用form格式来传递
    @PostMapping(path = "changePassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseBase changePassword(
            @NotEmpty(message = "参数userId不能为空") @ApiParam(name = "userId", value = "用户id", required = true) String userId,
            @NotEmpty(message = "参数oldPassword不能为空") @ApiParam(name = "oldPassword", value = "旧密码", required = true) String oldPassword,
            @NotEmpty(message = "参数newPassword不能为空") @ApiParam(name = "newPassword", value = "新密码", required = true) String newPassword) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(userId)) {
            responseBase.setCode(0);
            responseBase.setMessage("账号不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(oldPassword)) {
            responseBase.setCode(0);
            responseBase.setMessage("旧密码不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(newPassword)) {
            responseBase.setCode(0);
            responseBase.setMessage("新密码不能为空");
            return responseBase;
        }
        return userService.changePassword(userId, oldPassword, newPassword);
    }


    /**
     * 用户密码重置
     *
     * @param userId
     * @param newPassword
     * @return
     */
    @PatchMapping(path = "resetPassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseBase resetPassword(
            @NotEmpty(message = "参数userId不能为空") @ApiParam(name = "userId", value = "用户id", required = true) String userId,
            @NotEmpty(message = "参数newPassword不能为空") @ApiParam(name = "newPassword", value = "密码", required = true) String newPassword) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(userId)) {
            responseBase.setCode(0);
            responseBase.setMessage("用户ID不能为空");
            return responseBase;
        }
        if (StringUtils.isBlank(newPassword)) {
            responseBase.setCode(0);
            responseBase.setMessage("密码不能为空");
            return responseBase;
        }
        return userService.resetPassword(userId, newPassword);
    }

    /**
     * 用户信息查询（根据用户ID）
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "queryUserById")
    public ResponseResult<UserInfoDTO> queryUserById(@RequestParam(name = "userId") String userId) {
        return userService.queryUserById(userId);
    }

    /**
     * 用户信息查询（根据用户ID）
     *
     * @param userIds
     * @return
     */
    @PostMapping(path = "queryUserAllByIds")
    public Map<String,String> queryUserAllByIds(@RequestParam(name = "userIds") Set<String> userIds) {
        return userService.queryUserAllByIds(userIds);
    }

    /**
     * 查询会员的业务员信息（根据用户ID）
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "queryMemberUserById")
    public ResponseResult<MemberUserDTO> queryMemberUserById(@RequestParam(name = "userId") String userId) {
        return userService.queryMemberUserById(userId);
    }

    /**
     * 根据门店orgSeq查出店长
     *
     * @param orgSeq
     * @return
     */
    @GetMapping(path = "queryManagerById")
    public ResponseResult<MemberUserDTO> queryManagerById(@RequestParam(name = "orgSeq") String orgSeq) {
        return userService.queryManagerById(orgSeq);
    }

    /**
     * 门店员工查询（根据名称，电话进行模糊查询）
     *
     * @param pageData
     * @return update   需要返回人员角色id、name、所属组织id、name
     */
    @PostMapping(path = "queryUsers")
    public ResponseResultPage<UserInfoDTO> queryUsers(@RequestBody PageData<UserRequest> pageData) {
        return userService.queryUsers(pageData);
    }

    /**
     * 根据门店orgSeq查出该门店下的所有人员，包括店主店长,可根员工名称和电话模糊查询
     *
     * @param pageData
     * @return
     */
    @PostMapping(path = "queryStoreUsers")
    public ResponseResultPage<UserInfoDTO> queryStoreUsers(@RequestBody PageData<UserRequest> pageData) {
        ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<>();
        if (pageData != null && pageData.getCondition().getOrgSeq() != null) {
            return userService.queryStoreUsers(pageData);
        } else {
            result.setCode(0);
            result.setMessage("传入的查询参数分页对象不能为空以及门店编码不能为空");
            return result;
        }
    }

    /**
     * 门店员工查询 根据门店orgSeq只查员工，返回员工的权限,可根员工名称和电话模糊查询
     *
     * @param pageData
     * @return update   需要返回人员角色id、name、所属组织id、name
     */
    @PostMapping(path = "queryUsersById")
    public ResponseResultPage<UserInfoDTO> queryUsersById(@RequestBody PageData<UserRequest> pageData) {
        ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<>();
        if (pageData != null && pageData.getCondition().getOrgSeq() != null) {
            return userService.queryUsersById(pageData);
        } else {
            result.setCode(0);
            result.setMessage("传入的查询参数分页对象不能为空以及门店编码不能为空");
            return result;
        }
    }

    /**
     * 根据门店orgSeq 下的店员拥有的 会员消费总额,服务次数
     *
     * @param userRequest
     * @return update   需要返回人员角色id、name、所属组织id、name
     */
    @PostMapping(path = "queryStorePerson")
    public ResponseRecords<UserInfoDTO> queryStorePerson(@RequestBody UserRequest userRequest) {
        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
        if (userRequest != null && userRequest.getOrgSeq() != null) {
//            userRequest.setInitFlag("(6)");
            return userService.queryStorePerson(userRequest);
        } else {
            result.setCode(0);
            result.setMessage("传入的门店编码不能为空");
            return result;
        }
    }

    /**
     * 根据门店orgSe查出店长和店主
     *
     * @param userRequest
     * @return update   需要返回人员角色id、name、所属组织id、name
     */
    @PostMapping(path = "queryManage")
    public ResponseRecords<UserInfoDTO> queryManage(@RequestBody UserRequest userRequest) {
        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
        if (userRequest != null && userRequest.getOrgSeq() != null) {
            userRequest.setInitFlag("(4,5)");
            return userService.queryManage(userRequest);
        } else {
            result.setCode(0);
            result.setMessage("传入的门店编码不能为空");
            return result;
        }
    }
    /**
     * 根据门店orgSeq查出店长
     *
     * @param orgSeq
     * @return update   需要返回人员角色id、name、所属组织id、name
     */
    @PostMapping(path = "queryManangerById")
    public ResponseResult<UserInfoDTO> queryManangerById(@RequestParam(name = "orgSeq") String orgSeq) {
        ResponseResult<UserInfoDTO> result = new ResponseResult<>();
        if (orgSeq != null && !"".equals(orgSeq)) {
            return userService.queryManangerById(orgSeq);
        } else {
            result.setCode(0);
            result.setMessage("传入的门店编码不能为空");
            return result;
        }
    }

    // 用户配置查询
    @GetMapping(path = "queryUserCustomizedParams")
    public ResponseBase queryUserCustomizedParams(@RequestParam(name = "userId") String userId) {
        return userService.queryUserCustomizedParams(userId);
    }

    /**
     * 用户类型添加
     *
     * @param userType
     * @return
     */
    @PostMapping(path = "createUserType")
    public ResponseBase createUserType(@RequestBody UserTypeDTO userType) {
        if (userType == null) {
            ResponseBase base = new ResponseBase();
            base.setCode(0);
            base.setMessage("参数用户类型不能为空");
            return base;
        }
        return userService.createUserType(userType);
    }

    /**
     * 用户类型修改
     *
     * @param userType
     * @return
     */
    @PutMapping(path = "modifyUserType")
    public ResponseBase modifyUserType(@RequestBody UserTypeDTO userType) {
        if (userType == null || userType.getUserType() == null || "".equals(userType.getUserType().trim())) {
            ResponseBase base = new ResponseBase();
            base.setCode(0);
            base.setMessage("传入参数对象和用户类型属性userType不能为空");
            return base;
        }
        return userService.modifyUserType(userType);
    }

    // DELETE方法包含body通常不是好主意。有些HTTP实现会忽略。

    /**
     * 删除用户类型
     *
     * @param userType
     * @return
     */
    @DeleteMapping(path = "deleteUserType")
    public ResponseBase deleteUserType(
            @NotEmpty(message = "用户类型ID不能为空") @ApiParam(name = "userType", value = "用户类型ID") String userType) {
        if (userType == null || "".equals(userType.trim())) {
            ResponseBase base = new ResponseBase();
            base.setCode(0);
            base.setMessage("用户类型ID不能为空");
            return base;
        }
        return userService.deleteUserType(userType);
    }

    /**
     * 用户类型查询（单个）
     *
     * @param userType
     * @return
     */
    @PostMapping(path = "queryUserTypeById")
    public ResponseResult<UserTypeDTO> queryUserTypeById(
            @ApiParam(name = "userType", value = "用户类型ID") String userType) {
        return userService.queryUserTypeById(userType);
    }

    /**
     * 用户类型查询（根据userTypeName模糊查询）
     *
     * @param userTypeName
     * @return
     */
    @PostMapping(path = "queryUserTypes")
    public ResponseRecords<UserTypeDTO> queryUserTypes(
            @ApiParam(name = "userTypeName", value = "用户类型名称", required = false) String userTypeName) {
        return userService.queryUserTypes(userTypeName);
    }

    /**
     * 店长授权
     *
     * @param orgSeq
     * @param password
     * @return
     */
    @GetMapping(path = "grantByStoreManager")
    public ResponseBase grantByStoreManager(
            @NotEmpty(message = "参数orgSeq不能为空") @ApiParam(name = "orgSeq", value = "门店id", required = true) String orgSeq,
            @NotEmpty(message = "参数password不能为空") @ApiParam(name = "password", value = "密码", required = true) String password) {
        return userService.grantByStoreManager(orgSeq, password);
    }


    /**
     * 组织类型查询接口
     */

    @PostMapping(path = "getUserOrgType")
    public ResponseRecords<OrganizationTypeDTO> getUserOrgType(@RequestParam(name = "orgType", required = false) String orgType, @RequestParam(name = "orgTypeName", required = false) String orgTypeName) {
        return userService.getUserOrgType(orgType, orgTypeName);
    }

    /**
     * 根据动态获取的组织机构判断最终所属门店Org_seq集合
     * 动态获取组织机构树
     *
     * @param orgSeq
     * @return
     */
//	@PostMapping(path="queryStoreOrgseq")
//	public ResponseRecords<OrgReslutDTO> queryStoreOrgseq(@RequestBody CurrentOrgReq currentOrgReq){
//		return userService.queryStoreOrgseq(currentOrgReq);
//	}
    @GetMapping(path = "queryStoreOrgseq")
    public ResponseRecords<OrgReslutDTO> queryStoreOrgseq(@RequestParam(value = "orgSeq", required = true) String orgSeq) {
        return userService.queryStoreOrgseq(orgSeq);
    }

    @GetMapping(path = "getOrgSeqType")
    public OrganizationDTO getOrgSeqType(@RequestParam(name = "orgSeq", required = true) String orgSeq) {
        return userService.getOrgSeqType(orgSeq);

    }

    /**
     * 根据门店id 当班店员名称查询店员列表
     *
     * @return
     * @Author zys
     */
    @PostMapping(path = "getShopAssList")
    public ResponseRecords getShopAssList(@RequestBody Map<String,String> map) {
        return userService.queryShopAssNameList(map);
    }

    /**
     * 根据店铺 id 查询店长或者店员信息
     *
     * @param orgSeq
     * @param userType : 用户类型
     * @return
     */
    @PostMapping("queryShopOwner")
    public ResponseRecords<UserInfoDTO> queryShopOwner(@RequestParam(name = "orgSeq") String orgSeq,
                                            @RequestParam("userType") String userType) {
        return userService.queryShopOwner(orgSeq, userType);
    }

    /**
     * 根据用户 id 查询用户手机号
     * @param userId
     * @return
     */
    @PostMapping("queryTel")
    public UserInfoDTO queryTel (@RequestParam("userId") String userId) {
        return userService.queryTel(userId);
    }

    /**
     * 智能巡店-员工信息
     *
     * @param orgSeq
     * @Author wangjie
     * @return
     */
    @PostMapping("queryOrgUserList")
    public ResponseResultPage<UserInfoDTO> queryOrgUserList(@RequestBody PageData<UserInfoParamDTO> pageData){
        return userService.queryOrgUserList(pageData);
    }


    /**
     * 查询基地经理
     * @param orgSeq
     * @return
     */
    @PostMapping("queryManagerList")
    public ResponseRecords<UserInfoDTO> queryManagerList(@RequestParam("orgSeq") String orgSeq) {
        return userService.queryManagerList(orgSeq);
    }

    /**
     * 查询上级组织
     * @param orgSeq
     * @return
     */
    @PostMapping("queryUpOrg")
    public ResponseResult<OrganizationDTO> queryUpOrg(@RequestParam("orgSeq")String orgSeq) {
        return userService.queryUpOrg(orgSeq);
    }

    /**
     * 智能巡店预警对象设置-基地选择列表查询
     * @param orgSeq
     * @return
     */
    @PostMapping("queryManagerListByUpOrg")
    public List<BaseDTO> queryManagerListByUpOrg(@RequestParam("orgSeq")String orgSeq) {
        return userService.queryManagerListByUpOrg(orgSeq);
    }

    /**
     * 查询门店orgType
     * @param orgSeq
     * @return
     */
    @PostMapping("queryOrgLV")
    public String queryOrgLV(@RequestParam("orgSeq")String orgSeq) {
        return userService.queryOrgLV(orgSeq);
    }

    /**
     * 查询门店信息列表
     * @param orgSeqList
     * @return
     */
    @PostMapping("queryOrgInfo")
    public List<OrganizationDTO> queryOrgInfo(@RequestParam("orgSeqList")List<String> orgSeqList) {
        return userService.queryOrgInfo(orgSeqList);
    }

    /**
     * 查询门店信息
     * @param orgSeq
     * @return
     */
    @PostMapping("queryOrgInfoByOrgSeq")
    public OrganizationDTO queryOrgInfoByOrgSeq(@RequestParam("orgSeq")String orgSeq) {
        return userService.queryOrgInfoByOrgSeq(orgSeq);
    }

    @PostMapping("queryDownOrg")
    public List<OrganizationDTO> queryDownOrg(@RequestParam("orgSeq")String orgSeq) {
        return userService.queryDownOrg(orgSeq);
    }


    /**
     * 根据基地orgSeqList查询基地业务经理信息
     * @param orgSeqList
     * @return
     */
    @PostMapping("queryBaseUserList")
    public ResponseRecords<UserInfoDTO> queryBaseUserList(@RequestParam("orgSeqList")List<String> orgSeqList) {
        return userService.queryBaseUserList(orgSeqList);
    }

    /**
     * 根据基地经理用户id查询所在基地及战区
     * @param userId
     * @return
     */
    @GetMapping("queryUserOrganizationInfo")
    public ResponseResult<UserOrganizationInfoDTO> queryUserOrganizationInfo(@RequestParam(name = "userId", required = true)String userId) {
        return userService.queryUserOrganizationInfo(userId);
    }
    
    /**
     * 根据组织id查询用户列表信息 lq 2020.7.23
     * @param orgSeq
     * @return
     */
    @PostMapping("queryUserListByOrgSeq")
    public ResponseRecords<UserInfoDTO> queryUserListByOrgSeq(@RequestParam("orgSeq")String orgSeq) {
        return userService.queryUserListByOrgSeq(orgSeq);
    }

}
