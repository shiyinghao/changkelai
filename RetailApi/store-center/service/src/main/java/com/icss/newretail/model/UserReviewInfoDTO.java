package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jc
 * @date 2020/3/23 19:29
 */
@Data
public class UserReviewInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String uuid;

	@ApiModelProperty(value = "所属战区组织编码")
	private String upOrgSeq;

	@ApiModelProperty(value = "授权码")
	private String authCode;

	@ApiModelProperty(value = "店铺姓名")
	private String storeName;

	@ApiModelProperty(value = "营业执照")
	private String businessLicense;

	@ApiModelProperty(value = "店铺类型(1标准店、2精品店、3综合体验店)")
	private Integer storeType;

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
	private String openDate;

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

	@ApiModelProperty(value = "审核状态")
	private Integer reviewStatus;

	@ApiModelProperty(value = "审核人")
	private String reviewUser;

	@ApiModelProperty(value = "复核人")
	private String finalReviewUser;

	@ApiModelProperty(value = "审核意见")
	private String reviewOpinion;

	@ApiModelProperty(value = "复核意见")
	private String finalReviewOpinion;

	@ApiModelProperty(value = "审核类型")
	private Integer reviewType;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "店长电话")
	private String storeManagerPhone;

	@ApiModelProperty(value = "店长姓名")
	private String storeManagerName;

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
	
	@ApiModelProperty(value = "营业开始时间")
    private String openBeginTime;
	
	@ApiModelProperty(value = "营业结束时间")
    private String openEndTime;

	@ApiModelProperty(value = "流程信息")
	private List<UserReviewRecordDTO> userReviewRecordList;

	@ApiModelProperty(value = "所属基地名称")
	private String baseName;

	@ApiModelProperty(value = "所属业务员姓名")
	private String basePersonName;

	@ApiModelProperty(value = "店铺编辑记录数")
	private Integer number;

	@ApiModelProperty(value = "是否是工行对公账户（1-是 2-否）")
	private Integer isIcbc;

	@ApiModelProperty(value = "法人身份证正面照片")
	private String frontCard;

	@ApiModelProperty(value = "法人身份证背面照片")
	private String backCard;

	@ApiModelProperty(value = "结算账号照片")
	private String settlementPic;

	@ApiModelProperty(value = "银行审核意见")
	private String icbcReviewOpinion;

	@ApiModelProperty(value = "银行审核状态（1-审核通过，2-审核不通过）")
	private Integer icbcReviewStatus;

	@ApiModelProperty(value = "工行建档模型")
	private List<UserIcbcFilingDTO> userIcbcFilingDTOList;

	@ApiModelProperty(value = "建档日期")
	private String createdDate;

	@ApiModelProperty(value = "银行审核开关（1-允许审核，2-不允许审核）")
	private Integer icbcSwitch;

	@ApiModelProperty(value = "银行账号")
	@TableField("bank_account")
	private String bankAccount;


	@ApiModelProperty(value = "银行名称")
	@TableField("bank_name")
	private String bankName;

	@ApiModelProperty(value = "店员信息")
	private List<StoreUserInfoDTO> storeUserInfoDTOS;

	@ApiModelProperty(value = "所属曲线")
	@TableField("county")
	private String county;
}
