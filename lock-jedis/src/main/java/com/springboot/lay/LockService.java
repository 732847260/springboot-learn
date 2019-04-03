package com.springboot.lay;

import com.springboot.lay.LockRedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 10:00 2019/3/29
 * @Modified By: LiangZF
 */
public class LockService {
    private static JedisPool pool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "127.0.0.1", 6379, 3000,"123456");
    }

    private LockRedis lockRedis = new LockRedis(pool);

    // 演示redis实现分布式锁   第一个
    public void seckill() {
        // 1.获取锁
        String identifierValue = lockRedis.getRedisLock(5000l, 5000l);
        if (identifierValue == null) {
            System.out.println(Thread.currentThread().getName() + ",获取锁失败，原因因为获取锁时间超时...");
            return;
        }
        try{
            Thread.sleep(100);//模拟减库存的处理时间
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ",获取锁成功,锁的id:" + identifierValue + ",正常执行业务了逻辑");

        // 2.释放锁
        lockRedis.unRedisLock(identifierValue);
    }

    // 演示redis实现分布式锁   第二个
    public void seckills() {
        String token = UUID.randomUUID().toString();
        // 1.获取锁
        if (!lockRedis.lock(token)) {
            System.out.println(Thread.currentThread().getName() + ",获取锁失败，原因因为获取锁时间超时...");
            return;
        }
        try{
            Thread.sleep(100);//模拟减库存的处理时间
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ",获取锁成功,锁的id:" + token + ",正常执行业务了逻辑");

        // 2.释放锁
        lockRedis.releaseDistributedLock(token);
    }
}
