package com.icss.newretail.file.entity;

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
 * 创艺接口访问日志
 * @author WXG
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_other_request_log")
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "接口名称")
    @TableId("service_name")
    private String serviceName;

    @ApiModelProperty(value = "请求方法")
    @TableId("service_action")
    private String serviceAction;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private int type;

    @ApiModelProperty(value = "访问结果")
    @TableField("result")
    private int result;

    @ApiModelProperty(value = "请求参数")
    @TableField("in_param")
    private String inParam;

    @ApiModelProperty(value = "返回参数")
    @TableField("out_param")
    private String outParam;

    @ApiModelProperty(value = "请求时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "响应时间")
    @TableField("return_time")
    private LocalDateTime returnTime;

}
