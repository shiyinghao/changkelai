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

/**
 * <p>
 *
 * </p>
 *
 * @author ydt
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_icbc_temp")
@ApiModel(value = "UserIcbcTemp对象", description = "")
public class UserIcbcTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "模板类型(1-法定代表人授权书,2-信息查询使用授权书,3-收单业务书,4-非金融企业划型信息采集表,5-对公客户信息采集表\r\n,6-非自然人客户受益所有人采集表,7-单位税收居民声明文件)")
    @TableField("temp_type")
    private Integer tempType;

    @ApiModelProperty(value = "模板地址")
    @TableField("temp_url")
    private String tempUrl;

    @ApiModelProperty(value = "模板状态（1-启用 0-失效）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "打印件地址")
    @TableField("print_url")
    private String printUrl;

    @ApiModelProperty(value = "打印件名称")
    @TableField("temp_name")
    private String tempName;

    @ApiModelProperty(value = "模板名称")
    @TableField("print_name")
    private String printName;
}
