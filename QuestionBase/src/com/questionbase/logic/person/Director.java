package com.questionbase.logic.person;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.TestPaperInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.TestPaper;

public class Director extends Teacher {

	private static final long serialVersionUID = 1L;

	public Director(String code) {
		super(code);
	}

	@SuppressWarnings("unchecked")
	public List<Question> getAllApllyQuestion() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();

		Query query = session
				.createQuery("select o.teacherCode from TeacherInfo o where schoolCode = ?");
		query.setParameter(0, this.schoolCode);
		List<String> ts = query.list();
		ArrayList<Question> result = new ArrayList<Question>();
		if (ts.size() > 0) {
			Query query2 = session.createQuery("select o from QuestionBank "
					+ "o where status = ? and teacherCode in (:alist)");

			query2.setParameter(0, QuestionStatus.Submited);
			query2.setParameterList("alist", ts);

			for (Object q : query2.list()) {
				result.add(QuestionManager.GetInstance()
						.CreateQuestionByQuestionBank((QuestionBank) q));
			}

		}

		return result;
	}

	public void agreeApllyQuestion(List<String> apllyquestionlist,
			QuestionStatus status) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("update QuestionBank o set o.status=? where code in (:alist)");

		query.setParameter(0, status);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (String q : apllyquestionlist) {
			list.add(Integer.parseInt(q));
		}

		query.setParameterList("alist", list);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<TestPaper> getAllApllyTestPaper() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();

		Query query = session
				.createQuery("select o.teacherCode from TeacherInfo o where schoolCode = ?");
		query.setParameter(0, this.schoolCode);
		List<String> ts = query.list();
		ArrayList<TestPaper> result = new ArrayList<TestPaper>();
		if (ts.size() > 0) {
			Query query2 = session.createQuery("select o from TestPaperInfo "
					+ "o where status = ? and teacherCode in (:alist)");

			query2.setParameter(0, QuestionStatus.Submited);
			query2.setParameterList("alist", ts);

			for (Object q : query2.list()) {
				result.add(QuestionManager.GetInstance()
						.CreateTestPaperByTestPaperInfo((TestPaperInfo) q));
			}

		}

		return result;
	}

	public void agreeApllyTestPaper(List<String> apllyPaperlist,
			QuestionStatus status) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("update TestPaperInfo o set o.status=? where code in (:alist)");

		query.setParameter(0, status);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (String q : apllyPaperlist) {
			list.add(Integer.parseInt(q));
		}

		query.setParameterList("alist", list);
		query.executeUpdate();
		session.getTransaction().commit();
	}
}
