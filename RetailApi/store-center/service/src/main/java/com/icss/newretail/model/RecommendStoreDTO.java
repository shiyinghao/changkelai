package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
@Data
public class RecommendStoreDTO implements Serializable{

    private static final long serialVersionUID = 2L;

    @ApiModelProperty(value = "主键ID")
    private String uuid;

    @ApiModelProperty(value = "组织机构编码")
    private String orgSeq;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "店铺姓名")
    private String storeName;

    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "店铺类型(1标准店、2精品店、3综合体验店)")
    private String storeType;

    @ApiModelProperty(value = "经营面积")
    private BigDecimal managerArea;

    @ApiModelProperty(value = "建筑面积")
    private BigDecimal buildingArea;

    @ApiModelProperty(value = "实用面积")
    private BigDecimal practicalArea;

    @ApiModelProperty(value = "租赁面积")
    private BigDecimal leaseholdArea;

    @ApiModelProperty(value = "仓库面积")
    private BigDecimal warehouseArea;

    @ApiModelProperty(value = "营业状态")
    private Integer openStatus;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "开业日期")
    private LocalDateTime openDate;

    @ApiModelProperty(value = "所属省")
    private String province;

    @ApiModelProperty(value = "所属城市")
    private String city;

    @ApiModelProperty(value = "商圈类型")
    private Integer businessDistrictType;

    @ApiModelProperty(value = "经销商公司名")
    private String companyName;

    @ApiModelProperty(value = "法人")
    private String legalPerson;

    @ApiModelProperty(value = "法人手机号")
    private String legalPersonPhone;

    @ApiModelProperty(value = "店主姓名")
    private String storeOwnerName;

    @ApiModelProperty(value = "店主手机号")
    private String storeOwnerPhone;

    @ApiModelProperty(value = "店铺电话")
    private String storePhone;

    @ApiModelProperty(value = "店主评级")
    private String storeOwnerGrade;

    @ApiModelProperty(value = "店铺地址")
    private String storeAddress;

    @ApiModelProperty(value = "店铺详细地址")
    private String storeAddressDetail;

    @ApiModelProperty(value = "店铺经度")
    private String storeLng;

    @ApiModelProperty(value = "店铺纬度")
    private String storeLat;

    @ApiModelProperty(value = "门头照片")
    private String doorwayPic;

    @ApiModelProperty(value = "店主照片")
    private String storeOwnerPic;

    @ApiModelProperty(value = "店长照片")
    private String storeManagerPic;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;


    @ApiModelProperty(value = "距离")
    private Integer distance = 0;

    @ApiModelProperty(value = "距离说明")
    private String distanceStr = "";

    @ApiModelProperty(value = "距离单位")
    private String distanceUnit = "";

    @ApiModelProperty(value = "营业开始时间")
    @TableField("open_begin_time")
    private String openBeginTime;

    @ApiModelProperty(value = "营业结束时间")
    @TableField("open_end_time")
    private String openEndTime;

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
