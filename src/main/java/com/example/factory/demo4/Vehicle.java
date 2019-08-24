package com.example.factory.demo4;

import lombok.Data;

/**
 * 交通工具抽象类
 */
@Data
public abstract class Vehicle {

    private String name;

    private String color;

    Vehicle() {
    }

    public Vehicle(String name) {
        this.name = name;
    }

}
