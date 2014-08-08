package com.questionbase.logic.person;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.questionbase.hbm.ExerciseBookBank;
import com.questionbase.hbm.ExerciseBookInfo;
import com.questionbase.hbm.StudentAndQuestion;
import com.questionbase.hbm.StudentInfo;
import com.questionbase.hbm.StudentQuestionStatus;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.Person;
import com.questionbase.question.ExerciseBook;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.S_Question;

public class Student extends Person {

	private static final long serialVersionUID = 1L;
	private String code;
	private String classCode;
	private String peopleCode;
	private String studyCode;
	private String name;
	private String schoolCode;

	public Student(String code) {
		this.code = code;
		InitStudentByStudentCode(code);
	}

	public Student(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public List<ExerciseBook> getExerciseBooks() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria c = session.createCriteria(ExerciseBookBank.class);
		c.add(Restrictions.eq("studentCode", this.code));
		List<ExerciseBookBank> es = c.list();
		ArrayList<ExerciseBook> result = new ArrayList<ExerciseBook>();
		for (ExerciseBookBank ebb : es) {
			ExerciseBook exerciseBook = new ExerciseBook(ebb.getName(),
					ebb.getStudentCode());
			exerciseBook.setCode(ebb.getCode());
			result.add(exerciseBook);
		}
		return result;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getPeopleCode() {
		return peopleCode;
	}

	public void setPeopleCode(String peopleCode) {
		this.peopleCode = peopleCode;
	}

	public String getStudyCode() {
		return studyCode;
	}

	public void setStudyCode(String studyCode) {
		this.studyCode = studyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getCode() {
		return code;
	}

	public void ChooseQuestion(List<Integer> questions) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		ChooseQuestion(questions, session);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void ChooseQuestion(List<Integer> questions, Session session) {
		Query query = session
				.createQuery("select questionCode from StudentAndQuestion where studentCode = ?");
		query.setString(0, code);
		List<Integer> stuQuestions = query.list();
		for (int questionCode : questions) {

			if (!stuQuestions.contains(questionCode)) {
				StudentAndQuestion saq = new StudentAndQuestion();
				saq.setGuard(StudentQuestionStatus.NotApplyForAnswer);
				saq.setQuestionCode(questionCode);
				saq.setAnswer("No Answer");
				saq.setStudentCode(this.code);
				session.save(saq);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<S_Question> getQuestions(StudentQuestionStatus status) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from StudentAndQuestion o where o.studentCode=? and guard=?");
		query.setParameter(0, code);
		query.setParameter(1, status);
		List<StudentAndQuestion> stuQuestions = query.list();
		return QuestionManager.GetInstance().CreateS_QuestionByQuestionCode(
				stuQuestions, session);
	}

	@SuppressWarnings("unchecked")
	public List<S_Question> getQuestions() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from StudentAndQuestion o where o.studentCode=?");
		query.setString(0, code);
		List<StudentAndQuestion> stuQuestions = query.list();
		return QuestionManager.GetInstance().CreateS_QuestionByQuestionCode(
				stuQuestions, session);
	}

	@SuppressWarnings("unchecked")
	public S_Question getQuestion(int c) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from StudentAndQuestion o where o.studentCode=? and questionCode=?");
		query.setString(0, code);
		query.setInteger(1, c);
		List<StudentAndQuestion> stuQuestions = query.list();
		if (stuQuestions.size() == 0) {
			return QuestionManager.GetInstance()
					.CreateS_QuestionByQuestionCode(c, session);
		} else {
			List<S_Question> result = QuestionManager.GetInstance()
					.CreateS_QuestionByQuestionCode(stuQuestions, session);
			return result.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	private void InitStudentByStudentCode(String selectCode) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();

		List<StudentInfo> stu = session.createCriteria(StudentInfo.class)
				.add(Restrictions.eq("studentCode", selectCode)).list();

		if (stu.size() > 0) {
			for (StudentInfo info : stu) {
				classCode = info.getClassCode();
				peopleCode = info.getPeopleCode();
				studyCode = info.getStudyCode();
				name = info.getName();
				schoolCode = info.getSchoolCode();
				break;
			}
		}

	}

	public void DeleteExerciseBook(int code) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("delete ExerciseBookBank o where o.code=?");
		query.setInteger(0, code);
		query.executeUpdate();
		Query query2 = session
				.createQuery("delete ExerciseBookInfo o where o.exerciseBookCode=?");
		query2.setInteger(0, code);

		query2.executeUpdate();

		session.getTransaction().commit();

	}

	public ExerciseBook CreateExerciseBook(String name, List<Integer> questions) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();

		session.beginTransaction();
		// 做习题册
		ExerciseBookBank exerciseBook = new ExerciseBookBank();
		exerciseBook.setGuard(false);
		exerciseBook.setName(name);
		exerciseBook.setStudentCode(code);
		session.save(exerciseBook);

		ExerciseBook result = new ExerciseBook(name, code);

		// 加题
		for (int qsCode : questions) {
			ExerciseBookInfo info = new ExerciseBookInfo();
			info.setExerciseBookCode(exerciseBook.getCode());
			info.setQuestionCode(qsCode);
			session.save(info);
		}

		ChooseQuestion(questions, session);

		result.ToDoAddAndNoSaveQuestions(QuestionManager.GetInstance()
				.CreateQuestionByQuestionCode(questions, session));

		result.setCode(exerciseBook.getCode());

		session.getTransaction().commit();
		return result;
	}

	@SuppressWarnings("unchecked")
	public void SetAnswer(int qCode, String answer) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select o from StudentAndQuestion o where studentCode = ? and questionCode = ?");
		query.setString(0, code);
		query.setInteger(1, qCode);
		List<StudentAndQuestion> question = query.list();
		if (question.size() > 0) {
			question.get(0).setAnswer(answer);
			session.save(question.get(0));
		}

		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void ApplyAnswer(int qCode) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select o from StudentAndQuestion o where studentCode = ? and questionCode = ?");
		query.setString(0, code);
		query.setInteger(1, qCode);
		List<StudentAndQuestion> question = query.list();
		if (question.size() > 0) {
			question.get(0).setGuard(StudentQuestionStatus.ApplyAndNotApprove);
			session.save(question.get(0));
		} else {
			List<Integer> questions = new ArrayList<Integer>();
			questions.add(qCode);
			this.ChooseQuestion(questions);
			this.ApplyAnswer(qCode);
		}

		session.getTransaction().commit();

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Student)) {
			return false;
		}

		Student tempStu = (Student) obj;
		if (tempStu.getCode().equals(code)) {
			return true;
		}

		return false;
	}

}
