package com.springboot.lay.controller;

import com.springboot.lay.common.RedisCommon;
import com.springboot.lay.config.RedisConfig;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: LiangZF
 * @Description: 同步流秒杀抢单
 * @Date: Created in 16:44 2019/3/30
 * @Modified By: LiangZF
 */
@RestController
public class RedisKills {

    @Autowired
    RedisCommon redisCommon;

    private long kcNum = 0;

    private String goodName = "xiaomi";

    private int timeOut = 30 * 1000;

    private static Logger log = LoggerFactory.getLogger(RedisKills.class);

    @GetMapping(value = "qingdan")
    public List<String> qiangdan(){
        // 构造用户
        List<String> users = new ArrayList<>();

        // 抢到商品的用户
        List<String> shopUsers = new ArrayList<>();

        IntStream.range(0,100000).parallel().forEach(a -> {
            users.add("小米-"+a);
        });
        // 初始化库存
        kcNum = 10;
        // 模拟开抢 ,同步流
        users.parallelStream().forEach(a->{
            String shopUser = skill(a);
            if(!StringUtils.isEmpty(shopUser)){
                shopUsers.add(shopUser);
            }
        });
        return shopUsers;
    }

    /*
     *
     * @Description: 抢购
     *
     * @auther: LiangZF
     * @date: 17:56 2019/3/30
     * @param: [b]
     * @return: java.lang.String
     *
     */
    public String skill(String b){
        // 用户开抢时间
        long startTime = System.currentTimeMillis();

        long endTime =  System.currentTimeMillis() + timeOut;

        // 在一定时间内循环尝试获得锁
        while((endTime >= startTime)){
            if(kcNum <= 0){
               break;
            }
            if(redisCommon.lock(goodName,b,60000l)){
                log.info("用户拿到锁",b);
                try {
                    if (kcNum <= 0) {
                        break;
                    }
                    //模拟生成订单耗时操作，方便查看 多次获取锁记录
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //抢购成功，商品递减，记录用户
                    kcNum -= 1;

                    log.info("用户{}抢单成功跳出...所剩库存：{}", b, kcNum);

                    return b + "抢单成功，所剩库存：" + kcNum;
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    log.info("用户{}释放锁...", b);
                    //释放锁
                   redisCommon.unLock(goodName,b);
                }
            }
        }
        return "";
    }


}
