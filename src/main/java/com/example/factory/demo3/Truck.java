package com.example.factory.demo3;

/**
 * 卡车类
 */
public class Truck extends Vehicle {
    @Override
    public Vehicle newInstance() {
        return new Truck();
    }
}
