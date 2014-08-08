package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "KnowledgePoint")
public class KnowledgePoint {

	@Column(name = "KnowledgePointCode", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int code;

	@Column(name = "SchoolCode")
	private String schoolCode;

	@Column(name = "TeachingMaterialVersion")
	private String teachingMaterialVersion;

	@Column(name = "CourseCode")
	private String courseCode;

	@Column(name = "Key")
	private String key;

	@Column(name = "Year")
	private String year;

	@Column(name = "Caption")
	private String Caption;

	@Column(name = "ParentKey")
	private String parentKey;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getTeachingMaterialVersion() {
		return teachingMaterialVersion;
	}

	public void setTeachingMaterialVersion(String teachingMaterialVersion) {
		this.teachingMaterialVersion = teachingMaterialVersion;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getCaption() {
		return Caption;
	}

	public void setCaption(String caption) {
		Caption = caption;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
