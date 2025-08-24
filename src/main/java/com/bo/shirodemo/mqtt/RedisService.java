package com.bo.shirodemo.mqtt;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final StringRedisTemplate template;

    public RedisService(StringRedisTemplate template) {
        this.template = template;
    }

    public void updateHeartbeat(Long deviceId) {
        String key = "device:heartbeat:" + deviceId;
        template.opsForValue().set(key, String.valueOf(Instant.now().toEpochMilli()), 2, TimeUnit.MINUTES);
    }

    public boolean isOnline(String deviceId) {
        return Boolean.TRUE.equals(template.hasKey("device:heartbeat:" + deviceId));
    }
}
