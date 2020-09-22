package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author ydt
 * @since 2020-07-13
 */
@Data
public class BuyFreeGoodsInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	private String uuid;

	@ApiModelProperty(value = "商品编码")
	private String goodsSeq;

	@ApiModelProperty(value = "商品名称")
	private String goodsName;

	@ApiModelProperty(value = "外部系统码")
	private String sysCode;

	@ApiModelProperty(value = "国际条码")
	private String barCode;

	@ApiModelProperty(value = "标签类型")
	private String tagType;

	@ApiModelProperty(value = "商品分类ID")
	private String goodsTypeId;

	@ApiModelProperty(value = "计量单位")
	private String unit;

	@ApiModelProperty(value = "是否最小单位(0:否/1:是)")
	private Integer isMinUnit;

	@ApiModelProperty(value = "最小单位商品id")
	private String minUnitGoodsId;

	@ApiModelProperty(value = "最小单位")
	private String minUnit;

	@ApiModelProperty(value = "单位转换比例")
	private Integer unitConvertRate;

	@ApiModelProperty(value = "是否定制商品")
	private Integer isCustomizeGoods;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "创建人")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新人")
	private String updateUser;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "品牌编码")
	private String brandReq;

	@ApiModelProperty(value = "是否自主商品（0否/1是）")
	private Integer isSelf;

	@ApiModelProperty(value = "是否组合商品（0否/1是）")
	private Integer isComb;

	@ApiModelProperty(value = "是否赠品（0否/1是）")
	private Integer isGift;

	@ApiModelProperty(value = "是否必选商品 0不选 1必选")
	private Integer upLower;

	@ApiModelProperty(value = "排序 ")
	private Integer sortNo;

	@ApiModelProperty(value = "商品描述 ")
	private String goodsDescribe;

	@ApiModelProperty(value = "活动id ")
	private String activityId;

	@ApiModelProperty(value = "活动商品id ")
	private String activityGoodsId;
}
