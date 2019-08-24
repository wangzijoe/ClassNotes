package com.example.factory.demo3;

public class Car extends Vehicle {
    @Override
    public Vehicle newInstance() {
        return new Car();
    }
}
