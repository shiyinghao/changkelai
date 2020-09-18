package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ydt
 * @since 2020-05-25
 */
@Data
public class UserIcbcTempDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "模板类型(1-法定代表人授权书,2-信息查询使用授权书,3-收单业务书,4-非金融企业划型信息采集表,5-对公客户信息采集表\r\n,6-非自然人客户受益所有人采集表,7-单位税收居民声明文件)")
    private Integer tempType;

    @ApiModelProperty(value = "模板地址")
    private String tempUrl;

    @ApiModelProperty(value = "模板状态（1-启用 0-失效）")
    private Integer status;


    @ApiModelProperty(value = "打印件地址")
    private String printUrl;

    @ApiModelProperty(value = "打印件名称")
    private String tempName;

    @ApiModelProperty(value = "模板名称")
    private String printName;


}
