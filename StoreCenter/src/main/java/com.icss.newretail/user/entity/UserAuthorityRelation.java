package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商家端小程序人员权限关联表
 * </p>
 *
 * @author mx
 * @since 2020-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_authority_relation")
@ApiModel(value="UserAuthorityRelation对象", description="商家端小程序人员权限关联表")
public class UserAuthorityRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "门店组织机构编码")
    @TableField("org_seq")
    private String orgSeq;

    @ApiModelProperty(value = "权限id")
    @TableField("authority_id")
    private String authorityId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;


}
