package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 商家端登录记录表
 * </p>
 *
 * @author jc
 * @since 2020-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_shop_login_record")
@ApiModel(value="ShopLoginRecord对象", description="商家端登录记录表")
public class ShopLoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "uuid", type = IdType.AUTO)
    private Long uuid;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "门店编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "登录时间")
    @TableField("login_time")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "退出时间")
    @TableField("logout_time")
    private LocalDateTime logoutTime;

    @ApiModelProperty(value = "登录时长(分钟)")
    @TableField("stay_time")
    private Long stayTime;


}
