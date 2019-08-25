package com.example.factory.builder.demo1;

/**
 * 建造者基类
 */
abstract class CarBuilder {

    abstract void buildCar();

    abstract void addEngine(String type);

    abstract void addWheel(String type);

    abstract void paint(String color);

    abstract void addTransmission(String type);

    abstract void addGasTank(String capacity);

    abstract void addBatteries(String capacity);

    abstract Car getCar();
}
