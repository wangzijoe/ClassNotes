package com.example.responsiblity;

public class ConsoleLogger extends AbstractLogger {


    ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("ConsoleLogger.write:" + message);
    }
}
