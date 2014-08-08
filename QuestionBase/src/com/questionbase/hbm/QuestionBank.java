package com.questionbase.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.questionbase.question.QuestionStatus;

@Entity
@Table(name = "QuestionBank")
public class QuestionBank {

	@Column(name = "QuestionCode", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int code;

	@Column(name = "Name")
	private String name;

	@Column(name = "Owner")
	private String teacherCode;

	@Column(name = "Answer")
	private String answer;

	@Column(name = "CourseCode")
	private String courseCode;

	@Column(name = "TeachingMaterialVersion")
	private String teachingMaterialVersion;

	@Column(name = "Year")
	private String year;

	@Column(name = "Difficulty")
	private String difficulty;

	@Column(name = "QuestionTypes")
	private String questionTypes;

	@Column(name = "Types")
	private String types;

	@Column(name = "Organization")
	private String organization;

	@Column(name = "Status")
	@Enumerated(value = EnumType.ORDINAL)
	private QuestionStatus status;

	@Column(name = "SubmitComment")
	private String submitComment;

	@Column(name = "ReversalTeacherCode")
	private String reversalTeacherCode;

	@Column(name = "ReversalComment")
	private String reversalComment;

	@Column(name = "Count", nullable = false, columnDefinition = "INT default 0")
	private int count;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdateTime")
	private Date updateTime;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(String questionTypes) {
		this.questionTypes = questionTypes;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public QuestionStatus getStatus() {
		return status;
	}

	public void setStatus(QuestionStatus status) {
		this.status = status;
	}

	public String getSubmitComment() {
		return submitComment;
	}

	public void setSubmitComment(String submitComment) {
		this.submitComment = submitComment;
	}

	public String getReversalTeacherCode() {
		return reversalTeacherCode;
	}

	public void setReversalTeacherCode(String reversalTeacherCode) {
		this.reversalTeacherCode = reversalTeacherCode;
	}

	public String getReversalComment() {
		return reversalComment;
	}

	public void setReversalComment(String reversalComment) {
		this.reversalComment = reversalComment;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
