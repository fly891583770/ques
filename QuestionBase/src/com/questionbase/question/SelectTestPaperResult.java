package com.questionbase.question;

import java.util.List;

public class SelectTestPaperResult {
	private List<TestPaper> papers;
	private int pageCount;

	public SelectTestPaperResult(List<TestPaper> papers, int pageCount) {
		this.papers = papers;
		this.pageCount = pageCount;
	}

	public List<TestPaper> getTestPapers() {
		return papers;
	}

	public void setQuestions(List<TestPaper> papers) {
		this.papers = papers;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
