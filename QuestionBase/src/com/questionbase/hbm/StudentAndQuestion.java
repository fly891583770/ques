package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "StudentAndQuestion")
public class StudentAndQuestion {

	@Column(name = "Sid", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int Sid;

	@Column(name = "StudentCode")
	private String studentCode;

	@Column(name = "QuestionCode")
	private int questionCode;

	@Column(name = "Answer")
	private String answer;

	@Column(name = "Permission")
	@Enumerated(value = EnumType.ORDINAL)
	private StudentQuestionStatus guard;

	public int getSid() {
		return Sid;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public StudentQuestionStatus getGuard() {
		return guard;
	}

	public void setGuard(StudentQuestionStatus guard) {
		this.guard = guard;
	}

	public void setSid(int sid) {
		Sid = sid;
	}

}
