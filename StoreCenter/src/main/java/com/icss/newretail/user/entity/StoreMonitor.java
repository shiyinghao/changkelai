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
import java.util.Date;

/**
 * <p>
 * 终端检测记录
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_monitor")
@ApiModel(value = "StoreMonitor对象", description = "终端检测记录")
public class StoreMonitor implements Serializable {

  private static final long serialVersionUID = 1L;

  public StoreMonitor() {
    super();
  }

  public StoreMonitor(String storeId, String deviceId,Integer isRunback) {
    super();
    this.orgSeq = storeId;
    this.deviceId = deviceId;
    this.isRunback = isRunback;
//    this.monitorDate = DateUtils.getCurrentDate("yyyy-MM-dd");
  }


  @ApiModelProperty(value = "检测记录ID")
  @TableId("uuid")
  private long uuid;

  @ApiModelProperty(value = "组织编码")
  @TableField("org_seq")
  private String orgSeq;

  @ApiModelProperty(value = "设备ID")
  @TableField("device_id")
  private String deviceId;

  @ApiModelProperty(value = "终端ID")
  @TableField("device_number")
  private String deviceNumber;

  @ApiModelProperty(value = "日期")
  @TableField("monitor_date")
  private String monitorDate;

  @ApiModelProperty(value = "时间")
  @TableField("monitor_time")
  private Date monitorTime;

  @ApiModelProperty(value = "租户ID")
  @TableField("tenant_id")
  private String tenantId;

  @ApiModelProperty(value = "是否后台运行（1是2否）")
  @TableField("is_runback")
  private Integer isRunback;

}
