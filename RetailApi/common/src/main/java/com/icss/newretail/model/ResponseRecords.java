package com.icss.newretail.model;

import com.icss.newretail.util.StringUtils;
import com.icss.newretail.util.ToolUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Response-返回结果列表（含合计数据）
 * 
 * @author zhangzhijia
 * @date Apr 17, 2019
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResponseRecords<T> extends ResponseBase {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "返回结果（列表）")
  private List<T> records;// 返回结果（列表）

  @ApiModelProperty(value = "返回结果合计")
  private T footer;// 返回结果合计数据

  public ResponseRecords() {
    super();
  }

  public ResponseRecords(int code, String message) {
    super(code, message);
    this.records = new ArrayList<T>();
  }
  
  public ResponseRecords(List<T> list) {
    this.records = list;
  }

  @Override
  public String getMessage() {
    if (ToolUtil.isAllEmpty(records) && StringUtils.isEmpty(super.getMessage())) {
      super.setMessage("没有查询到记录");
    }
    return super.getMessage();
  }
  
}
