package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Response-返回标识
 * 
 * @author zhangzhijia
 * @date Apr 17, 2019
 */
@Data
@Accessors(chain = true)
public class ResponseBase implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "返回状态（1:成功 0:失败）")
    private int code = 1;// 1:成功 0:失败

    @ApiModelProperty(value = "返回消息")
    private String message;

    public static int SUCCESS = 1;
    public static int FAILED = 0;

    public ResponseBase() {

    }

    public ResponseBase(int code, String message) {
    	this.code = code;
    	this.message = message;
    }
}
