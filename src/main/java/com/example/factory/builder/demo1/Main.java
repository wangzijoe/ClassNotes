package com.example.factory.builder.demo1;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {


    public static void main(String[] args) {
        final CarBuilderDirector director = new CarBuilderDirector();
        final Car gasolineCar = director.buildGasolineCar(new GasolineCarBuilder());
        log.info(JSON.toJSONString(gasolineCar));
        final Car electricCar = director.buildElectricCar(new ElectricCarBuilder());
        log.info(JSON.toJSONString(electricCar));


    }
}
