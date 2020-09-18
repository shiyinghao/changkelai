/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.icss.newretail.objectmapper;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.icss.newretail.dateformat.MyDateFormat;
import com.icss.newretail.deserialize.CustomJsonDateDeserializer;
import com.icss.newretail.deserialize.CustomJsonLocalDateTimeDeserializer;
import com.icss.newretail.serialize.CustomJsonDateSerializer;
import com.icss.newretail.serialize.CustomJsonLocalDateTimeSerializer;
import org.apache.servicecomb.common.rest.codec.RestObjectMapper;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自定义序列化转换器
 */
public class CustomRestObjectMapper extends RestObjectMapper {
	private static final long serialVersionUID = 2333415529679480470L;

	public CustomRestObjectMapper() {
		super();
		//反序列化同时支持yyyy-MM-dd HH:mm:ss和yyyy-MM-dd'T'HH:mm:ss.SSS'Z'格式
		setDateFormat(new MyDateFormat(getDateFormat()));
		//将Date和LocalDateTime类型字段序列化为yyyy-MM-dd HH:mm:ss格式
		SimpleModule module = new SimpleModule();
		module.addSerializer(Date.class, new CustomJsonDateSerializer());
		module.addSerializer(LocalDateTime.class, new CustomJsonLocalDateTimeSerializer());
		module.addDeserializer(Date.class, new CustomJsonDateDeserializer());
		module.addDeserializer(LocalDateTime.class, new CustomJsonLocalDateTimeDeserializer());
		registerModule(module);
	}
}
