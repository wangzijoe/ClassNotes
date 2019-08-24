package com.example.factory.demo4;

/**
 * 工厂方法模式
 */
public abstract class VehicleFactory {

    protected abstract Vehicle createVehicle(String vehicleType);

    Vehicle orderVehicle(String vehicleType, String color) {
        Vehicle vehicle = createVehicle(vehicleType);
        vehicle.setColor(color);
        return vehicle;
    }
}
