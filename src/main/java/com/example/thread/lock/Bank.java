package com.example.thread.lock;

public class Bank implements Runnable {

    private LockAccount lockAccount;

    private int amount;

    public Bank(LockAccount lockAccount, int amount) {
        this.lockAccount = lockAccount;
        this.amount = amount;
    }

    @Override
    public void run() {
        this.lockAccount.add(amount);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
