package com.example.functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionInterfaceTestMain {

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    /**
     * 1、lambda表达式
     * 这种形式最为直观，lambda表达式，接收一个String类型的参数，返回一个String类型的结果。
     * 完全符合函数式接口FunctionInterfaceTest的定义
     */
    FunctionInterfaceTest01 functionInterfaceTest01 = item -> item + 1;
    /**
     * 2、方法引用
     * FunctionInterfaceTestMain方法当中的getInstance和getMessage方法接收一个参数，返回一个结果。符合函数式接口
     * FunctionInterfaceTest的定义。
     * 函数式接口只是定义了个方法的约定（接收一个String类型的参数，返回一个String类型的结果），
     * 而对于方法内部进行何种操作则并没有做任何的限制。在这点上，跟java以前的版本中的实现类与接口之间的
     * 关系很类似。不同的是，函数式接口更偏重于计算过程，约束了一个计算过程的输入和输出。
     * 这种约束计算过程的输入和输出的形式的好处可以看一下joinStr方法。
     */
    FunctionInterfaceTest01 FunctionInterfaceTest001 = FunctionInterfaceTestMain::getInstance;// 方法引用
    FunctionInterfaceTest01 FunctionInterfaceTest002 = FunctionInterfaceTestMain::getMessage;// 方法引用

    String msg1 = joinStr("你好", FunctionInterfaceTest001);// 输出：你好！世界
    String msg2 = joinStr("你好", FunctionInterfaceTest002); // 输出：世界，你好！
    System.err.println(msg1);
    System.err.println(msg2);

    // 还有更简单的写法,高度抽象化，具体处理由使用者自己决定
    String msg3 = joinStr("你好", item -> "高度抽象化 " + item + "！世界"); // 输出：你好！世界
    String msg4 = joinStr("你好", item -> "高度抽象化 " + "世界," + item + "!"); // 输出：世界，你好！
    System.out.println(msg3);
    System.out.println(msg4);

    /**
     * 3、构造方法引用
     * 构造函数的结构：接收输入参数，然后返回一个对象。这种约束跟函数式接口的约束很像。
     * 所以只要“输入参数类型”与“输出参数类型”跟FunctionInterfaceTest中的方法约束相同，
     * 就可以创建出FunctionInterfaceTest接口的实例，如下，String的构造方法中有
     * new String(str)的构造方法，所以可以得到实例。
     * 这里存在一个类型推断的问题，JDK的编译器已经帮我们自动找到了只有一个参数，且是String类型的构造方法。
     * 这就是我们直接String::new，没有指定使用哪一个构造方法，却可以创建实例的原因
     */
    FunctionInterfaceTest01 functionInterfaceTest003 = String::new; // 方法引用

    /*
     * 接口名                         说明
     * Function<T,R>           接收一个T类型的参数，返回一个R类型的结果
     * Consumer<T>           接收一个T类型的参数，不返回值
     * Predicate<T>             接收一个T类型的参数，返回一个boolean类型的结果
     * Supplier<T>              不接受参数，返回一个T类型的结果
     */
    /**
     * 先看看如何创建它们
     */
    Function<String, String> function1 = item -> item + "返回值";

    Consumer<String> function2 = iterm -> {
      System.out.println(iterm);
    };// lambda语句，使用大括号，没有return关键字，表示没有返回值

    Predicate<String> function3 = iterm -> "".equals(iterm);

    Supplier<String> function4 = () -> new String("");
    /**
     * 再看看怎么使用
     * demo释义：
     * 1、创建一个String类型的集合
     * 2、将集合中的所有元素的末尾追加字符串'1'
     * 3、选出长度大于2的字符串
     * 4、遍历输出所有元素
     */
    List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "xiaoming", "zhaoliu");

    list.stream().map(value -> value + "1") // 传入的是一个Function函数式接口
        .filter(value -> value.length() > 5) // 传入的是一个Predicate函数式接口
        .forEach(value -> System.out.println(value)); // 传入的是一个Consumer函数式接口

    /*
     * 接口名                               说明
     * BiFunction<T, U, R>        接收T类型和U类型的两个参数，返回一个R类型的结果
     * BiConsumer<T , U>        接收T类型和U类型的两个参数，不返回值
     * BiPredicate<T, U>           接收T类型和U类型的两个参数，返回一个boolean类型的结果
     */

    /**
     * Bi类型的接口创建
     */
    BiFunction<String, String, Integer> biFunction = (str1, str2) -> str1.length() + str2.length();

    BiConsumer<String, String> biConsumer = (str1, str2) -> System.out.println(str1 + str2);

    BiPredicate<String, String> biPredicate = (str1, str2) -> str1.length() > str2.length();
    /**
     * Bi类型的接口使用
     */
    int length = getLength("hello", "world", (str1, str2) -> str1.length() + str2.length()); // 输出10
    boolean boolean1 = getBoolean("hello", "world", (str1, str2) -> str1.length() > str2.length()); // 输出false

    System.out.println(length);
    System.out.println(boolean1);

    noResult("hello", "world", (str1, str2) -> System.out.println(str1 + " " + str2)); // 没有输出
  }

  public static String getInstance(String item) {
    return item + "！世界";
  }

  public static String getMessage(String massage) {
    return "世界," + massage + "!";
  }

  public static String joinStr(String str, FunctionInterfaceTest01 functionTest) {
    return functionTest.getInfo(str);
  }

  public static int getLength(String str1, String str2, BiFunction<String, String, Integer> function) {
    return function.apply(str1, str2);
  }

  public static void noResult(String str1, String str2, BiConsumer<String, String> biConcumer) {
    biConcumer.accept(str1, str2);
  }

  public static boolean getBoolean(String str1, String str2, BiPredicate<String, String> biPredicate) {
    return biPredicate.test(str1, str2);
  }

}
