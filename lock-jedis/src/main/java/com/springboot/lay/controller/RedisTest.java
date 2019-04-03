package com.springboot.lay.controller;

import com.springboot.lay.common.RedisCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: LiangZF
 * @Description: 秒杀下单手机
 * @Date: Created in 16:34 2019/3/29
 * @Modified By: LiangZF
 */
@RestController
@Slf4j
public class RedisTest {
    @Autowired
    RedisCommon redisCommon;
    //private static Logger log = LoggerFactory.getLogger(RedisTest.class);
    static Map<String, Integer> phone = new HashMap<>();
    static Map<String, String> order = new HashMap<>();
    private int timeOut = 30 * 1000;
    // 手机库存
    int phoneNum;

    static {
        phone.put("xiaomi", 100);
        phone.put("apple", 30);
    }


    private String queryMap(String phoneName) {
        return "还剩:" + phone.get(phoneName)
                + "份该商品成功下单用户数:"
                + order.size() + "人";
    }

    @RequestMapping(value = "skill", method = RequestMethod.GET)
    public String skills(String phoneName) {
        String token = UUID.randomUUID().toString();
        // 用户开抢时间
        long startTime = System.currentTimeMillis();
        // 最大超时时间
        long endTime = System.currentTimeMillis() + timeOut;
        while (startTime < endTime) {
            // 60秒
            if (!redisCommon.lock(phoneName, token, 60000l)) {
                log.info("人太多，抢不到!");
                return "人太多，抢不到!";
            } else {
                try {
                    phoneNum = phone.get(phoneName);
                    if (phoneNum <= 0) {
                        log.info("活动结束!");
                        break;
                    }
                    // 下单成功用户表
                    order.put(token, phoneName);
                    phoneNum = phoneNum - 1;
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    phone.put(phoneName, phoneNum);
                    log.info("抢到了!");
                    System.out.println(queryMap(phoneName));
                    return "抢到了!";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (redisCommon.unLock(phoneName, token)) {
                        log.info("解锁");
                    }
                }
            }
        }
        return "123";
    }


}
