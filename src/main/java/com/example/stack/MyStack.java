package com.example.stack;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.EmptyStackException;

@Slf4j
class MyStack<T> {

    private T[] elements;

    private int size;

    private static final int INIT_CAPACITY = 16;

    private MyStack() {
        //noinspection unchecked
        elements = (T[]) new Object[INIT_CAPACITY];
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    private void push(T elem) {
        ensureCapacity();
        elements[size++] = elem;
    }

    private T pop() {
        if (size == 0) throw new EmptyStackException();
        return elements[--size];
    }

    /*
        该栈功能（先进后出FILO），咋一看，没有什么问题，甚至可以通过各种单元测试，然而
    其pop()方法却存在着内存泄漏的问题，当我们使用pop方法弹出栈中的对象的时候，该对象不会被
    当作垃圾回收。即使使用栈的程序不再引用这些对象。因为栈的内部维护着对这些对象的过期引用(obsolete reference)
        在支持垃圾回收的语言中，内存泄漏是很隐蔽的，这种内存泄漏其实就是无意识的对象保持，如果一个对象引用，被
    无意识的保留起来，那么垃圾回收器不会处理这个对象，也不会处理该对象引用的其他对象，即使这样的对象只有少数几个，
    也可能会导致很多的对象被排除在垃圾回收器之外，从而对性能照成影响，极端情况下引发Disk Paging（物理内存与硬盘
    虚拟内存交换数据），甚至造成OOM异常。
     */
    public static void main(String[] args) {
        MyStack<Object> myStack = new MyStack<>();
        myStack.push("elemA");
        myStack.push("elemB");
        Object o1 = myStack.pop();
        Object o2 = myStack.pop();
        o1 = null;
        o2 = null;
        log.info("o1 = {}, o2 = {}", o1, o2);
        System.gc();
        log.info(myStack.elements[0].toString());
    }
}