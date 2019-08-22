package com.example.thread.lock;

import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class LockAccount {

    private Lock lock = new ReentrantLock();

    private int balance;

    protected void add(int amount) {
        try {
            lock.lock();
            balance = balance + amount;
        } finally {
            lock.unlock();
        }
    }
}
