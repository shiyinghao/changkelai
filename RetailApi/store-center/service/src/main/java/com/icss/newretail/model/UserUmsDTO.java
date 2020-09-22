package com.icss.newretail.model;

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
 * @author jc
 * @since 2020-04-26
 */
@Data
public class UserUmsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "短信平台检测方式（1 --- 提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出；" +
            "不为1 或该参数不存在 --- 提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出）")
    @TableField("f")
    private Integer f;

    @ApiModelProperty(value = "发送短信号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "短信模板id")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "短信内容")
    @TableField("messageContent")
    private String messageContent;

    @ApiModelProperty(value = "自定义流水号，查询回执时使用")
    @TableField("serialNumber")
    private String serialNumber;

    @ApiModelProperty(value = "发送时间设置（五分钟以内，默认即时发送）")
    @TableField("cheduleTime")
    private String cheduleTime;

    @ApiModelProperty(value = "创建人员id")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "短信发送状态（0-标识成功）")
    @TableField("resultStatus")
    private String resultStatus;

    @ApiModelProperty(value = "短信描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "失败号码列表")
    @TableField("faillist")
    private String faillist;

    @ApiModelProperty(value = "任务编号")
    @TableField("taskid")
    private String taskid;


}
