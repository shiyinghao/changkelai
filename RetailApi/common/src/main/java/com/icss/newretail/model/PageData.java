package com.icss.newretail.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageData<T> {

	@ApiModelProperty(value = "每页显示数量")
	@JsonSetter(nulls = Nulls.SKIP)
	private int size = 10; // 每页显示数量 默认10

	@ApiModelProperty(value = "当前页码")
	@JsonSetter(nulls = Nulls.SKIP)
	private int current = 1; // 当前页码 默认1

	@ApiModelProperty(value = "查询参数")
	private T condition;// 传入查询参数

	@ApiModelProperty(value = "升序字段")
	@JsonSetter(nulls = Nulls.SKIP)
	private String[] ascs = new String[]{};

	@ApiModelProperty(value = "降序字段")
	@JsonSetter(nulls = Nulls.SKIP)
	private String[] descs = new String[]{};

}
