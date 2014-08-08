package com.questionbase.question;

import com.questionbase.hbm.StudentQuestionStatus;

public class S_Question extends Question {

	private String studentAnswer;

	private StudentQuestionStatus guard;

	public S_Question(int code) {
		this.code = code;
	}

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public StudentQuestionStatus getGuard() {
		return guard;
	}

	public void setGuard(StudentQuestionStatus guard) {
		this.guard = guard;
	}

}