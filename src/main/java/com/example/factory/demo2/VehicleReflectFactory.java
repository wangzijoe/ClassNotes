package com.example.factory.demo2;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态工厂类
 * 1、使用反射机制注册产品类对象和实例化
 */
public class VehicleReflectFactory {

  @SuppressWarnings("rawtypes")
    private Map<String, Class> map = new HashMap<>();

  @SuppressWarnings("rawtypes")
    protected void registerVehicle(String vehicleId, Class vehicleClass) {
        map.put(vehicleId, vehicleClass);
    }

    /**
     * 在某些情况下，反射机制并不适用，
     * 1、反射需要用到运行时权限
     * 2、反射机制会降低程序的运行效率，高性能场景下避免使用该机制
     * 此时我们可以使用 注册产品对象，并向每一个产品添加newInstance();方法，该方法返回自身类型相同的新实例
     */
  @SuppressWarnings("rawtypes")
    protected Vehicle createVehicle(String vehicleId) throws IllegalAccessException, InstantiationException {
        Class clazz = this.map.get(vehicleId);
        return (Vehicle) clazz.newInstance();
    }
}
