package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 店铺审核信息表
 * </p>
 *
 * @author ydt
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_review_info")
@ApiModel(value = "UserReviewInfo对象", description = "店铺审核信息表")
public class UserReviewInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "授权码")
    @TableField("auth_code")
    private String authCode;

    @ApiModelProperty(value = "所属战区组织编码")
    @TableField("up_org_seq")
    private String upOrgSeq;

    @ApiModelProperty(value = "店铺姓名")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty(value = "营业执照")
    @TableField("business_license")
    private String businessLicense;

    @ApiModelProperty(value = "店铺类型((1-一代店,二代店、、2-三代店，3-四代店，4-精品店，5-专卖店)")
    @TableField("store_type")
    private Integer storeType;

    @ApiModelProperty(value = "经营面积")
    @TableField("manager_area")
    private BigDecimal managerArea;

    @ApiModelProperty(value = "建筑面积")
    @TableField("building_area")
    private BigDecimal buildingArea;

    @ApiModelProperty(value = "实用面积")
    @TableField("practical_area")
    private BigDecimal practicalArea;

    @ApiModelProperty(value = "租赁面积")
    @TableField("leasehold_area")
    private BigDecimal leaseholdArea;

    @ApiModelProperty(value = "仓库面积")
    @TableField("warehouse_area")
    private BigDecimal warehouseArea;

    @ApiModelProperty(value = "营业状态")
    @TableField("open_status")
    private Integer openStatus;

    @ApiModelProperty(value = "营业时间")
    @TableField("open_time")
    private String openTime;

    @ApiModelProperty(value = "开业日期")
    @TableField("open_date")
    private String openDate;

    @ApiModelProperty(value = "所属省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "所属城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "商圈类型")
    @TableField("business_district_type")
    private Integer businessDistrictType;

    @ApiModelProperty(value = "经销商公司名")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "法人")
    @TableField("legal_person")
    private String legalPerson;

    @ApiModelProperty(value = "法人手机号")
    @TableField("legal_person_phone")
    private String legalPersonPhone;

    @ApiModelProperty(value = "店主姓名")
    @TableField("store_owner_name")
    private String storeOwnerName;

    @ApiModelProperty(value = "店主手机号")
    @TableField("store_owner_phone")
    private String storeOwnerPhone;

    @ApiModelProperty(value = "店铺电话")
    @TableField("store_phone")
    private String storePhone;

    @ApiModelProperty(value = "店主评级")
    @TableField("store_owner_grade")
    private String storeOwnerGrade;

    @ApiModelProperty(value = "店铺地址")
    @TableField("store_address")
    private String storeAddress;

    @ApiModelProperty(value = "店铺详细地址")
    @TableField("store_address_detail")
    private String storeAddressDetail;

    @ApiModelProperty(value = "店铺经度")
    @TableField("store_lng")
    private String storeLng;

    @ApiModelProperty(value = "店铺纬度")
    @TableField("store_lat")
    private String storeLat;

    @ApiModelProperty(value = "门头照片")
    @TableField("doorway_pic")
    private String doorwayPic;

    @ApiModelProperty(value = "店主照片")
    @TableField("store_owner_pic")
    private String storeOwnerPic;

    @ApiModelProperty(value = "店长照片")
    @TableField("store_manager_pic")
    private String storeManagerPic;

    @ApiModelProperty(value = "审核状态(1-申请已提交 2-初审通过 3-初审不通过 4-复审不通过 5-复审通过)")
    @TableField("review_status")
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核人(战区)")
    @TableField("review_user")
    private String reviewUser;

    @ApiModelProperty(value = "复核人(总部)")
    @TableField("final_review_user")
    private String finalReviewUser;

    @ApiModelProperty(value = "审核意见")
    @TableField("review_opinion")
    private String reviewOpinion;

    @ApiModelProperty(value = "复核意见")
    @TableField("final_review_opinion")
    private String finalReviewOpinion;

    @ApiModelProperty(value = "审核类型(1-申请审核 2-修改审核，3-拟稿暂存)")
    @TableField("review_type")
    private Integer reviewType;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "店长电话")
    @TableField("store_manager_phone")
    private String storeManagerPhone;

    @ApiModelProperty(value = "店长姓名")
    @TableField("store_manager_name")
    private String storeManagerName;

    @ApiModelProperty(value = "工行Merid(12位识别码)")
    @TableField("merid")
    private String merid;

    @ApiModelProperty(value = "工行协议号")
    @TableField("agreement")
    private String agreement;

    @ApiModelProperty(value = "微信识别码")
    @TableField("wechat_id")
    private String wechatId;

    @ApiModelProperty(value = "所属战区")
    @TableField("up_org_zone")
    private String upOrgZone;

    @ApiModelProperty(value = "所属基地")
    @TableField("base_code")
    private String baseCode;

    @ApiModelProperty(value = "基地所属业务员")
    @TableField("base_person_code")
    private String basePersonCode;

    @ApiModelProperty(value = "营业开始时间")
    @TableField("open_begin_time")
    private String openBeginTime;

    @ApiModelProperty(value = "营业结束时间")
    @TableField("open_end_time")
    private String openEndTime;

    @ApiModelProperty(value = "所属基地名称")
    @TableField("base_name")
    private String baseName;

    @ApiModelProperty(value = "基地所属业务员姓名")
    @TableField("base_person_name")
    private String basePersonName;

    @ApiModelProperty(value = "店铺编辑记录数")
    @TableField("number")
    private Integer number;

    @ApiModelProperty(value = "是否是工行对公账户（1-是 2-否）")
    @TableField("is_icbc")
    private Integer isIcbc;

    @ApiModelProperty(value = "法人身份证正面照片")
    @TableField("front_card")
    private String frontCard;

    @ApiModelProperty(value = "法人身份证背面照片")
    @TableField("back_card")
    private String backCard;

    @ApiModelProperty(value = "结算账号照片")
    @TableField("settlement_pic")
    private String settlementPic;

    @ApiModelProperty(value = "银行审核意见")
    @TableField("icbc_review_opinion")
    private String icbcReviewOpinion;

    @ApiModelProperty(value = "银行审核状态（1-审核通过，2-审核不通过）")
    @TableField("icbc_review_status")
    private Integer icbcReviewStatus;

    @ApiModelProperty(value = "建档日期")
    @TableField("created_date")
    private String createdDate;


    @ApiModelProperty(value = "银行审核开关（1-允许审核，2-不允许审核）")
    @TableField("icbc_switch")
    private Integer icbcSwitch;


    @ApiModelProperty(value = "银行账号")
    @TableField("bank_account")
    private String bankAccount;


    @ApiModelProperty(value = "银行名称")
    @TableField("bank_name")
    private String bankName;

    @ApiModelProperty(value = "所属曲线")
    @TableField("county")
    private String county;
}
