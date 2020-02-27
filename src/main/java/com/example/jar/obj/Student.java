package com.example.jar.obj;

import lombok.Data;

@Data
public class Student {
	
	private String name;
	
	private int age;

	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	

}
