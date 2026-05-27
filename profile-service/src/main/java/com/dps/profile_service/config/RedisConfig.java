package com.dps.profile_service.config;

import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig implements CachingConfigurer {

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.activateDefaultTyping(BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
						ObjectMapper.DefaultTyping.NON_FINAL);

		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)).disableCachingNullValues()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer cacheManagerCustomizer(RedisCacheConfiguration baseCacheConfiguration) {
		return builder -> builder
				// 30mins TTL for PRofile service
				.withCacheConfiguration("profile", baseCacheConfiguration.entryTtl(Duration.ofMinutes(30)))
				// bank account only 60mins very rare change
				.withCacheConfiguration("profile-bank", baseCacheConfiguration.entryTtl(Duration.ofMinutes(60)));
	}

	@Override
	@Bean
	public CacheErrorHandler errorHandler() {
		return new RedisCacheErrorHandler();
	}
}