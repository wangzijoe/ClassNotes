package com.example.thread.lock;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        final LockAccount account = new LockAccount();
        final Bank bank = new Bank(account, 1);
        // 创建一个线程池
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Thread(bank));
        }
        // 执行所有线程
        for (Thread t : list) {
            t.start();
        }
        // 主线程等待3秒钟，再去内存中读取账户余额
        Thread.sleep(3000);
        log.info(JSON.toJSONString(account));
    }
}
