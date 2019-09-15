package com.example.explain.expression.impl;

import com.example.explain.expression.Expression;

/**
 * （数字）类，他解释所有数字
 */
public class Number implements Expression {

    private float number;

    public Number(float number) {
        this.number = number;
    }

    @Override
    public float interpret() {
        return number;
    }
}
