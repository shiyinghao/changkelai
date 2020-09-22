package com.icss.newretail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 店铺公众号信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserWechatInfo对象", description="店铺公众号信息")
public class WechatInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;

    @ApiModelProperty(value = "公众号ID")
    private String corpId;

    @ApiModelProperty(value = "开发者凭证")
    private String corpSecret;

    @ApiModelProperty(value = "公众号原始id")
    private String corpCode;

    @ApiModelProperty(value = "公众号名称")
    private String corpName;

    @ApiModelProperty(value = "公众号类型'")
    private Integer corpType;

    @ApiModelProperty(value = "应用id")
    private String agentId;

    @ApiModelProperty(value = "应用名称")
    private String agentName;

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    @ApiModelProperty(value = "关键字")
    private String corpKey;

    @ApiModelProperty(value = "公众号图片")
    private String wechatPic;
    
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
}
