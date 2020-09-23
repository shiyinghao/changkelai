package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icss.newretail.model.UserInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_info")
@ApiModel(value = "UserInfo对象", description = "用户信息")
public class UserInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户ID")
  @TableId("user_id")
  private String userId;

  @ApiModelProperty(value = "用户中文名字")
  @TableField("user_name")
  private String userName;

  //------yanghu  add   realName   sex

  @ApiModelProperty(value = "用户真实姓名")
  private String realName;

  @ApiModelProperty(value = "1 男  2  女")
  private String sex;
  //------------------

  @ApiModelProperty(value = "密码")
  @TableField("password")
  private String password;

  @ApiModelProperty(value = "电话")
  @TableField("tel")
  private String tel;

  @ApiModelProperty(value = "状态(启用/停用)")
  @TableField("status")
  private Integer status;

  @ApiModelProperty(value = "退出登录时间")
  @TableField("logout_time")
  private LocalDateTime logoutTime;

  @ApiModelProperty(value = "创建时间")
  @TableField("create_time")
  private Date createTime;

  @ApiModelProperty(value = "用户类型(收银员/店长)")
  @TableField("user_type")
  private String userType;

  @ApiModelProperty(value = "签到状态(是否在线)")
  @TableField("signin_status")
  private Integer signinStatus;

  @ApiModelProperty(value = "登录时间")
  @TableField("login_time")
  private Date loginTime;

  @ApiModelProperty(value = "所属组织编码")
  @TableField("org_seq")
  private String orgSeq;

  @ApiModelProperty(value = "电子邮箱")
  @TableField("email")
  private String email;

  @ApiModelProperty(value = "员工编号")
  @TableField("employee_no")
  private String employeeNo;

  @ApiModelProperty(value = "身份证")
  @TableField("identity")
  private String identity;

  @ApiModelProperty(value = "密码错误次数")
  @TableField("wrong_count")
  private Integer wrongCount;

  @ApiModelProperty(value = "是否锁定")
  @TableField("is_lock")
  private Integer isLock;

  @ApiModelProperty(value = "锁定时间")
  @TableField("lock_time")
  private Date lockTime;

  @ApiModelProperty(value = "盐")
  @TableField("salt")
  private String salt;

  @ApiModelProperty(value = "头像")
  @TableField("head_pic_url")
  private String headPicUrl;

  @ApiModelProperty(value = "创建人员id")
  @TableField("create_user")
  private String createUser;

  @ApiModelProperty(value = "更新人")
  @TableField("update_user")
  private String updateUser;

  @ApiModelProperty(value = "更新时间")
  @TableField("update_time")
  private LocalDateTime updateTime;


  @ApiModelProperty(value = "身份证号码")
  @TableField("id_card")
  private String idCard;

  @ApiModelProperty(value = "信息是否完整(1-是/0-否)")
  @TableField("is_info_complete")
  private Integer isInfoComplete;

  @ApiModelProperty(value = "学历(0-高中以下 1-大专 2-本科 3-硕士")
  @TableField("academic")
  private Integer academic;

  @ApiModelProperty(value = "工作年限")
  @TableField("work_year")
  private Integer workYear;

  @ApiModelProperty(value = "上岗日期")
  @TableField("onboard_date")
  private String onboardDate;


  public UserInfo() {
  }

  public UserInfo(UserInfoDTO userInfoDTO) {

    this.realName = userInfoDTO.getRealName();
    this.sex = userInfoDTO.getSex();
    this.identity = userInfoDTO.getIdentity();
    this.employeeNo = userInfoDTO.getEmployeeNo();
    this.email = userInfoDTO.getEmail();
    this.loginTime = userInfoDTO.getLoginTime();
    this.orgSeq = userInfoDTO.getOrgSeq();
    this.signinStatus = userInfoDTO.getSigninStatus();
    this.status = userInfoDTO.getStatus();
    this.tel = userInfoDTO.getTel();
    this.userId = userInfoDTO.getUserId();
    this.userName = userInfoDTO.getUserName();
    this.userType = userInfoDTO.getUserType();
    this.createTime = userInfoDTO.getCreateTime();
    this.password = userInfoDTO.getPassword();
    this.createUser = userInfoDTO.getCreateUser();
    this.updateUser = userInfoDTO.getUpdateUser();
    this.updateTime = userInfoDTO.getUpdateTime();
    this.idCard = userInfoDTO.getIdCard();
    this.isInfoComplete = userInfoDTO.getIsInfoComplete();
    this.academic = userInfoDTO.getAcademic();
    this.workYear = userInfoDTO.getWorkYear();
    this.onboardDate = userInfoDTO.getOnboardDate();

  }
}
