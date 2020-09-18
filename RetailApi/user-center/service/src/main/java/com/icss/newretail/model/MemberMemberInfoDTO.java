package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员信息
 * </p>
 *
 * @author syh
 * @since 2020-03-27
 */
@Data
@Accessors(chain = true)
public class MemberMemberInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "分享记录表主键id")
    private String shareRecordId;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "会员名称")
    private String userName;

    @ApiModelProperty(value = "会员昵称")
    private String nickName;

//    public String getNickName() {
//        try {
//            nickName = Base64.encodeBase64String(nickName.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        try {
//            nickName = new String(Base64.decodeBase64(nickName), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        this.nickName = nickName;
//    }

    @ApiModelProperty(value = "会员来源（1.自主注册 2.邀请注册）")
    private Integer memberType;

    @ApiModelProperty(value = "会员等级ID")
    private String gradelevelId;

    @ApiModelProperty(value = "手机号")
    private String cellPhoneNo;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "性别(男1/女2)")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "会员等级")
    private String gradeLevel;

    @ApiModelProperty(value = "注册时间")
    private String registerTime;

    @ApiModelProperty(value = "支付宝唯一识别号")
    private String alipayId;

    @ApiModelProperty(value = "微信唯一识别号")
    private String wechatId;

    @ApiModelProperty(value = "用户的openid")
    private String openId;

    @ApiModelProperty(value = "人脸的UserID")
    private String faceUserId;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "会员成长等级")
    private String memberGrowthLevel;

    @ApiModelProperty(value = "会员卡号")
    private String membercardId;

    @ApiModelProperty(value = "状态 1：启用 0：停用 -1：删除")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "头像")
    private String headPicUrl;

    private String shareId;//分享人id

    private String userId;//店员id

    @ApiModelProperty(value = "类型来区别是否是分享的连接")
    private String type;

    @ApiModelProperty(value = "绑定状态")
    private String bdstatus;

    private String bindTime;//绑定时间

    private BigDecimal amount;//会员消费总金额

    @ApiModelProperty(value = "门店组织编码")
    private String MDorgSeq;

    @ApiModelProperty(value = "门店名称")
    private String MDorgSeqName;

    @ApiModelProperty(value = "会员当前分值")
    private BigDecimal currentScore;

    @ApiModelProperty(value = "登录状态 0 退出 1 登录")
    private Integer flag;

    private Integer bindDay;
    private Integer bindStatus;

    private String unbindDayWarn;

    @ApiModelProperty(value = "会员编码")
    private String memberSeq;

    @ApiModelProperty(value = "会员编码")
    private String bdPersonName;

    @ApiModelProperty(value = "流失关系")
    private String bdship;

    private String remarkName;


    @ApiModelProperty(value = "门店授权编码")
    private String authCode;


    @ApiModelProperty(value = "所属基地")
    private String JDName;

    @ApiModelProperty(value = "所属战区")
    private String upOrgZone;

    @ApiModelProperty(value = "启用状态 0-禁用 1-启用")
    private Integer blockStatus;

    //===============会员资质信息====================
    @ApiModelProperty(value = "主键id")
    private String zHuuid;

    @ApiModelProperty(value = "资质类型")
    @TableField("credentials_type")
    private Integer credentialsType;

    @ApiModelProperty(value = "姓名/公司名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "身份证号码/营业执照号码")
    @TableField("number")
    private String number;

    @ApiModelProperty(value = "身份证正面照")
    @TableField("front_photo")
    private String frontPhoto;

    @ApiModelProperty(value = "身份证反面照")
    @TableField("reverse_photo")
    private String reversePhoto;

    @ApiModelProperty(value = "营业执照")
    @TableField("business_license")
    private String businessLicense;

    @ApiModelProperty(value = "是否默认")
    @TableField("is_default")
    private Integer isDefault;
}
