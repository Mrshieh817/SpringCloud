package com.xcf.mybatis.Tool.redis.cache;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;

/** 
* @author xcf 
* @Date 创建时间：2021年4月2日 上午9:41:48 
* <h2>带过期时间的RedisCache</h2>
*/
@Slf4j
public class TTLRedisCache extends RedisCache {

    private final RedisCacheWriter cacheWriter;
    private final String name;
    /**
     * <h3>存活时间</h3>
     */
    private final Long TTL;

    /**
     * <h3>空值缓存存活时间 [默认值:5]</h3>
     */
    private Long nullKeyTTL;

    /**
     * <h3>开启随机延迟 [默认值:true]</h3>
     */
    private Boolean randomDelay;

    @Override
    public synchronized <T> T get(Object key, Callable<T> valueLoader) {

        log.debug("命中缓存:{}",key);
        return super.get(key, valueLoader);
    }

    @Override
    public ValueWrapper get(Object key) {

        log.debug("命中缓存:{}",key);
        System.out.println(MessageFormat.format("命中缓存:{0}",key));
        return super.get(key);
    }


    @Override
    public <T> T get(Object key, Class<T> type) {

        log.debug("命中缓存:{}",key);
        return super.get(key, type);
    }

    protected TTLRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, Long TTL) {
        super(name, cacheWriter, cacheConfig);

        Assert.isTrue(cacheConfig.getAllowCacheNullValues(), "TTLRedisCache must be true");
        Assert.isTrue(null != TTL && TTL > 0, "ttl must be positive integer");
        this.TTL = TTL;
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.nullKeyTTL = 5L;
        this.randomDelay = true;
    }

    public void setNullKeyTTL(Long nullKeyTTL) {

        if (Objects.nonNull(nullKeyTTL) && nullKeyTTL > 0) {
            this.nullKeyTTL = nullKeyTTL;
        }
    }

    public void isRandomDelay(Boolean randomDelay) {

        if (Objects.nonNull(randomDelay)) {
            this.randomDelay = randomDelay;
        }
    }

    @Override
    public void put(@NonNull Object key, @Nullable Object value) {

        Object cacheValue = preProcessCacheValue(value);
        if (!isAllowNullValues() && cacheValue == null) {
            throw new IllegalArgumentException(String.format(
                    "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                    name));
        }

        // 当value为空时直接使用nullKeyTTl 若已经开启随机延迟且value非空，则随机一个延迟
        Long finalTTL = Objects.equals(NullValue.INSTANCE, cacheValue) ? nullKeyTTL : (randomDelay ? addRandomDelay(TTL) : TTL);

        log.debug("存入缓存:{\"key\":\"{}\",\"ttl\":{}}",key,finalTTL);
        cacheWriter.put(name, serializeCacheKey(super.createCacheKey(key)), super.serializeCacheValue(cacheValue), Duration.ofSeconds(finalTTL));
    }

    private Long addRandomDelay(Long ttl) {

        return ttl + RandomUtils.nextInt(0, 10);
    }
}