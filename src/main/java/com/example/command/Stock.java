package com.example.command;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 股票类，这是一个请求类
 */
@Data
class Stock {

    private String name;

    private int quantity;

    void buy() {
        System.out.println("Stock.buy" + JSON.toJSONString(this));
    }

    void sell() {
        System.out.println("Stock.sell" + JSON.toJSONString(this));
    }
}
