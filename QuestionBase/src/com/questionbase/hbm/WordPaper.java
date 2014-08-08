package com.questionbase.hbm;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WordPaper")
public class WordPaper {

	@Column(name = "Code", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int code;

	@Column(name = "questionPaper")
	private Blob questionPaper;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Blob getQuestionPaper() {
		return questionPaper;
	}

	public void setQuestionPaper(Blob questionPaper) {
		this.questionPaper = questionPaper;
	}




}
