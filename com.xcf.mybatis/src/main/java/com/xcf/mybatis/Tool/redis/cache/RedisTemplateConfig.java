package com.xcf.mybatis.Tool.redis.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xcf
 * @Date 创建时间：2021年4月2日 上午9:49:27 配置redis相关信息
 */
@Configuration
@Slf4j
public class RedisTemplateConfig {

	/**
	 * RedisTemplate配置
	 *
	 * @param factory           连接工厂
	 * @param redisObjectMapper redis专用的ObjectMapper
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> soRedisTemplate(RedisConnectionFactory factory,
			@Qualifier("noTypeObjectMapper") ObjectMapper redisObjectMapper) {

		var template = new RedisTemplate<String, Object>();
		// 配置连接工厂
		template.setConnectionFactory(factory);
		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		var jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
		jacksonSeial.setObjectMapper(redisObjectMapper);
		// 值采用json序列化
		template.setValueSerializer(jacksonSeial);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		// 设置hash key 和value序列化模式
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jacksonSeial);
		template.afterPropertiesSet();

		return template;
	}

	/**
	 * 通用泛型RedisTemplate配置
	 *
	 * @param factory 连接工厂
	 * @return RedisTemplate
	 */
	@Bean
	public <T> RedisTemplate<String, T> genericRedisTemplate(RedisConnectionFactory factory,
			@Qualifier("hasTypeObjectMapper") ObjectMapper hasTypeObjectMapper) {

		var template = new RedisTemplate<String, T>();
		var serializer = new Jackson2JsonRedisSerializer<>(Object.class);

		serializer.setObjectMapper(hasTypeObjectMapper);

		template.setConnectionFactory(factory);
		// 值采用json序列化
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);
		template.afterPropertiesSet();

		return template;
	}

	@Bean
	public RedisTemplateWrapper redisTemplateWrapper(RedisConnectionFactory factory,
			@Qualifier("noTypeObjectMapper") ObjectMapper redisObjectMapper) {
		var redisTemplateWrapper = new RedisTemplateWrapper(redisObjectMapper);

		var serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		redisTemplateWrapper.setConnectionFactory(factory);
		redisTemplateWrapper.setKeySerializer(new StringRedisSerializer());
		redisTemplateWrapper.setHashKeySerializer(new StringRedisSerializer());
		redisTemplateWrapper.setValueSerializer(serializer);
		redisTemplateWrapper.setHashValueSerializer(serializer);
		redisTemplateWrapper.afterPropertiesSet();

		return redisTemplateWrapper;
	}

	@Bean
	public RedisTemplate<String, String> ssRedisTemplate(RedisConnectionFactory factory,
			@Qualifier("noTypeObjectMapper") ObjectMapper redisObjectMapper) {

		var template = new RedisTemplate<String, String>();
		// 配置连接工厂
		template.setConnectionFactory(factory);
		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		var jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
		jacksonSeial.setObjectMapper(redisObjectMapper);
		// 值采用json序列化
		template.setValueSerializer(jacksonSeial);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		// 设置hash key 和value序列化模式
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jacksonSeial);
		template.afterPropertiesSet();

		return template;
	}

	@Bean
	ObjectMapper noTypeObjectMapper(Jackson2ObjectMapperBuilder builder) {

		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
		return objectMapper;
	}

	@Bean
	ObjectMapper hasTypeObjectMapper(Jackson2ObjectMapperBuilder builder) {

		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
				ObjectMapper.DefaultTyping.NON_FINAL);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
		return objectMapper;
	}

}