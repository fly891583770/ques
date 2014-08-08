package com.questionbase.hbm;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Stream")
public class Stream {

	@Column(name = "Code", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int code;

	@Column(name = "QuestionTitle")
	private Blob questionTitle;

	@Column(name = "QuestionAnswer")
	private Blob questionAnswer;

	@Column(name = "Summary")
	private String summary;
	
	@Column(name = "QuestionTitleURL")
	 private String questionTitleURL;

	@Column(name = "QuestionAnswerURL")
	 private String questionAnswerURL;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Blob getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(Blob questionTitle) {
		this.questionTitle = questionTitle;
	}

	public Blob getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(Blob questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	

	public String getQuestionTitleURL() {
		return questionTitleURL;
	}

	public void setQuestionTitleURL(String questionTitleURL) {
		this.questionTitleURL = questionTitleURL;
	}
 
	public String getQuestionAnswerURL() {
		return questionAnswerURL;
	}

	public void setQuestionAnswerURL(String questionAnswerURL) {
		this.questionAnswerURL = questionAnswerURL;
	}


}
