package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZX_JX_KC")
public class CourseInfo {
	
	@Column(name = "KCBH")
	@Id
	private String code;
	
	@Column(name = "KCMC")
	private String name;
	
	@Column(name = "KCM")
	private String courseCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}	
}
