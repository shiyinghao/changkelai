package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jc
 * @date 2020/3/24 11:56
 */
@Data
public class UserStoreVerifyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private String uuid;

	@ApiModelProperty(value = "授权码")
	private String authCode;

	@ApiModelProperty(value = "时间")
	@TableField("date")
	private LocalDateTime date;


	@ApiModelProperty(value = "店铺名")
	@TableField("company_name")
	private String companyName;

}
