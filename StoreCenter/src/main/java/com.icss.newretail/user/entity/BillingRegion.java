package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 店铺开票拨号表
 * </p>
 *
 * @author zhangzhijia
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_billing_region")
@ApiModel(value="BillingRegion对象", description="店铺开票拨号表")
public class BillingRegion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "区域")
    @TableField("region")
    private String region;

    @ApiModelProperty(value = "电话号码")
    @TableField("tel_no")
    private String telNo;


}
