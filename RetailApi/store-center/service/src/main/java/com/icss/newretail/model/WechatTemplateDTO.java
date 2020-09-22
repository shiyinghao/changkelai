package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公众号模版信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserWechatTemplate对象", description="公众号模版信息")
public class WechatTemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公众号模版uuid")
    private String templateUuid;

    @ApiModelProperty(value = "微信公众号")
    private String corpId;

    @ApiModelProperty(value = "模板类型")
    private String templateType;

    @ApiModelProperty(value = "模版ID")
    private String templateId;

    @ApiModelProperty(value = "状态(0禁用/1启用)")
    private Integer status;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

}
