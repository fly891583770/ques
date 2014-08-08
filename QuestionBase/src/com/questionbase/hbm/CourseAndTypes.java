package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.questionbase.hbm.keys.CourseAndTypesPK;

@Entity
@Table(name = "CourseAndTypes")
@IdClass(CourseAndTypesPK.class)
public class CourseAndTypes {

	@Column(name = "CourseKCM")
	@Id
	private String courseCode;

	@Column(name = "TypesInfoCode")
	@Id
	private int typesCode;

	@Column(name = "Organization")
	@Id
	private String schoolCode;

	public String getSchoolCode() {
		return schoolCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getTypesCode() {
		return typesCode;
	}

	public void setTypesCode(int typesCode) {
		this.typesCode = typesCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
}
