package com.springboot.lay;

public class ThreadRedis extends Thread {
    private LockService lockService;

    public ThreadRedis(LockService lockService) {
        this.lockService = lockService;
    }

    @Override
    public void run() {
        //lockService.seckills();
        lockService.seckill();
    }
}