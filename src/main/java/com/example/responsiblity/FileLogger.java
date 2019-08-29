package com.example.responsiblity;

public class FileLogger extends AbstractLogger {


    FileLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("FileLogger.write:" + message);
    }
}
