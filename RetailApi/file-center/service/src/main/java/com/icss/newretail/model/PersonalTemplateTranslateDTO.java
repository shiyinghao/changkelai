package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonalTemplateTranslateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String x;
    private String y;

}