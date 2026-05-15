package com.dps.profile_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class RedisCacheErrorHandler implements CacheErrorHandler {

 @Override
 public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
     log.error("Cache GET failed for cache: {}, key: {}. Falling back to DB.", 
               cache.getName(), key, exception);
 }

 @Override
 public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
     log.error("Cache PUT failed for cache: {}, key: {}. Continuing without cache.", 
               cache.getName(), key, exception);
 }

 @Override
 public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
     log.error("Cache EVICT failed for cache: {}, key: {}. Ignoring.", 
               cache.getName(), key, exception);
 }

 @Override
 public void handleCacheClearError(RuntimeException exception, Cache cache) {
     log.error("Cache CLEAR failed for cache: {}. Ignoring.", 
               cache.getName(), exception);
 }
}