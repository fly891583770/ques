package com.questionbase.question;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.questionbase.hbm.KnowledgePoint;
import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.SectionPoint;
import com.questionbase.hbm.StudentAndQuestion;
import com.questionbase.hbm.StudentQuestionStatus;
import com.questionbase.hbm.TestPaperInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;

//这个需要单实例的
public class QuestionManager {

	private QuestionManager() {

	}

	private static volatile QuestionManager instance;

	private int PageNumber = 15;

	public static QuestionManager GetInstance() {
		if (instance == null) {
			synchronized (QuestionManager.class) {
				if (instance == null) {
					instance = new QuestionManager();
				}
			}
		}

		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<Question> GetAllQuestions() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		ArrayList<Question> result = new ArrayList<Question>();
		Query query = session.createQuery("select o from QuestionBank o");
		List<QuestionBank> list = query.list();
		for (QuestionBank o : list) {
			result.add(CreateQuestionByQuestionBank(o));
		}
		return result;
	}

	public Question GetQuestion(int id) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from QuestionBank o where o.code =?");
		query.setParameter(0, id);
		QuestionBank q = (QuestionBank) query.uniqueResult();
		if (q != null) {
			return CreateQuestionByQuestionBank(q);
		}

		return null;
	}

	public Question CreateQuestionByQuestionBank(QuestionBank o) {
		Question question = new Question(o.getCode());
		question.setAnswer(o.getAnswer());
		question.setName(o.getName());
		question.setTeacherCode(o.getTeacherCode());
		question.setCourseCode(o.getCourseCode());
		question.setDifficulty(o.getDifficulty());
		question.setOrganization(o.getOrganization());
		question.setTeachingMaterialVersion(o.getTeachingMaterialVersion());
		question.setYear(o.getYear());
		question.setTypes(o.getTypes());
		question.setQuestionTypes(o.getQuestionTypes());
		question.setReversalComment(o.getReversalComment());
		question.setReversalTeacherCode(o.getReversalTeacherCode());
		question.setStatus(o.getStatus());
		question.setSubmitComment(o.getSubmitComment());
		question.setCount(o.getCount());
		question.setUpdateTime(o.getUpdateTime());
		return question;
	}

	public List<Question> CreateQuestionByQuestionCode(List<Integer> questions) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		return CreateQuestionByQuestionCode(questions, session);
	}

	public List<Question> CreateQuestionByQuestionCode(List<Integer> questions,
			Session session) {
		Criteria criteria = session.createCriteria(QuestionBank.class);
		Disjunction dis = Restrictions.disjunction();
		Property code = Property.forName("code");
		for (int qsCode : questions) {
			dis.add(code.eq(qsCode));
		}
		criteria.add(dis);

		List<Question> result = new ArrayList<Question>();
		for (Object q : criteria.list()) {
			result.add(CreateQuestionByQuestionBank((QuestionBank) q));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public void ApprovedQuestion(String sid, String qCode) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("select o from StudentAndQuestion o where studentCode = ? and questionCode = ?");
		query.setString(0, sid);
		query.setString(1, qCode);
		List<StudentAndQuestion> question = query.list();
		if (question.size() > 0) {
			question.get(0).setGuard(StudentQuestionStatus.Approved);
			session.save(question.get(0));
		}

		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public void ApprovedQuestion(String sid, List<Integer> qCode) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Query query = session.createQuery("select o from StudentAndQuestion "
				+ "o where studentCode = ? and questionCode in (:alist)");
		query.setString(0, sid);
		query.setParameterList("alist", qCode);
		List<StudentAndQuestion> question = query.list();
		if (question.size() > 0) {
			for (StudentAndQuestion q : question) {
				q.setGuard(StudentQuestionStatus.Approved);
				session.save(q);
			}
		}
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public SelectQuestionResult SelectQuestion(Hashtable<String, String> table) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria dc = session
				.createCriteria(QuestionBank.class, "QuestionBank");
		if (table.containsKey("CourseCode") && table.get("CourseCode") != "") {

			dc.add(Restrictions.in("courseCode",
					table.get("CourseCode").split(",")));
		}
		if (table.containsKey("TeachingMaterialVersion")
				&& table.get("TeachingMaterialVersion") != "") {
			dc.add(Restrictions.in("teachingMaterialVersion",
					table.get("TeachingMaterialVersion").split(",")));
		}
		if (table.containsKey("Year") && table.get("Year") != "") {
			dc.add(Restrictions.in("year", table.get("Year").split(",")));
		}
		if (table.containsKey("Difficulty") && table.get("Difficulty") != "") {
			dc.add(Restrictions.in("difficulty",
					table.get("Difficulty").split(",")));
		}
		if (table.containsKey("QuestionTypes")
				&& table.get("QuestionTypes") != "") {
			dc.add(Restrictions.in("questionTypes", table.get("QuestionTypes")
					.split(",")));
		}
		if (table.containsKey("Types") && table.get("Types") != "") {
			dc.add(Restrictions.in("types", table.get("Types").split(",")));
		}
		if (table.containsKey("Status") && table.get("Status") != "") {

			String[] status = table.get("Status").split("[,]");
			Disjunction dis = Restrictions.disjunction();
			for (String s : status) {
				dis.add(Restrictions.eq("status", GetQuetsionStatus(s)));
			}
			dc.add(dis);
		}
		if (table.containsKey("Organization")
				&& table.get("Organization") != "") {
			dc.add(Restrictions.in("organization", table.get("Organization")
					.split(",")));
		}
		if (table.containsKey("TeacherName") && table.get("TeacherName") != "") {

			Query query = session
					.createQuery("select o.teacherCode from TeacherInfo o where name = ?");
			query.setParameter(0, table.get("TeacherName"));
			List<Object> result = query.list();
			if (result.size() > 0) {
				dc.add(Restrictions.in("teacherCode", result));
			}else{
				result.add("-1");
				dc.add(Restrictions.in("teacherCode", result));
			}

		}
		if (table.containsKey("KnowledgePoint")
				&& table.get("KnowledgePoint") != "") {
			Query query = session
					.createQuery("select o.questionCode from KnowledgePointAndQuestion o where knowledgePointCode in (:alist)");
			query.setParameterList("alist",
					table.get("KnowledgePoint").split(","));
			List<Object> ks = query.list();
			if (ks.size() > 0)
				dc.add(Restrictions.in("code", query.list()));
			else{
				ks.add("-1");
				dc.add(Restrictions.in("code", ks));
			}
		}
		if (table.containsKey("SectionPoint")
				&& table.get("SectionPoint") != "") {
			Query query = session
					.createQuery("select o.questionCode from SectionAndQuestion o where sectionPointCode in (:alist)");
			query.setParameterList("alist", table.get("SectionPoint")
					.split(","));
			List<Object> ks = query.list();
			if (ks.size() > 0)
				dc.add(Restrictions.in("code", query.list()));
			else{
				ks.add("-1");
				dc.add(Restrictions.in("code", ks));
			}
		}

		List<Question> result = new ArrayList<Question>();
		for (Object q : dc.list()) {
			result.add(CreateQuestionByQuestionBank((QuestionBank) q));
		}
		int resultSize = result.size();
		if (table.containsKey("Page")) {
			int count = Integer.parseInt(table.get("Page"));
			dc.setFirstResult(count * PageNumber);
			dc.setMaxResults(PageNumber);
		}
		result = new ArrayList<Question>();
		for (Object q : dc.list()) {
			result.add(CreateQuestionByQuestionBank((QuestionBank) q));
		}

		return new SelectQuestionResult(result,
				resultSize % PageNumber == 0 ? resultSize / PageNumber
						: resultSize / PageNumber + 1);
	}

	private QuestionStatus GetQuetsionStatus(String s) {
		int i = Integer.parseInt(s);
		switch (i) {
		case 0:
			return QuestionStatus.NotSubmited;
		case 1:
			return QuestionStatus.Submited;
		case 2:
			return QuestionStatus.Reversal;
		case 3:
			return QuestionStatus.Accept;

		default:
			break;
		}
		return null;
	}

	public List<S_Question> CreateS_QuestionByQuestionCode(
			List<StudentAndQuestion> stuQuestions, Session session) {

		ArrayList<Integer> qCodes = new ArrayList<Integer>();
		for (StudentAndQuestion q : stuQuestions) {
			qCodes.add(q.getQuestionCode());
		}
		List<Question> qs = CreateQuestionByQuestionCode(qCodes, session);

		ArrayList<S_Question> result = new ArrayList<S_Question>();

		for (Question q : qs) {
			for (StudentAndQuestion sq : stuQuestions) {
				if (q.getCode() == sq.getQuestionCode()) {
					S_Question sr = new S_Question(q.getCode());
					sr.setAnswer(q.getAnswer());
					sr.setName(q.getName());
					sr.setTeacherCode(q.getTeacherCode());
					sr.setCourseCode(q.getCourseCode());
					sr.setDifficulty(q.getDifficulty());
					sr.setOrganization(q.getOrganization());
					sr.setTeachingMaterialVersion(q
							.getTeachingMaterialVersion());
					sr.setYear(q.getYear());
					sr.setTypes(q.getTypes());
					sr.setQuestionTypes(q.getQuestionTypes());
					sr.setReversalComment(q.getReversalComment());
					sr.setReversalTeacherCode(q.getReversalTeacherCode());
					sr.setStatus(q.getStatus());
					sr.setSubmitComment(q.getSubmitComment());
					sr.setCount(q.getCount());
					sr.setUpdateTime(q.getUpdateTime());
					sr.setGuard(sq.getGuard());
					sr.setStudentAnswer(sq.getAnswer());
					result.add(sr);
				}
			}
		}

		return result;
	}

	public S_Question CreateS_QuestionByQuestionCode(int c, Session session) {
		ArrayList<Integer> qCodes = new ArrayList<Integer>();
		qCodes.add(c);
		List<Question> qs = CreateQuestionByQuestionCode(qCodes, session);
		for (Question q : qs) {
			if (q.getCode() == c) {
				S_Question sr = new S_Question(q.getCode());
				sr.setAnswer(q.getAnswer());
				sr.setName(q.getAnswer());
				sr.setTeacherCode(q.getTeacherCode());
				sr.setGuard(StudentQuestionStatus.NotApplyForAnswer);
				sr.setStudentAnswer("");
				sr.setCourseCode(q.getCourseCode());
				sr.setDifficulty(q.getDifficulty());
				sr.setOrganization(q.getOrganization());
				sr.setTeachingMaterialVersion(q.getTeachingMaterialVersion());
				sr.setYear(q.getYear());
				sr.setTypes(q.getTypes());
				sr.setQuestionTypes(q.getQuestionTypes());
				sr.setReversalComment(q.getReversalComment());
				sr.setReversalTeacherCode(q.getReversalTeacherCode());
				sr.setStatus(q.getStatus());
				sr.setSubmitComment(q.getSubmitComment());
				sr.setCount(q.getCount());
				sr.setUpdateTime(q.getUpdateTime());
				return sr;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public SelectTestPaperResult SelectTestPaper(Hashtable<String, String> table) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria dc = session.createCriteria(TestPaperInfo.class,
				"TestPaperInfo");
		if (table.containsKey("CourseCode") && table.get("CourseCode") != "") {
			dc.add(Restrictions.in("courseCode",
					table.get("CourseCode").split(",")));
		}
		if (table.containsKey("TeachingMaterialVersion")
				&& table.get("TeachingMaterialVersion") != "") {
			dc.add(Restrictions.in("teachingMaterialVersion",
					table.get("TeachingMaterialVersion").split(",")));
		}
		if (table.containsKey("Year") && table.get("Year") != "") {
			dc.add(Restrictions.in("year", table.get("Year").split(",")));
		}
		if (table.containsKey("Types") && table.get("Types") != "") {
			dc.add(Restrictions.in("types", table.get("Types").split(",")));
		}
		if (table.containsKey("Status") && table.get("Status") != "") {

			String[] status = table.get("Status").split("[,]");
			Disjunction dis = Restrictions.disjunction();
			for (String s : status) {
				dis.add(Restrictions.eq("status", GetQuetsionStatus(s)));
			}
			dc.add(dis);
		}
		if (table.containsKey("Organization")
				&& table.get("Organization") != "") {
			dc.add(Restrictions.in("organization", table.get("Organization")
					.split(",")));
		}
		if (table.containsKey("TeacherName") && table.get("TeacherName") != "") {

			Query query = session
					.createQuery("select o.teacherCode from TeacherInfo o where name = ?");
			query.setParameter(0, table.get("TeacherName"));
			List<Object> result = query.list();
			if (result.size() > 0) {
				dc.add(Restrictions.in("teacherCode", result));
			} else {
				ArrayList<String> failedResult = new ArrayList<String>();
				failedResult.add("-1");
				dc.add(Restrictions.in("teacherCode", failedResult));
			}
		}

		if (table.containsKey("TestPaerName")
				&& table.get("TestPaerName") != "") {

			Query query = session
					.createQuery("select o.code from TestPaperInfo o where name = ?");
			query.setParameter(0, table.get("TestPaerName"));
			List<Object> result = query.list();
			if (result.size() > 0) {
				dc.add(Restrictions.in("code", result));
			} else {
				ArrayList<Integer> failedResult = new ArrayList<Integer>();
				failedResult.add(-1);
				dc.add(Restrictions.in("code", failedResult));
			}

		}

		List<TestPaper> result = new ArrayList<TestPaper>();
		for (Object t : dc.list()) {
			result.add(CreateTestPaperByTestPaperInfo((TestPaperInfo) t));
		}
		int resultSize = result.size();
		if (table.containsKey("Page")) {
			int count = Integer.parseInt(table.get("Page"));
			dc.setFirstResult(count * PageNumber);
			dc.setMaxResults(PageNumber);
		}
		result = new ArrayList<TestPaper>();
		for (Object t : dc.list()) {
			result.add(CreateTestPaperByTestPaperInfo((TestPaperInfo) t));
		}

		return new SelectTestPaperResult(result,
				resultSize % PageNumber == 0 ? resultSize / PageNumber
						: resultSize / PageNumber + 1);
	}

	public TestPaper CreateTestPaperByTestPaperInfo(TestPaperInfo o) {
		TestPaper paper = new TestPaper(o.getCode());
		paper.setName(o.getName());
		paper.setTeacherCode(o.getTeacherCode());
		paper.setCourseCode(o.getCourseCode());
		paper.setOrganization(o.getOrganization());
		paper.setTeachingMaterialVersion(o.getTeachingMaterialVersion());
		paper.setYear(o.getYear());
		paper.setReversalComment(o.getReversalComment());
		paper.setReversalTeacherCode(o.getReversalTeacherCode());
		paper.setStatus(o.getStatus());
		paper.setSubmitComment(o.getSubmitComment());
		paper.setTypes(o.getTypes());
		paper.setWordPaperId(o.getWordPaperId());
		return paper;
	}

	public void SaveKnowledgePoint(InputStream is, String courseCode,
			String teachingMaterialVersion, String year, String schoolCode)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);

		NodeList list = document.getElementsByTagName("NODES").item(0)
				.getChildNodes();
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("delete KnowledgePoint where courseCode=? and year=? and teachingMaterialVersion=?");
		query.setParameter(0, courseCode);
		query.setParameter(1, year);
		query.setParameter(2, teachingMaterialVersion);
		query.executeUpdate();

		for (int i = 0; i < list.getLength(); i++) {
			Node item = list.item(i);
			if (item.getNodeName().equals("#text")) {
				continue;
			}
			KnowledgePoint point = new KnowledgePoint();
			point.setCaption(item.getAttributes().getNamedItem("Caption")
					.getNodeValue());
			point.setCourseCode(courseCode);
			point.setKey(item.getAttributes().getNamedItem("Key")
					.getNodeValue());
			point.setParentKey(item.getAttributes().getNamedItem("ParentKey")
					.getNodeValue());
			point.setSchoolCode(schoolCode);
			point.setTeachingMaterialVersion(teachingMaterialVersion);
			point.setYear(year);

			session.save(point);
		}

		session.getTransaction().commit();
	}

	public void SaveSectionPoint(InputStream is, String courseCode,
			String teachingMaterialVersion, String year, String schoolCode)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(is);

		NodeList list = document.getElementsByTagName("NODES").item(0)
				.getChildNodes();
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("delete SectionPoint where courseCode=? and year=? and teachingMaterialVersion=?");
		query.setParameter(0, courseCode);
		query.setParameter(1, year);
		query.setParameter(2, teachingMaterialVersion);
		query.executeUpdate();

		for (int i = 0; i < list.getLength(); i++) {
			Node item = list.item(i);
			if (item.getNodeName().equals("#text")) {
				continue;
			}
			SectionPoint point = new SectionPoint();
			point.setCaption(item.getAttributes().getNamedItem("Caption")
					.getNodeValue());
			point.setCourseCode(courseCode);
			point.setKey(item.getAttributes().getNamedItem("Key")
					.getNodeValue());
			point.setParentKey(item.getAttributes().getNamedItem("ParentKey")
					.getNodeValue());
			point.setSchoolCode(schoolCode);
			point.setTeachingMaterialVersion(teachingMaterialVersion);
			point.setYear(year);

			session.save(point);
		}

		session.getTransaction().commit();

	}

	@SuppressWarnings("unchecked")
	public List<KnowledgePoint> GetKnowledgePoint(String courseCode,
			String teachingMaterialVersion, String year, String schoolCode,
			String parentKey) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria dc = session.createCriteria(KnowledgePoint.class);
		if (schoolCode != null)
			dc.add(Restrictions.eq("schoolCode", schoolCode));
		if (year != null)
			dc.add(Restrictions.eq("year", year));
		if (teachingMaterialVersion != null)
			dc.add(Restrictions.eq("teachingMaterialVersion",
					teachingMaterialVersion));
		if (courseCode != null)
			dc.add(Restrictions.eq("courseCode", courseCode));
		if (parentKey == null)
			dc.add(Restrictions.isNull("parentKey"));
		else {
			Criteria dc2 = session.createCriteria(KnowledgePoint.class);
			dc2.add(Restrictions.eq("code", Integer.parseInt(parentKey)));
			KnowledgePoint k = (KnowledgePoint) dc2.uniqueResult();
			dc.add(Restrictions.eq("parentKey", k.getKey()));
		}

		return dc.list();
	}

	@SuppressWarnings("unchecked")
	public List<SectionPoint> GetSectionPoint(String courseCode,
			String teachingMaterialVersion, String year, String schoolCode,
			String parentKey) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria dc = session.createCriteria(SectionPoint.class);
		if (schoolCode != null)
			dc.add(Restrictions.eq("schoolCode", schoolCode));
		if (year != null)
			dc.add(Restrictions.eq("year", year));
		if (teachingMaterialVersion != null)
			dc.add(Restrictions.eq("teachingMaterialVersion",
					teachingMaterialVersion));
		if (courseCode != null)
			dc.add(Restrictions.eq("courseCode", courseCode));
		if (parentKey == null)
			dc.add(Restrictions.isNull("parentKey"));
		else {
			Criteria dc2 = session.createCriteria(SectionPoint.class);
			dc2.add(Restrictions.eq("code", Integer.parseInt(parentKey)));
			SectionPoint k = (SectionPoint) dc2.uniqueResult();
			dc.add(Restrictions.eq("parentKey", k.getKey()));
		}

		return dc.list();
	}

	@SuppressWarnings("unchecked")
	public DeleteStatus deleteQuestion(List<Integer> codes) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		List<Integer> deleteCodes = new ArrayList<Integer>();
		DeleteStatus result = DeleteStatus.Ok;
		Query query0 = session
				.createQuery("select o from QuestionBank o  where o.code in (:alist)");
		query0.setParameterList("alist", codes);
		List<QuestionBank> qs = query0.list();

		for (QuestionBank q : qs) {
			if (q.getCount() == 0) {
				deleteCodes.add(q.getCode());
			} else {
				result = DeleteStatus.Half;
			}
		}

		if (deleteCodes.size() == 0) {
			return DeleteStatus.AllFailed;
		}

		Query query = session
				.createQuery("delete QuestionBank where code in (:alist)");
		query.setParameterList("alist", codes);
		query.executeUpdate();

		Query query2 = session
				.createQuery("delete SectionAndQuestion where questionCode in (:alist)");
		query2.setParameterList("alist", codes);
		query2.executeUpdate();

		Query query3 = session
				.createQuery("delete KnowledgePointAndQuestion where questionCode in (:alist)");
		query3.setParameterList("alist", codes);
		query3.executeUpdate();

		session.getTransaction().commit();

		return result;
	}

	@SuppressWarnings("unchecked")
	public DeleteStatus deletePaper(List<Integer> codes) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("delete TestPaperInfo where code in (:alist)");
		query.setParameterList("alist", codes);
		query.executeUpdate();

		Query query1 = session
				.createQuery("select distinct o.questionCode from TestPaperDetailInfo o where o.paperCode in (:alist)");
		query1.setParameterList("alist", codes);
		List<Integer> qCodes = query1.list();

		Query query2 = session
				.createQuery("select o from QuestionBank o where o.code in (:alist)");
		query2.setParameterList("alist", qCodes);
		List<QuestionBank> qs = query2.list();
		for (QuestionBank q : qs) {
			if (q.getCount() > 0) {
				q.setCount(q.getCount() - 1);
			}
			q.setUpdateTime(new Date());
			session.save(q);
		}

		session.getTransaction().commit();

		return DeleteStatus.Ok;
	}

	@SuppressWarnings("unchecked")
	public List<KnowledgePoint> GetKnowledgePointByQuestion(int code) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query q = session
				.createQuery("select k from KnowledgePoint k , "
						+ "KnowledgePointAndQuestion kq where k.code=kq.knowledgePointCode and kq.questionCode=?");
		q.setParameter(0, code);
		List<KnowledgePoint> qs = q.list();
		return qs;

	}
	
	@SuppressWarnings("unchecked")
	public List<SectionPoint> GetSectionPointByQuestionCode(int code) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query q = session
				.createQuery("select k from SectionPoint k , "
						+ "SectionAndQuestion kq where k.code=kq.sectionPointCode and kq.questionCode=?");
		q.setParameter(0, code);
		List<SectionPoint> qs = q.list();
		return qs;

	}
}
