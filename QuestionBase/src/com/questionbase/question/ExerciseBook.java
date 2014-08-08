package com.questionbase.question;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.questionbase.hbm.ExerciseBookBank;
import com.questionbase.hbm.ExerciseBookInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;

public class ExerciseBook {
	private int code;

	private String name;

	private String studentCode;

	private List<Question> questions;

	public List<Question> getQuestions() throws CloneNotSupportedException {
		InitQuesiont();

		ArrayList<Question> copyQuestions = new ArrayList<Question>();
		for (Question q : questions) {
			copyQuestions.add((Question) q.clone());
		}

		return copyQuestions;
	}

	@SuppressWarnings("unchecked")
	private void InitQuesiont() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o.questionCode from ExerciseBookInfo o where o.exerciseBookCode = ?");
		query.setParameter(0, this.code);
		List<Integer> es = query.list();
		questions = QuestionManager.GetInstance().CreateQuestionByQuestionCode(
				es, session);

	}

	public ExerciseBook(String name, String studentCode) {
		this.name = name;
		this.studentCode = studentCode;
		questions = new ArrayList<Question>();
	}

	public ExerciseBook(String id) {
		this.code = Integer.parseInt(id);
	}

	public void ToDoAddAndNoSaveQuestions(List<Question> qs) {
		synchronized (this.questions) {
			for (Question q : qs) {
				if (questions.contains(q)) {
					questions.remove(q);
				}

				questions.add(q);
			}
		}
	}

	private void ToDoDeletAndNoSaveQuestions(ArrayList<Integer> qs) {
		synchronized (this.questions) {
			for (int qCode : qs) {
				for (Question q : questions) {
					if (q.getCode() == qCode) {
						questions.remove(q);
						break;
					}
				}
			}
		}

	}

	public String getName() {
		if (name == null) {
			Session session = QuestionBaseFactory.GetInstance()
					.getSessionFactory().openSession();
			Query query = session
					.createQuery("select o.name from ExerciseBookBank o where o.code = ?");
			query.setParameter(0, this.code);
			name = query.uniqueResult().toString();
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void AddQuestions(ArrayList<Integer> qs) {
		ArrayList<Integer> questionCodes = new ArrayList<Integer>();
		ArrayList<Integer> questionSaveCodes = new ArrayList<Integer>();

		for (Question q : questions) {
			questionCodes.add(q.getCode());
		}

		for (int qCode : qs) {
			if (!questionCodes.contains(qCode)) {
				questionSaveCodes.add(qCode);
			}
		}

		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();

		session.beginTransaction();

		for (int qCode : questionSaveCodes) {
			ExerciseBookInfo info = new ExerciseBookInfo();
			info.setExerciseBookCode(code);
			info.setQuestionCode(qCode);
			session.save(info);
		}

		ToDoAddAndNoSaveQuestions(QuestionManager.GetInstance()
				.CreateQuestionByQuestionCode(questionSaveCodes, session));

		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void DeleteQuestions(ArrayList<Integer> qs) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(ExerciseBookInfo.class);
		criteria.add(Restrictions.eq("exerciseBookCode", code));
		Disjunction dis = Restrictions.disjunction();
		Property questionCode = Property.forName("questionCode");
		for (int qsCode : qs) {
			dis.add(questionCode.eq(qsCode));
		}
		criteria.add(dis);
		List<ExerciseBookInfo> infos = criteria.list();
		for (ExerciseBookInfo i : infos) {
			session.delete(i);
		}
		ToDoDeletAndNoSaveQuestions(qs);
		session.getTransaction().commit();
	}

}
