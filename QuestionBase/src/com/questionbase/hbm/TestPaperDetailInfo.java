package com.questionbase.hbm;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.questionbase.hbm.keys.TestPaperDetailInfoPK;

@Entity
@Table(name = "TestPaperDetailInfo")
@IdClass(TestPaperDetailInfoPK.class)
public class TestPaperDetailInfo {

	@Column(name = "PaperCode")
	@Id
	private int paperCode;

	@Column(name = "QuestionCode")
	@Id
	private int questionCode;

	@Column(name = "Score")
	private int score;

	@Column(name = "Sequence")
	private int sequence;

	@Column(name = "UpdateTime")
	private Date updateTime;

	public int getPaperCode() {
		return paperCode;
	}

	public void setPaperCode(int paperCode) {
		this.paperCode = paperCode;
	}

	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
