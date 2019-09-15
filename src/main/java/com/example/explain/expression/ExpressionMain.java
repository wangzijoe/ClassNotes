package com.example.explain.expression;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpressionMain {

    public static void main(String[] args) {
        Evaluator eval = new Evaluator();
        System.out.println(eval.evaluate("23+"));
        System.out.println(eval.evaluate("43-"));
        System.out.println(eval.evaluate("43-2+"));
    }
}
