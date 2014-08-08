package com.questionbase.question;

import java.util.List;

public class SelectQuestionResult {
	private List<Question> questions;
	private int pageCount;

	public SelectQuestionResult(List<Question> questions, int pageCount) {
		this.questions = questions;
		this.pageCount = pageCount;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
