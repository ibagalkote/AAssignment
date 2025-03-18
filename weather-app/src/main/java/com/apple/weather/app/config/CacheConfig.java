package com.apple.weather.app.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {
	
	private static Integer CACHE_EXPIRE_TIME_IN_MINUTES = 30;
	private static Integer CACHE_MAX_SIZE = 100;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("weather");
        cacheManager.setCaffeine(caffeineConfig());
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(CACHE_EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES) // Expire cache after 30 minutes
                .maximumSize(CACHE_MAX_SIZE) // Maximum 100 entries in cache
                .recordStats();
    }
}