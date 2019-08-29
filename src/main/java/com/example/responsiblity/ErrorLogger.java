package com.example.responsiblity;

public class ErrorLogger extends AbstractLogger {


    ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("ErrorLogger.write:" + message);
    }
}
