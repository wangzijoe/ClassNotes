package com.example.command;

public class SellStock implements Order {

    private Stock stock;

    SellStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}
