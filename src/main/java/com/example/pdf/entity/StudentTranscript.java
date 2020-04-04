package com.example.pdf.entity;

import lombok.Data;

/**
 * 学生成绩表对象
 * @author 17923
 *
 */
@Data
public class StudentTranscript {
	
	/** 学生姓名 **/
	private String studentName;

	/** 科目 **/
	private String subject;
	
	/** 分数 **/
	private Integer mark;
	
}
