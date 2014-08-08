package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ZX_JG_JBXX")
public class TeacherInfo {
	
	@Column(name = "JZGDM")
	@Id
	private String teacherCode;
	
	@Column(name = "RYBH")
	private String peopleCode;

	@Column(name = "XM")
	private String name;

	@Column(name = "ZYRJKCM")
	private String mainCourseCode;
	
	@Column(name = "JZRJKCM")
	private String subCourseCode;
	
	@Column(name = "XXDM")
	private String schoolCode;
	
	@Column(name = "FZNJ")
	private String grade;

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getPeopleCode() {
		return peopleCode;
	}

	public void setPeopleCode(String peopleCode) {
		this.peopleCode = peopleCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainCourseCode() {
		return mainCourseCode;
	}

	public void setMainCourseCode(String mainCourseCode) {
		this.mainCourseCode = mainCourseCode;
	}

	public String getSubCourseCode() {
		return subCourseCode;
	}

	public void setSubCourseCode(String subCourseCode) {
		this.subCourseCode = subCourseCode;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
