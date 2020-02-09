package com.endava.practice.roadmap.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent( CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        log.info("New cache for the key: {}; oldValue: {}; newValue: {}",
                cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}

