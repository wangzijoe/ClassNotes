package com.example.jar;

public class TestInteger {

  public static void main(String[] args) {
    System.err.println(new Integer(0) == 0);
    System.err.println(Integer.valueOf(0) == 0);
    System.err.println(Integer.valueOf(0) == Integer.valueOf(0));
    System.err.println(new Integer(0) == new Integer(0));
    System.err.println(Integer.valueOf(0) == new Integer(0));

  }
}
