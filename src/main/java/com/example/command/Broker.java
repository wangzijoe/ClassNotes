package com.example.command;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令调用者类，经纪人
 */
class Broker {


    private List<Order> orderList = new ArrayList<>();

    void takeOrder(Order order) {
        orderList.add(order);
    }

    void placeOrders() {
        for (Order order : orderList) {
            order.execute();
        }
        orderList.clear();
    }
}
