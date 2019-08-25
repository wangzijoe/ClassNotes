package com.example.factory.builder.demo1;

/**
 * 电动车建造者
 */
class ElectricCarBuilder extends CarBuilder {

    private Car car;

    @Override
    void buildCar() {
        car = new Car();
    }

    @Override
    void addEngine(String type) {
        this.car.setEngine(type);
    }

    @Override
    void addWheel(String type) {
        this.car.setWheel(type);
    }

    @Override
    void paint(String color) {
        this.car.setColor(color);
    }

    @Override
    void addTransmission(String type) {
        this.car.setTransmission(type);
    }

    @Override
    void addGasTank(String capacity) {
        throw new IllegalArgumentException();
    }

    @Override
    void addBatteries(String capacity) {
        this.car.setBatteries(capacity);
    }

    @Override
    Car getCar() {
        return this.car;
    }
}
