package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SectionAndQuestion")
public class SectionAndQuestion {

	@Column(name = "sid", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int sid;

	@Column(name = "QuestionCode")
	private int questionCode;

	@Column(name = "SectionCode")
	private String sectionPointCode;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

	public String getSectionPointCode() {
		return sectionPointCode;
	}

	public void setSectionPointCode(String sectionPointCode) {
		this.sectionPointCode = sectionPointCode;
	}

}
