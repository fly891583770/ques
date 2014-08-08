package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.questionbase.hbm.keys.CourseAndQuestionTypePK;

@Entity
@Table(name = "CourseAndQuestionType")
@IdClass(CourseAndQuestionTypePK.class)
public class CourseAndQuestionType {

	@Column(name = "CourseKCM")
	@Id
	private String courseCode;

	@Column(name = "QuestionTypeCode")
	@Id
	private int questionTypeCode;

	@Column(name = "Organization")
	@Id
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

	public int getQuestionTypeCode() {
		return questionTypeCode;
	}

	public void setQuestionTypeCode(int questionTypeCode) {
		this.questionTypeCode = questionTypeCode;
	}

}
