package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author MC_JC
 * @date 2020.04.23
 */
@Data
public class StoreDeliveryInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "门店组织机构")
    private String orgSeq;

    @ApiModelProperty(value = "最远启配距离")
    private BigDecimal distance;

    @ApiModelProperty(value = "免费配送距离")
    private BigDecimal freeDistance;

    @ApiModelProperty(value = "配送服务时间(开始)")
    @TableField("start_time")
    private String startTime;

    @ApiModelProperty(value = "配送服务时间(起始)")
    private String deliveryTime;

    @ApiModelProperty(value = "配送计费方式(1:固定值  2:按公里计费 )")
    private Integer deliveryType;

    @ApiModelProperty(value = "配送费")
    private BigDecimal deliveryMoney;

    public Boolean checkArgs(){
        if(orgSeq==null||orgSeq.equals("")){
            return true;
        }
        return  false;
    }

    public  Boolean checkUpdate(){
        if(uuid==null||uuid.equals("")||orgSeq==null||orgSeq.equals("")){
            return true;
        }
        return  false;
    }

}
