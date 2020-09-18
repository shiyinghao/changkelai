package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信人员认证关系表
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserWechatUserAuth对象", description="微信人员认证关系表")
public class WechatUserAuthDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "认证关系UUID")
    private String authUuid;

    @ApiModelProperty(value = "公众号id")
    private String corpId;

    @ApiModelProperty(value = "人员编码")
    private String openId;

    @ApiModelProperty(value = "认证类型(10001门店10002客服10003配送)")
    private String authType;

    @ApiModelProperty(value = "认证账号")
    private String authAccount;

    @ApiModelProperty(value = "状态(0禁用1启用)")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String comments;

}
