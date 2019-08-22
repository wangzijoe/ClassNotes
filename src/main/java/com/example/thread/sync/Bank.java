package com.example.thread.sync;

public class Bank implements Runnable {

    private Account account;

    private int amount;

    public Bank(Account account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.add(amount);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
