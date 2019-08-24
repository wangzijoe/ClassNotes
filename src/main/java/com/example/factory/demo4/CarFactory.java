package com.example.factory.demo4;

public class CarFactory extends VehicleFactory {


    @Override
    protected Vehicle createVehicle(String vehicleType) {
        if (vehicleType.equals("sport")) return new SportCar();
        if (vehicleType.equals("Sedan")) return new SedanCar();
        return null;
    }
}
