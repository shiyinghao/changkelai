package com.icss.newretail.model;

import com.icss.newretail.util.StringUtils;
import com.icss.newretail.util.ToolUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Response-返回单个结果
 * 
 * @author zhangzhijia
 * @date Apr 17, 2019
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResponseResult<T> extends ResponseBase {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "返回结果（单）")
  private T result;// 返回结果（单个）

  @Override
  public String getMessage() {
    if (ToolUtil.isEmpty(result) && StringUtils.isEmpty(super.getMessage())) {
      super.setMessage("没有匹配记录");
    }
    return super.getMessage();
  }

  public ResponseResult() {
    super();
  }

  public ResponseResult(T t) {
    result = t;
  }

  public ResponseResult(int code, String message) {
    super(code, message);
  }

}
