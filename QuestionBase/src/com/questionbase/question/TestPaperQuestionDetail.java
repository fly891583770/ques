package com.questionbase.question;

public class TestPaperQuestionDetail {
	private int questionCode;
	private int sequence;
	private Question question;
	private int score;

	public TestPaperQuestionDetail() {
	}

	public TestPaperQuestionDetail(int code) {
		this.questionCode = code;
	}

	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
