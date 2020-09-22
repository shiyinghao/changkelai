package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonalTemplateConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String style;
    private String left;
    private String top;
    private String src;
    private String customWidth;
    private String customHeight;
    private String fontFamily;
    private String endY;

    private PersonalTemplateTransformDTO transform;

    private List<PersonalTemplateConInfoDTO> conInfo;

}