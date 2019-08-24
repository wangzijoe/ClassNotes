package com.example.factory.demo4;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {

        VehicleFactory carFactory = new CarFactory();
        Vehicle vehicle = carFactory.orderVehicle("Sedan", "red");
        log.info(JSON.toJSONString(vehicle, true));

        final TruckFactory truckFactory = new TruckFactory();
        final Vehicle vehicle1 = truckFactory.orderVehicle("Large", "blue");
        log.info(JSON.toJSONString(vehicle1, true));

        // 匿名具体工厂模式
        final VehicleFactory factory = new VehicleFactory() {

            @Override
            protected Vehicle createVehicle(String vehicleType) {
                if (vehicleType.equals("Mountain")) return new MountainBike();
                if (vehicleType.equals("City")) return new CityBike();
                return null;
            }
        };
        final Vehicle vehicle2 = factory.orderVehicle("City", "white");
        log.info(JSON.toJSONString(vehicle2, true));
    }
}
