package com.example.thread.sync;

import lombok.Data;

@Data
public class Account {

    private int balance;

    protected synchronized void add(int ammount) {
        balance = balance + ammount;
    }
}
