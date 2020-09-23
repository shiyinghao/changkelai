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
import java.time.LocalDateTime;

/**
 * <p>
 * 商家授权编码表
 * </p>
 *
 * @author jc
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_verify")
@ApiModel(value="UserStoreVerify对象", description="商家授权编码表")
public class UserStoreVerify implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "授权码")
    @TableField("auth_code")
    private String authCode;

    @ApiModelProperty(value = "时间")
    @TableField("date")
    private LocalDateTime date;


    @ApiModelProperty(value = "店铺名")
    @TableField("company_name")
    private String companyName;
}
