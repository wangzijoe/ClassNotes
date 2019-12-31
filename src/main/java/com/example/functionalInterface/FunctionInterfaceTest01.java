package com.example.functionalInterface;

/*
      在java8中，满足下面任意一个条件的接口都是函数式接口：
          1、被@FunctionalInterface注释的接口，满足@FunctionalInterface注释的约束。
          2、没有被@FunctionalInterface注释的接口，但是满足@FunctionalInterface注释的约束
     @FunctionalInterface注释的约束：
          1、接口有且只能有个一个抽象方法，只有方法定义，没有方法体
          2、在接口中覆写Object类中的public方法，不算是函数式接口的方法。
 */

//接口一
@FunctionalInterface
public interface FunctionInterfaceTest01 {

  String getInfo(String input);

  @Override
  String toString(); // Object中的方法

  @Override
  boolean equals(Object obj); // Object中的方法

}
