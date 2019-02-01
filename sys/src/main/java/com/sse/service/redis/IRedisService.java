package com.sse.service.redis;

import org.springframework.stereotype.Service;

/**
 * <p></p>
 * author pczhao  <br/>
 * date  2019-01-31 10:49
 */

@Service("IRedisService")
public interface IRedisService<T> {

    void set(String key, T value);

    void set(String key, T value, long expireTime);

    Boolean exists(final String key);

    T get(String key);

    T getAndUpdateTime(String key);

    void delete(String key);
}
