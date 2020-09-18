package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmpParaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织编码")
    private String orgSeq;
    @ApiModelProperty(value = "员工姓名")
    private String realName;
}
