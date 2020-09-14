package com.icss.newretail.config.redis.util;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 重写Redis序列化方式，使用Json方式
 * RedisTemplate默认使用的是JdkSerializationRedisSerializer，
 * StringRedisTemplate默认使用的是StringRedisSerializer。 
 * Spring-data-redis为我们提供了下面的Serializer：
 * 			GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、
 * 			JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。 
 * 在此我们将自己配置RedisTemplate并定义Serializer
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private Class<T> clazz;

	public FastJsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, DEFAULT_CHARSET);
		return (T) JSON.parseObject(str, clazz);
	}
}