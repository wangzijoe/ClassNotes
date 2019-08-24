package com.example.factory.demo4;

public class TruckFactory extends VehicleFactory {


    @Override
    protected Vehicle createVehicle(String vehicleType) {
        if (vehicleType.equals("Small")) return new SmallTruck();
        if (vehicleType.equals("Large")) return new LargeTruck();
        return null;
    }
}
