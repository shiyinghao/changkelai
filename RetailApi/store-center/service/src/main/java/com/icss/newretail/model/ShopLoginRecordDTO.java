package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jc
 * @date 2020/7/28 14:39
 */
@Data
public class ShopLoginRecordDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private Long uuid;

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "门店编码")
	private String orgSeq;

	@ApiModelProperty(value = "登录时间")
	private LocalDateTime loginTime;

	@ApiModelProperty(value = "退出时间")
	private LocalDateTime logoutTime;

	@ApiModelProperty(value = "登录时长(分钟)")
	private Long stayTime;
}
