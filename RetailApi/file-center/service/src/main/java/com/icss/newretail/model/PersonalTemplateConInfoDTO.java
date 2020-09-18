package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalTemplateConInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;
    private String fontSize;
    private String direction;
    private String fontAlign;
    private String fontBox;
    private String fontLeft;
    private String fontClass;
    private String margintop;
    private String fontFloat;
    private String fontColor;
    private String defaultcolor;

}