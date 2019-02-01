package com.sse.service.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-01-31 10:49
 */

@Service
public class RedisService<T> implements IRedisService<T> {

    private RedisTemplate<String, T> redisTemplate;

    @Autowired
    public RedisService(@Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.redisTemplate = (RedisTemplate<String, T>) redisTemplate;
    }

    /*
     * 一天有多少分钟，默认时间是一天
     */
    private static final long MINUTES_OF_ONE_DAY = 24 * 60;

    /*
     * 默认有效时间是一个小时
     */
    private static final long DEFAULT_ACTIVE_TIME = MINUTES_OF_ONE_DAY;


    /**
     * 将 key，value 存放到redis数据库中，默认设置过期时间为一天
     *
     * @param key   键值
     * @param value 值
     */
    public void set(String key, T value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            set(key, value, DEFAULT_ACTIVE_TIME);
        }
    }

    /**
     * 将 key，value 存放到redis数据库中，设置过期时间单位是分钟
     *
     * @param key        键
     * @param value      值
     * @param expireTime 单位是分钟
     */
    public void set(String key, T value, long expireTime) {
        if (StringUtils.isNotBlank(key) && value != null) {
            ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value, expireTime, TimeUnit.MINUTES);
        }
    }

    /**
     * 判断 key 是否在 redis 数据库中
     *
     * @param key 键
     */
    public Boolean exists(final String key) {
        if (StringUtils.isNotBlank(key)) {
            return redisTemplate.hasKey(key);
        }
        return false;
    }


    /**
     * 获取 key 对应的值
     *
     * @param key 键
     */
    public T get(String key) {
        if (StringUtils.isNotBlank(key)) {
            ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(key);
        }
        return null;
    }

    /**
     * 获得 key 对应的键值，并更新缓存时间，时间长度为默认值
     *
     * @param key 键
     */
    public T getAndUpdateTime(String key) {
        T result = get(key);
        if (result != null) {
            set(key, result);
        }
        return result;
    }

    /**
     * 删除 key 对应的 value
     *
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
