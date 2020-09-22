package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SortDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序方式（desc asc）")
    private String sort;

    @ApiModelProperty(value = "排序字段")
    private String sortField;


}
