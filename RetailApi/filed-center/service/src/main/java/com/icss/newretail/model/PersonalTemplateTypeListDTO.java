package com.icss.newretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PersonalTemplateTypeListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板分类编号")
    @JsonProperty("TemplateCategoryID")
    private String templateCategoryID;

    @ApiModelProperty(value = "模板分类名称")
    @JsonProperty("CategoryName")
    private String categoryName;

}