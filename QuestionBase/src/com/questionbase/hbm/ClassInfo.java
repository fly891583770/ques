package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZX_XX_BJ")
public class ClassInfo {

	@Column(name = "BJDM")
	@Id
	private String code;

	@Column(name = "BZRJGDM")
	private String teacherCode;

	@Column(name = "BJ")
	private String name;

	@Column(name = "XN")
	private String year;

	@Column(name = "XXDM")
	private String schoolCode;

	@Column(name = "XD")
	private String level;

	@Column(name = "NJ")
	private String cYear;

	public String getcYear() {
		return cYear;
	}

	public void setcYear(String cYear) {
		this.cYear = cYear;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBZRJGDM() {
		return teacherCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

}
