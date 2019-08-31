package com.example.command;

/**
 * 使用 Broker 类来接受并执行命令。
 */
public class CommandPatternDemo {

    public static void main(String[] args) {
        Stock stock = new Stock();
        stock.setName("ABC");
        stock.setQuantity(123);

        Order buyStockOrder = new BuyStock(stock);
        Order sellStockOrder = new SellStock(stock);

        Broker broker = new Broker();
        broker.takeOrder(buyStockOrder);
        broker.takeOrder(sellStockOrder);

        broker.placeOrders();
    }
}
