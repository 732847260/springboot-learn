package com.springboot.lock.controller;

import com.springboot.lock.common.RedisCommon;
import com.springboot.lock.exception.SellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.KeyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 15:33 2019/3/26
 * @Modified By: LiangZF
 */
@RestController
public class RedisController {

    @Autowired
    RedisCommon redisLockHelper;

    /**
     * 超时时间 5s
     */
    private static final int TIMEOUT = 5*1000;

    static Map<String,Integer> products;//模拟商品信息表
    static Map<String,Integer> stock;//模拟库存表
    static Map<String,String> orders;//模拟下单成功用户表
    static{
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }

    private String queryMap(String productId){
        return "国庆活动，皮蛋特教，限量"
                +products.get(productId)
                +"份,还剩:"+stock.get(productId)
                +"份,该商品成功下单用户数:"
                +orders.size()+"人";
    }

    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }


    @GetMapping(value = "/seckilling" )
    public void Seckilling(String targetId){
        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if(!redisLockHelper.lock(targetId,String.valueOf(time))){
           // return "排队人数太多，请稍后再试.";
           // System.out.println("排队人数太多，请稍后再试.");
            throw new SellException(101,"很抱歉，人太多了，换个姿势再试试~~");
        }
        int surplusCount = stock.get(targetId);
        //int surplusCount = 0;
        // 1.查询该商品库存，为0则活动结束 e.g. getStockByTargetId
        if(surplusCount==0){
          //  System.out.println("活动结束");s
            throw new SellException(100,"活动结束");
        }else {
            // 2.下单 e.g. buyStockByTargetId

            orders.put(UUID.randomUUID().toString(),targetId);
            //3.减库存 不做处理的话，高并发下会出现超卖的情况，下单数，大于减库存的情况。虽然这里减了，但由于并发，减的库存还没存到map中去。新的并发拿到的是原来的库存
            surplusCount =surplusCount-1;
            System.out.println(surplusCount);
            try{
                Thread.sleep(500);//模拟减库存的处理时间
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            // 4.减库存操作数据库 e.g. updateStockByTargetId
            stock.put(targetId,surplusCount);
            System.out.println(queryMap(targetId));
            // buyStockByTargetId 和 updateStockByTargetId 可以同步完成(或者事物)，保证原子性。
        }
        //解锁
        redisLockHelper.unlock(targetId,String.valueOf(time));
    }
}
