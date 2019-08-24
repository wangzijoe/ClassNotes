package com.example.factory.demo1;

/**
 * 静态工厂类
 * 只负责Vehicle的实例化，符合单一职责原则，用户只调用Vehicle接口，
 * 无需关心Vehicle创建的过程，这样做可以减少耦合，符合以来倒置原则：
 * 但是，当增加一个新的Vehicle类时，需要对VehicleFactory进行修改，
 * 这样就打破了开闭原则。
 * <p>
 * 我们可以修改这种简单工厂模式，使得新注册的Vehicle类，在使用的时候
 * 才被实例化，从而保证其对扩展开放，对修改关闭
 * 具体实现方式有两种：
 * 1、使用反射机制注册产品类对象和实例化
 * 2、注册产品对象，并向每一个产品添加newInstance();方法，该方法返回自身类型相同的新实例
 */
public class VehicleFactory {

    public enum VehicleType {
        Bike, Car, Truck
    }

    protected static Vehicle create(VehicleType type) {
        if (type.equals(VehicleType.Bike)) return new Bike();
        if (type.equals(VehicleType.Car)) return new Car();
        if (type.equals(VehicleType.Truck)) return new Truck();
        return null;
    }

}
