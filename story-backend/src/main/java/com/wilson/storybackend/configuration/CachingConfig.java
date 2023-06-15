package com.wilson.storybackend.configuration;

import lombok.extern.java.Log;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.util.Date;

@Configuration
@EnableCaching
@EnableScheduling
@Log
public class CachingConfig {
    public static final String SUM_HISTORY = "sumHistory";
    public static final String HISTORY = "history";
    public static final String STORY = "story";
    public static final String STORY_LIST = "storyList";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(SUM_HISTORY,
                HISTORY, STORY, STORY_LIST);
    }

    @Caching(evict = {
            @CacheEvict(allEntries = true, value = SUM_HISTORY),
            @CacheEvict(allEntries = true, value = HISTORY),
            @CacheEvict(allEntries = true, value = STORY),
            @CacheEvict(allEntries = true, value = STORY_LIST)})
    @Scheduled(fixedDelay = 30 * 1000, initialDelay = 500)
    public void cacheEvict() {
        log.info("Flush Cache " + DateFormat.getInstance().format(new Date()));
    }

}