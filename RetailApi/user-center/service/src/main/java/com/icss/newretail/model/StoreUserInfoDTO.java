package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 门店店员信息表
 * @author jc
 * @date 2020/5/16 16:11
 */
@Data
public class StoreUserInfoDTO implements Serializable {

	private static final long serialVersionUID = 2676117158143913601L;

	private String userId;

	private String orgSeq;

	private String realName;

	private String headPicUrl;

	private Integer status;

	private String tel;

	private BigDecimal saleAmount;

	private Integer memberCount;

	private LocalDateTime createTime;

	private String storeName;

	private String jdOrgSeq;

	private String jdName;

	private String zqOrgSeq;

	private String zqName;

	private String idCard;

	private String authCode;

	@ApiModelProperty(value = "排序字段")
	private List<SortDTO> sortList;

	private String employeeNo;

	@ApiModelProperty(value = "学历(0-高中以下 1-大专 2-本科 3-硕士")
	@TableField("academic")
	private Integer academic;

	@ApiModelProperty(value = "工作年限")
	@TableField("work_year")
	private Integer workYear;

	@ApiModelProperty(value = "姓别(1-男2-女")
	@TableField("sex")
	private Integer sex;

	@ApiModelProperty(value = "职位(4店主5店长6店员）")
	private String userType;

	@ApiModelProperty(value = "上岗日期")
	private String onboardDate;

}
