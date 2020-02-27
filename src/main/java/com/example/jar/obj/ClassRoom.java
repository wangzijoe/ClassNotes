package com.example.jar.obj;

import java.util.List;

import lombok.Data;

@Data
public class ClassRoom {
	
	private List<Student> students;

	public ClassRoom(List<Student> students) {
		super();
		this.students = students;
	}

}
