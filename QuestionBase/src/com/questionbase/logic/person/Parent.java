package com.questionbase.logic.person;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.questionbase.exception.QuestionBaseException;
import com.questionbase.hbm.FamilyInfo;
import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.StudentQuestionStatus;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.Person;
import com.questionbase.logic.PersonUtil;
import com.questionbase.logic.course.StudentQuestion;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;

public class Parent extends Person {

	private static final long serialVersionUID = 1L;
	private String familyCode;
	private String sid;
	private String name;
	private Person child;

	public Parent(String code) throws QuestionBaseException {
		this.familyCode = code;
		InitParentByParentCode(code);
	}

	@SuppressWarnings("unchecked")
	private void InitParentByParentCode(String code)
			throws QuestionBaseException {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from FamilyInfo o where familyCode = ?");
		query.setString(0, code);

		List<FamilyInfo> parent = query.list();
		for (FamilyInfo f : parent) {
			String sCode = f.getChildCode();
			child = PersonUtil.getInstance().getPerson(sCode, session, null,
					null);
			this.sid = f.getSid();
			this.name = f.getName();
			break;
		}
	}

	public Hashtable<Student, List<Question>> getChildApplyQuestions() {

		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		String c_Code = getChildCode(session);
		List<StudentQuestion> qCodes = getChildApplyQuestionCodes(session,
				c_Code);

		Hashtable<Student, List<Question>> result = new Hashtable<Student, List<Question>>();
		for (StudentQuestion sq : qCodes) {
			if (result.containsKey(sq.getStudent())) {
				result.get(sq.getStudent()).addAll(sq.getQuestions());
			} else {
				result.put(sq.getStudent(), sq.getQuestions());
				;
			}

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<StudentQuestion> getChildApplyQuestionCodes(Session session,
			String c_Code) {

		Query query = session.createQuery("select q.studentCode,question "
				+ "from StudentAndQuestion as q , QuestionBank as question"
				+ " where q.guard = ? and q.studentCode = ? "
				+ " and q.questionCode = question.code order by q.studentCode");
		query.setParameter(0, StudentQuestionStatus.ApplyAndNotApprove);
		query.setParameter(1, c_Code);
		List<Object[]> datas = query.list();
		List<StudentQuestion> result = new ArrayList<StudentQuestion>();
		StudentQuestion sq = new StudentQuestion(new Student(c_Code), null);
		result.add(sq);
		for (Object[] data : datas) {
			if (data.length >= 2) {
				Question q = QuestionManager.GetInstance()
						.CreateQuestionByQuestionBank((QuestionBank) data[1]);
				result.get(0).getQuestions().add(q);
			}
		}

		return result;
	}

	private String getChildCode(Session session) {
		Student s = (Student) child;
		return s.getCode();
	}

	public void ApprovedQuestion(String qCode) {
		if (!child.getClass().equals(Student.class)) {
			return;
		}

		Student s = (Student) child;
		QuestionManager.GetInstance().ApprovedQuestion(s.getCode(), qCode);
	}

	public String getFamilyCode() {
		return familyCode;
	}

	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getChild() {
		return child;
	}

	public void setChild(Person child) {
		this.child = child;
	}

}
