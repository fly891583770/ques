package com.questionbase.question;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.questionbase.hbm.TestPaperDetailInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;

public class TestPaperDetail {
	protected int paperCode;
	private ArrayList<TestPaperQuestionDetail> result;

	public TestPaperDetail() {
		
	}

	public TestPaperDetail(int code) {
		this.paperCode = code;
		init();
	}

	public int getPaperCode() {
		return paperCode;
	}

	public void setPaperCode(int paperCode) {
		this.paperCode = paperCode;
	}

	public ArrayList<TestPaperQuestionDetail> getResult() {
		return result;
	}

	public void setResult(ArrayList<TestPaperQuestionDetail> result) {
		this.result = result;
	}

	@SuppressWarnings("unchecked")
	private void init() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria c = session.createCriteria(TestPaperDetailInfo.class);
		c.add(Restrictions.eq("paperCode", paperCode));
		c.addOrder(Order.asc("sequence"));
		List<TestPaperDetailInfo> infos = c.list();

		result = new ArrayList<TestPaperQuestionDetail>();
		ArrayList<Integer> qs = new ArrayList<Integer>();
		for (TestPaperDetailInfo info : infos) {
			int q = info.getQuestionCode();
			TestPaperQuestionDetail t = new TestPaperQuestionDetail(q);
			t.setScore(info.getScore());
			t.setSequence(info.getSequence());
			result.add(t);
			qs.add(q);
		}

		List<Question> questions = QuestionManager.GetInstance()
				.CreateQuestionByQuestionCode(qs, session);

		for (TestPaperQuestionDetail t : result) {
			for (Question q : questions) {
				if (t.getQuestionCode() == q.getCode()) {
					t.setQuestion(q);
					break;
				}
			}
		}
	}

}
