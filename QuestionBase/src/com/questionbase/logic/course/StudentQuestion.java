package com.questionbase.logic.course;

import java.util.ArrayList;
import java.util.List;

import com.questionbase.logic.person.Student;
import com.questionbase.question.Question;

public class StudentQuestion {

	private Student student;
	private List<Question> questions;

	public StudentQuestion(Student student, Question q) {
		this.student = student;
		questions = new ArrayList<Question>();
		if(q!=null)
			questions.add(q);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StudentQuestion)) {
			return false;
		}
		StudentQuestion cObj = (StudentQuestion) obj;
		if (cObj.getStudent().getCode().equals(student.getCode())) {
			return true;
		}

		return false;
	}

}
