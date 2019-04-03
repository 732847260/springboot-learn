package com.springboot.lay;

import com.springboot.lay.ThreadRedis;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 10:01 2019/3/29
 * @Modified By: LiangZF
 */
public class Test002 {
    public static void main(String[] args) {
       LockService lockService = new LockService();
        for (int i = 0; i < 50; i++) {
            new ThreadRedis(lockService).start();
        }
    }
}
