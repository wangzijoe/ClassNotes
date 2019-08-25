package com.example.factory.builder.demo2;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    /**
     * 拥有方法链的匿名建造者模式
     */
    public static void main(String[] args) {
        final Car car = new Car.Builder()
                .setEngine("gasoline engine")
                .setGasTank("200L")
                .setColor("red")
                .setTransmission("manual")
                .setWheel("20*12*30")
                .build();
        log.info(JSON.toJSONString(car, true));
    }
}
