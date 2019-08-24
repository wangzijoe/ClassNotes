package com.example.factory.demo3;

import lombok.Data;

/**
 * 交通工具抽象类
 */
@Data
public abstract class Vehicle {

    private String name;

    Vehicle() {
    }

    public Vehicle(String name) {
        this.name = name;
    }

    public abstract Vehicle newInstance();

}
