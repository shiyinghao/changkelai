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
import java.time.LocalDateTime;

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
@TableName("t_user_icbc_filing")
@ApiModel(value = "UserIcbcFiling对象", description = "")
public class UserIcbcFiling implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId("uuid")
    private String uuid;

    @ApiModelProperty(value = "工行建档文件类型(1-法定代表人授权书,2-信息查询使用授权书,3-收单业务书,4-非金融企业划型信息采集表,5-对公客户信息采集表" +
            ",6-非自然人客户受益所有人采集表,7-单位税收居民声明文件)")
    @TableField("file_type")
    private Integer fileType;

    @ApiModelProperty(value = "模板id")
    @TableField("file_temp_id")
    private String fileTempId;

    @ApiModelProperty(value = "扫描件")
    @TableField("file_scanning_copy")
    private String fileScanningCopy;

    @ApiModelProperty(value = "备注")
    @TableField("file_remarks")
    private String fileRemarks;

    @ApiModelProperty(value = "文件状态（1-启用，0-失效）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;


    @ApiModelProperty(value = "店铺申请信息id")
    @TableField("review_id")
    private String reviewId;

    @ApiModelProperty(value = "店铺主键id")
    @TableField("store_id")
    private String storeId;

    @ApiModelProperty(value = "工行文件标识key")
    @TableField("icbckey")
    private String icbckey;

    @ApiModelProperty(value = "扫描件名称")
    @TableField("file_scanning_copy_name")
    private String fileScanningCopyName;
}
