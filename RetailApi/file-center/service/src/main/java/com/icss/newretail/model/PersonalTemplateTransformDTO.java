package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalTemplateTransformDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String scale;
    private String angle;
    private String rx;
    private String ry;
    private String rz;
    private PersonalTemplateTranslateDTO translate;

}