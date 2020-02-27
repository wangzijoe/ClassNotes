package com.example.jar.obj;

import java.util.ArrayList;
import java.util.List;

public class CLassRoomStudentMain {

	public static void main(String[] args) {
		Student student1 = new Student("a",25);
		Student student2 = new Student("b",25);
		Student student3 = new Student("c",25);
		Student student4 = new Student("d",25);
		List<Student> students = new ArrayList<Student>();
		students.add(student1);
		students.add(student2);
		students.add(student3);
		students.add(student4);
		ClassRoom classRoom = new ClassRoom(students);
		
		List<Student> list = classRoom.getStudents();
		for (Student student : list) {
			student.setAge(28);
		}
		System.err.println(classRoom);
	}
}
