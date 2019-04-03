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
     * ��ʱʱ�� 5s
     */
    private static final int TIMEOUT = 5*1000;

    static Map<String,Integer> products;//ģ����Ʒ��Ϣ��
    static Map<String,Integer> stock;//ģ�����
    static Map<String,String> orders;//ģ���µ��ɹ��û���
    static{
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }

    private String queryMap(String productId){
        return "������Ƥ���ؽ̣�����"
                +products.get(productId)
                +"��,��ʣ:"+stock.get(productId)
                +"��,����Ʒ�ɹ��µ��û���:"
                +orders.size()+"��";
    }

    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }


    @GetMapping(value = "/seckilling" )
    public void Seckilling(String targetId){
        //����
        long time = System.currentTimeMillis() + TIMEOUT;
        if(!redisLockHelper.lock(targetId,String.valueOf(time))){
           // return "�Ŷ�����̫�࣬���Ժ�����.";
           // System.out.println("�Ŷ�����̫�࣬���Ժ�����.");
            throw new SellException(101,"�ܱ�Ǹ����̫���ˣ���������������~~");
        }
        int surplusCount = stock.get(targetId);
        //int surplusCount = 0;
        // 1.��ѯ����Ʒ��棬Ϊ0������ e.g. getStockByTargetId
        if(surplusCount==0){
          //  System.out.println("�����");s
            throw new SellException(100,"�����");
        }else {
            // 2.�µ� e.g. buyStockByTargetId

            orders.put(UUID.randomUUID().toString(),targetId);
            //3.����� ��������Ļ����߲����»���ֳ�����������µ��������ڼ������������Ȼ������ˣ������ڲ��������Ŀ�滹û�浽map��ȥ���µĲ����õ�����ԭ���Ŀ��
            surplusCount =surplusCount-1;
            System.out.println(surplusCount);
            try{
                Thread.sleep(500);//ģ������Ĵ���ʱ��
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            // 4.�����������ݿ� e.g. updateStockByTargetId
            stock.put(targetId,surplusCount);
            System.out.println(queryMap(targetId));
            // buyStockByTargetId �� updateStockByTargetId ����ͬ�����(��������)����֤ԭ���ԡ�
        }
        //����
        redisLockHelper.unlock(targetId,String.valueOf(time));
    }
}
