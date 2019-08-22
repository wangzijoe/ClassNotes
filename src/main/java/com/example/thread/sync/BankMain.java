package com.example.thread.sync;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BankMain {

    // 100 个银行同时向同一账户打一块钱
    public static void main(String[] args) throws InterruptedException {
        // 创建共同资源
        Account account = new Account();
        // 创建一个银行对象
        Bank bank = new Bank(account, 1);
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
