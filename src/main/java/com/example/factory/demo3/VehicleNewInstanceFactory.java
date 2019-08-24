package com.example.factory.demo3;

import java.util.HashMap;
import java.util.Map;

/**
 * 2、注册产品对象，并向每一个产品添加newInstance();方法，该方法返回自身类型相同的新实例
 */
public class VehicleNewInstanceFactory {

    private Map<String, Vehicle> map = new HashMap<>();

    protected void registerVehicle(String vehicleId, Vehicle vehicle) {
        this.map.put(vehicleId, vehicle);
    }

    protected Vehicle createVehicle(String vehicleId) {
        return this.map.get(vehicleId).newInstance();
    }
}
