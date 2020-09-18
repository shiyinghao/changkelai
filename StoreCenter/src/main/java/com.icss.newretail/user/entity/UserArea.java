package com.icss.newretail.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icss.newretail.model.AreaInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 行政区划信息
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_area")
@ApiModel(value="UserArea对象", description="行政区划信息")
public class UserArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "区域ID")
    @TableId("area_id")
    private String areaId;

    @ApiModelProperty(value = "区域编码")
    @TableField("area_seq")
    private String areaSeq;

    @ApiModelProperty(value = "区域名称")
    @TableField("area_name")
    private String areaName;
    
    @ApiModelProperty(value = "上级区域编码")
    private String upAreaSeq;// 

    @ApiModelProperty(value = "租户ID")
    @TableField("tenant_id")
    private String tenantId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private String updateUser;

    public UserArea(){};
    public UserArea(AreaInfoDTO areaInfoDTO){
        this.areaId = areaInfoDTO.getAreaId();
        this.areaName = areaInfoDTO.getAreaName();
        this.areaSeq = areaInfoDTO.getAreaSeq();
        this.createUser = areaInfoDTO.getCreateUser();
        this.updateUser = areaInfoDTO.getUpdateUser();
        this.upAreaSeq = areaInfoDTO.getUpAreaSeq();
    }


}
