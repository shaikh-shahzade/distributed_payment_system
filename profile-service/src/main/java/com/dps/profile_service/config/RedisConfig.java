package com.dps.profile_service.config;


import java.time.Duration;

import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
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
public class RedisConfig implements CachingConfigurer{

 @Bean
 public RedisCacheConfiguration cacheConfiguration() {
     return RedisCacheConfiguration.defaultCacheConfig()
             .entryTtl(Duration.ofMinutes(30))
             .disableCachingNullValues()
             .serializeKeysWith(
                     RedisSerializationContext.SerializationPair
                             .fromSerializer(new StringRedisSerializer()))
             .serializeValuesWith(
                     RedisSerializationContext.SerializationPair
                             .fromSerializer(new GenericJackson2JsonRedisSerializer()));
 }

 @Bean
 public RedisCacheManagerBuilderCustomizer cacheManagerCustomizer() {
     return builder -> builder
             // full profile — 30 min TTL
             .withCacheConfiguration("profile",
                     RedisCacheConfiguration.defaultCacheConfig()
                             .entryTtl(Duration.ofMinutes(30)))
             // bank account only — 60 min TTL (changes very rarely)
             .withCacheConfiguration("profile-bank",
                     RedisCacheConfiguration.defaultCacheConfig()
                             .entryTtl(Duration.ofMinutes(60)));
 }
 
 @Override
 @Bean
 public CacheErrorHandler errorHandler() {
     return new RedisCacheErrorHandler();
 }
}