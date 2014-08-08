package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.questionbase.question.QuestionStatus;

@Entity
@Table(name = "TestPaperInfo")
public class TestPaperInfo {

	@Column(name = "PaperCode", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int code;

	@Column(name = "Name")
	private String name;

	@Column(name = "Owner")
	private String teacherCode;

	@Column(name = "CourseCode")
	private String courseCode;

	@Column(name = "TeachingMaterialVersion")
	private String teachingMaterialVersion;

	@Column(name = "Year")
	private String year;

	@Column(name = "Organization")
	private String organization;

	@Column(name = "Types")
	private String types;

	@Column(name = "Status")
	@Enumerated(value = EnumType.ORDINAL)
	private QuestionStatus status;

	@Column(name = "SubmitComment")
	private String submitComment;

	@Column(name = "ReversalTeacherCode")
	private String reversalTeacherCode;

	@Column(name = "ReversalComment")
	private String reversalComment;

	@Column(name = "WordPaperId")
	private String wordPaperId;

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

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getWordPaperId() {
		return wordPaperId;
	}

	public void setWordPaperId(String wordPaperId) {
		this.wordPaperId = wordPaperId;
	}

}
