package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.icss.newretail.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 用户信息-UserInfo
 * 
 * @author zhangzhijia
 * @date Apr 15, 2019
 */
@Data
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID =1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    //------yanghu  add   realName   sex

    @ApiModelProperty(value = "用户真实姓名")
    private String realName;

    @ApiModelProperty(value = "1 男  2  女")
    private String sex;
    //------------------

    @ApiModelProperty(value = "微信昵称")
    private String nickName;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "状态(启用/停用)")
    private Integer status;

    @ApiModelProperty(value = "退出登录时间")
    private Date logoutTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "用户类型(收银员/店长)")
    private String userType;

    @ApiModelProperty(value = "签到状态(是否在线)")
    private Integer signinStatus;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "所属组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "员工编号")
    private String employeeNo;

    @ApiModelProperty(value = "身份证")
    private String identity;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像地址")
    private String headPicUrl;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "账号")
    private List<String> accounts;

    //角色id、name、所属组织id、name

    @ApiModelProperty(value = "组织集合")
    private List<OrgList> orgList;

    @ApiModelProperty(value = "角色集合")
    private List<RoleList> roleList;

    @ApiModelProperty(value = "权限集合")
    private List<AuthorityList> authorityList;

    @ApiModelProperty(value = "创建人员id")
    private String createUser;
    //------wangyao  add   memberNumber

    @ApiModelProperty(value = "店员拥有会员数量")
    private Integer memberAmount;

    @ApiModelProperty(value = "成交额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "服务记录数")
    private String serviceCount;

    //-----------------
    @ApiModelProperty(value = "更新人")
    private String updateUser;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "门店组织id")
    private String storeOrgSeq;
    @ApiModelProperty(value = "门店组织名称")
    private String storeOrgName;
    @ApiModelProperty(value = "门店组织编码")
    private String storeOrgCode;

    @ApiModelProperty(value = "信息是否完整(1-是/0-否)")
    @TableField("is_info_complete")
    private Integer isInfoComplete;

    private String idCard;

    @ApiModelProperty(value = "学历(0-高中以下 1-大专 2-本科 3-硕士")
    @TableField("academic")
    private Integer academic;

    @ApiModelProperty(value = "工作年限")
    @TableField("work_year")
    private Integer workYear;

    @ApiModelProperty(value = "上岗日期")
    @TableField("onboard_date")
    private String onboardDate;


    public Boolean checkArgs() {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userType)) {
            return true;
        }
        return false;
    }
}
