package com.example.factory.builder.demo1;

/**
 * 汽油车建造者
 */
class GasolineCarBuilder extends CarBuilder {

    private Car car;

    @Override
    void buildCar() {
        this.car = new Car();
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
        this.car.setGasTank(capacity);
    }

    @Override
    void addBatteries(String capacity) {
        throw new IllegalArgumentException();

    }

    @Override
    Car getCar() {
        return this.car;
    }
}
