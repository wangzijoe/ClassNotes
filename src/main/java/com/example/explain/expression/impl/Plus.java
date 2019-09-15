package com.example.explain.expression.impl;

import com.example.explain.expression.Expression;

/**
 * 加号操作符类
 */
public class Plus implements Expression {

    private Expression left;

    private Expression right;

    public Plus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public float interpret() {
        return left.interpret() + right.interpret();
    }
}
