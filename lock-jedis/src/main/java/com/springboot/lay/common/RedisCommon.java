package com.springboot.lay.common;

import com.springboot.lay.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @Author: LiangZF
 * @Description: redis分布式锁工具类
 * @Date: Created in 16:07 2019/3/29
 * @Modified By: LiangZF
 */
@Component
public class RedisCommon {
    @Autowired
    private JedisPool jedisPool;
    private static Logger log = LoggerFactory.getLogger(RedisConfig.class);

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    //PX：key过期时间单位设置为毫秒（EX：单位秒）
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    // SETNX

    /*
     *  上锁
     */
    public boolean lock(String lockKey,String token,long expireTime){
        Jedis conn = null;
        try{
            conn = jedisPool.getResource();
            if (conn == null) {
                log.info("JedisPool为，检查是否打开了redis");
                return false;
            }
            String result = conn.set(lockKey,token , SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            System.out.println(result);
            if (LOCK_SUCCESS.equals(result)) {
                log.info("获取锁成功"+token);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return false;
    }

    /*
     *
     * @Description:  解锁
     *
     * @auther: LiangZF
     * @date: 16:33 2019/3/29
     * @param: [lockKey, token]
     * @return: boolean
     *
     */


    public boolean unLock(String lockKey,String token){
        Jedis conn = null;
        try{
            conn = jedisPool.getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = conn.eval(script, Collections.singletonList(lockKey), Collections.singletonList(token));
            if (RELEASE_SUCCESS.equals(result)) {
                log.info("释放锁..." + Thread.currentThread().getName() + ",token:" + token);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            conn.close();
        }
        return false;
    }

}
