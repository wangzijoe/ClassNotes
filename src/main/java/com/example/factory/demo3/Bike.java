package com.example.factory.demo3;

public class Bike extends Vehicle {
    @Override
    public Vehicle newInstance() {
        return new Bike();
    }
}
