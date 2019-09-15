package com.example.explain.expression.impl;

import com.example.explain.expression.Expression;

/**
 * 减号操作符类
 */
public class Minus implements Expression {

    private Expression left;

    private Expression right;

    public Minus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public float interpret() {
        return right.interpret() - left.interpret();
    }
}
