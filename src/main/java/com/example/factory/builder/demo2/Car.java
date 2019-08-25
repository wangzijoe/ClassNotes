package com.example.factory.builder.demo2;

import lombok.Data;

@Data
public class Car {
    private String engine;
    private String wheel;
    private String color;
    private String transmission;// 传动机构
    private String gasTank;
    private String batteries;

    //私有构造方法，只被Builder类使用
    private Car(Builder builder) {
        this.engine = builder.engine;
        this.wheel = builder.wheel;
        this.color = builder.color;
        this.transmission = builder.transmission;
        this.gasTank = builder.gasTank;
        this.batteries = builder.batteries;
    }

    static class Builder {
        private String engine;
        private String wheel;
        private String color;
        private String transmission;
        private String gasTank;
        private String batteries;

        public Car build() {
            return new Car(this);
        }

        public Builder setEngine(String engine) {
            this.engine = engine;
            return this;
        }

        public Builder setWheel(String wheel) {
            this.wheel = wheel;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setTransmission(String transmission) {
            this.transmission = transmission;
            return this;
        }

        public Builder setGasTank(String gasTank) {
            this.gasTank = gasTank;
            return this;
        }

        public Builder setBatteries(String batteries) {
            this.batteries = batteries;
            return this;
        }
    }
}
