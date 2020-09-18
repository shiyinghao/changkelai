package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Response-返回标识 权限令牌
 * 
 * @author zhangliang
 * @date 2020-05-23
 */
@Data
@Accessors(chain = true)
public class ResponseBaseToken implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "返回状态（1:成功 0:失败）")
    private int code = 1;// 1:成功 0:失败

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回令牌")
    private String token;

    public static int SUCCESS = 1;
    public static int FAILED = 0;

    public ResponseBaseToken() {

    }

    public ResponseBaseToken(int code, String message) {
    	this.code = code;
    	this.message = message;
    }
}
