package com.questionbase.logic.course;

import java.io.Serializable;

public class ClassAndCourse implements Serializable  {

	private String classCode;
	private String courseCode;
	public ClassAndCourse(String classCode,String courseCode){
		this.classCode = classCode;
		this.courseCode = courseCode;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	
}
