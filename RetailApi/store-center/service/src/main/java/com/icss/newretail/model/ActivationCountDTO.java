package com.icss.newretail.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author jc
 * @date 2020/7/28 17:13
 */
@Data
@Accessors(chain = true)
public class ActivationCountDTO implements Serializable {

	private static final long serialVersionUID = -7911331906892972132L;

	private Long startTimes;

	private Long userCount;

	private Long useTime;

	private int lowStoreCount;

	private int highStoreCount;

	private int middleStoreCount;

}
