/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import lombok.Data;

/**
 * @author yedaotian
 * @date
 */
@Data
public class WarningRequest {

    //已分配内存
    private long total;
    //已分配内存中的剩余空间
    private long free;
    //最大内存
    private long max;
    //最大可用内存
    private long usable;

}
