package com.example.factory.builder.demo1;

class CarBuilderDirector {

    /**
     * 构造电动汽车
     *
     * @param builder 建造者基类
     * @return Car对象
     */
    Car buildElectricCar(CarBuilder builder) {
        builder.buildCar();
        builder.addEngine("Electric 150kW");
        builder.addBatteries("1500 kWh");
        builder.addTransmission("Manual");
        builder.addWheel("20*12*30");
        builder.paint("red");
        return builder.getCar();
    }

    Car buildGasolineCar(CarBuilder builder) {
        builder.buildCar();
        builder.addEngine("Gasoline Engine");
        builder.addWheel("20*12*30");
        builder.addGasTank("200L");
        builder.paint("black");
        return builder.getCar();
    }
}
