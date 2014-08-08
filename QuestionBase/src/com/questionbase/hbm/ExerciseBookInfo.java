package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ExerciseBookInfo")
public class ExerciseBookInfo {

	@Column(name = "Sid", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int sid;

	@Column(name = "ExerciseBookCode")
	private int exerciseBookCode;

	@Column(name = "QuestionCode")
	private int questionCode;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getExerciseBookCode() {
		return exerciseBookCode;
	}

	public void setExerciseBookCode(int exerciseBookCode) {
		this.exerciseBookCode = exerciseBookCode;
	}

	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

}
