package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SchoolDetail")
public class SchoolDetail {

	@Column(name = "SchoolCode")
	@Id
	private String code;

	@Column(name = "CourseInfo")
	private String courseCode;

	@Column(name = "TeachingMaterialVersion")
	private String teachingMaterialVersion;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getTeachingMaterialVersion() {
		return teachingMaterialVersion;
	}

	public void setTeachingMaterialVersion(String teachingMaterialVersion) {
		this.teachingMaterialVersion = teachingMaterialVersion;
	}

}
