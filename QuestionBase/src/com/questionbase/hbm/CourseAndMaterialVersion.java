package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.questionbase.hbm.keys.CourseAndMaterialVersionPK;

@Entity
@Table(name = "CourseAndMaterialVersion")
@IdClass(CourseAndMaterialVersionPK.class)
public class CourseAndMaterialVersion {

	@Column(name = "CourseKCM")
	@Id
	private String courseCode;

	@Column(name = "TeachingMaterialVersionCode")
	@Id
	private int teachingMaterialVersionCode;
	
	@Column(name = "Organization")
	private String schoolCode;

	
	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getTeachingMaterialVersionCode() {
		return teachingMaterialVersionCode;
	}

	public void setTeachingMaterialVersionCode(int teachingMaterialVersionCode) {
		this.teachingMaterialVersionCode = teachingMaterialVersionCode;
	}
}
