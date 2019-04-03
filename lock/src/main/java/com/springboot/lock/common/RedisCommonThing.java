package com.springboot.lock.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 12:21 2019/3/28
 * @Modified By: LiangZF
 */
@Component
public class RedisCommonThing {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public boolean lock(String targetId,String token,long timeStamp){
        Boolean b = (Boolean) stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer valueSerializer = stringRedisTemplate.getValueSerializer();
                RedisSerializer keySerializer = stringRedisTemplate.getKeySerializer();
                Object obj = connection.execute("set", keySerializer.serialize(targetId),
                        valueSerializer.serialize(token),
                        SafeEncoder.encode("NX"),
                        SafeEncoder.encode("PX"),
                        Protocol.toByteArray(timeStamp));
                return obj != null;
            }
        });
        return b;
    }
    public void unlock(String targetId ,String token){
        String uuid = stringRedisTemplate.opsForValue().get(targetId);
        if(uuid.equals(token)){
            stringRedisTemplate.opsForValue().getOperations().delete(targetId);
        }

    }


}

