package com.example.explain.expression;

import com.example.explain.expression.impl.Minus;
import com.example.explain.expression.impl.Number;
import com.example.explain.expression.impl.Plus;

import java.util.Stack;

/**
 * 逆波兰表达式计算器
 */
class Evaluator {

    /**
     * 求值
     *
     * @param expression 逆波兰表达式
     * @return 计算结果
     */
    float evaluate(String expression) {
        if (expression.contains(" ")) throw new IllegalArgumentException();
        Stack<Expression> stack = new Stack<>();
        float result = 0;
        for (String token : expression.split("")) {
            if (isOperator(token)) {
                Expression exp = null;
                if ("+".equals(token))
                    exp = stack.push(new Plus(stack.pop(), stack.pop()));
                else if ("-".equals(token))
                    exp = stack.push(new Minus(stack.pop(), stack.pop()));
                if (null != exp) {
                    result = exp.interpret();
                    stack.push(new Number(result));
                }
            }
            if (isNumber(token))
                stack.push(new Number(Float.parseFloat(token)));
        }
        return result;
    }

    private boolean isNumber(String token) {
        try {
            Float.parseFloat(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return "+".equals(token) || "-".equals(token);
    }
}
