package com.icss.newretail.model;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: MessageCustom
 * @Description: 推送消息实体类
 * @author jc
 * @date 2019年7月4日
 *
 */
@Data
public class MessageCustom implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value="消息标题")
  private String title;

  @ApiModelProperty(value="消息内容")
  private String content;
  
  @ApiModelProperty(value="消息其他内容")
  private Map<String,Object> messageMap;
  
  public MessageCustom() {
    super();
  }

  public MessageCustom(String title, String content, Map<String, Object> mess) {
    super();
    this.title = title;
    this.content = content;
    this.messageMap = mess;
  }
  
}
