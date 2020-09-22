package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 门店信息
 * </p>
 *
 * @author syh
 * @since 2020-03-25
 */
@Data
public class UserStoreInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty(value = "组织机构编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "授权码")
    @TableField("auth_code")
    private String authCode;

    @ApiModelProperty(value = "店铺姓名")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty(value = "营业执照")
    @TableField("business_license")
    private String businessLicense;

    @ApiModelProperty(value = "店铺类型(1标准店、2精品店、3综合体验店)")
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
    private LocalDateTime openDate;

    @ApiModelProperty(value = "所属省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "所属城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "所属区县")
    @TableField("county")
    private String county;

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

    private String headPicUrl;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "营业开始时间")
    @TableField("open_begin_time")
    private String openBeginTime;

    @ApiModelProperty(value = "营业结束时间")
    @TableField("open_end_time")
    private String openEndTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "距离")
    private Integer distance = 0;

    @ApiModelProperty(value = "距离说明")
    private String distanceStr = "";

    @ApiModelProperty(value = "距离单位")
    private String distanceUnit = "";

    @ApiModelProperty(value = "店长手机")
    private String storeManagerTel;

    @ApiModelProperty(value = "店长电话")
    private String storeManagerPhone;

    @ApiModelProperty(value = "店长姓名")
    private String storeManagerName;

    @ApiModelProperty(value = "等级id")
    private String gradelevelId;

    @ApiModelProperty(value = "工行Merid(12位识别码)")
    private String merid;

    @ApiModelProperty(value = "工行协议号")
    private String agreement;

    @ApiModelProperty(value = "微信识别码")
    private String wechatId;

    @ApiModelProperty(value = "所属战区")
    private String upOrgZone;

    @ApiModelProperty(value = "所属基地")
    private String baseCode;

    @ApiModelProperty(value = "所属业务员")
    private String basePersonCode;

    @ApiModelProperty(value = "所属战区组织编码")
    private String upOrgSeq;

    @ApiModelProperty(value = "所属基地名称")
    private String baseName;

    @ApiModelProperty(value = "所属业务员姓名")
    private String basePersonName;

    @ApiModelProperty(value = "编辑状态")
    private String reviewStatus;

    @ApiModelProperty(value = "所属编辑信息的id")
    private String reviewId;

    @ApiModelProperty(value = "店铺编码")
    private String storeCode;

    @ApiModelProperty(value = "停业日期")
    private LocalDateTime shutDownTime;

    @ApiModelProperty(value = "是否是工行对公账户（1-是 2-否）")
    private Integer isIcbc;

    @ApiModelProperty(value = "法人身份证正面照片")
    private String frontCard;

    @ApiModelProperty(value = "法人身份证背面照片")
    private String backCard;

    @ApiModelProperty(value = "结算账号照片")
    private String settlementPic;

    @ApiModelProperty(value = "门店建档信息")
    private List<UserIcbcFilingDTO> userIcbcFilingDTOList;

    @ApiModelProperty(value = "建档日期")
    private String createdDate;
    @ApiModelProperty(value = "店员信息")
    private List<StoreUserInfoDTO> storeUserInfoDTOS;

    @ApiModelProperty(value = "支付信息状态（1-已解锁 2-已锁定）")
    @TableField("icbc_switch")
    private Integer icbcSwitch;

    @ApiModelProperty(value = "档案更新时间")
    @TableField("icbc_update_time")
    private LocalDateTime icbcUpdateTime;

    @ApiModelProperty(value = "银行账号")
    @TableField("bank_account")
    private String bankAccount;


    @ApiModelProperty(value = "银行名称")
    @TableField("bank_name")
    private String bankName;

    @ApiModelProperty(value = "终端id")
    @TableField("terminal_id")
    private String terminalId;

    @ApiModelProperty(value = "店铺申请 审核流程信息")
    private List<UserReviewRecordDTO> userReviewRecordDTOList;

    //2020-07-17- 新增字段  店铺介绍
    @ApiModelProperty(value = "店铺介绍")
    private String storeDesc;

    @ApiModelProperty(value = "信息是否完整(1-是/0-否)")
    @TableField("is_info_complete")
    private Integer isInfoComplete;

    public void setDistance(Integer distance) {
        this.distance = distance;
        if (distance != null && distance >= 0) {
            if (distance >= 1000) {
                DecimalFormat numDf = new DecimalFormat("#.##");
                this.distanceStr = numDf.format(distance * 0.001);
                this.distanceUnit = "km";
            } else {
                this.distanceStr = String.valueOf(distance);
                this.distanceUnit = "m";
            }
        }
    }


}
