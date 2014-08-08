package com.questionbase.logic.person;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.questionbase.hbm.InstructorInfo;
import com.questionbase.hbm.KnowledgePointAndQuestion;
import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.SectionAndQuestion;
import com.questionbase.hbm.StudentQuestionStatus;
import com.questionbase.hbm.TeacherInfo;
import com.questionbase.hbm.TestPaperDetailInfo;
import com.questionbase.hbm.TestPaperInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.Person;
import com.questionbase.logic.course.ClassAndCourse;
import com.questionbase.logic.course.StudentQuestion;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.SelectQuestionResult;
import com.questionbase.question.TestPaper;
import com.questionbase.question.TestPaperQuestionDetail;

public class Teacher extends Person {

	private static final long serialVersionUID = 1L;
	protected String teacherCode;
	protected String peopleCode;
	protected String name;
	protected String mainCourseCode;
	protected String subCourseCode;
	protected String schoolCode;
	protected String grade;
	protected List<ClassAndCourse> classAndCourses;

	public Teacher(String code) {
		this.teacherCode = code;
		this.classAndCourses = new ArrayList<ClassAndCourse>();
		InitTeacherByTeacherCode(code);
	}

	@SuppressWarnings("unchecked")
	private void InitTeacherByTeacherCode(String code) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();

		List<TeacherInfo> teachers = session.createCriteria(TeacherInfo.class)
				.add(Restrictions.eq("teacherCode", code)).list();

		if (teachers.size() > 0) {
			for (TeacherInfo info : teachers) {
				teacherCode = info.getTeacherCode();
				peopleCode = info.getPeopleCode();
				mainCourseCode = info.getMainCourseCode();
				subCourseCode = info.getSubCourseCode();
				name = info.getName();
				schoolCode = info.getSchoolCode();
				grade = info.getGrade();
				break;
			}
			InitCourseByTeacherCode(session);
		}
	}

	@SuppressWarnings("unchecked")
	private void InitCourseByTeacherCode(Session session) {
		List<InstructorInfo> infos = session
				.createCriteria(InstructorInfo.class)
				.add(Restrictions.eq("teacherCode", teacherCode)).list();

		for (InstructorInfo info : infos) {
			classAndCourses.add(new ClassAndCourse(info.getClassCode(), info
					.getCourseCode()));
		}
	}

	public Hashtable<Student, List<Question>> getStudentsApplyQuestions() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		List<Student> stus = getStudentCodes(session);
		List<StudentQuestion> qCodes = getStudentApplyQuestionCodes(session,
				stus);

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
	private List<StudentQuestion> getStudentApplyQuestionCodes(Session session,
			List<Student> stus) {
		List<StudentQuestion> result = new ArrayList<StudentQuestion>();

		if (stus.size() == 0) {
			return result;
		}

		List<String> stuCodes = new ArrayList<String>();
		for (Student s : stus) {
			stuCodes.add(s.getCode());
		}

		Query query = session.createQuery("select q.studentCode,question "
				+ "from StudentAndQuestion as q , QuestionBank as question"
				+ " where q.guard = ? and q.studentCode in (:alist) "
				+ " and q.questionCode = question.code order by q.studentCode");
		query.setParameter(0, StudentQuestionStatus.ApplyAndNotApprove);
		query.setParameterList("alist", stuCodes);
		List<Object[]> datas = query.list();

		sb: for (Object[] data : datas) {
			if (data.length >= 2) {
				Question q = QuestionManager.GetInstance()
						.CreateQuestionByQuestionBank((QuestionBank) data[1]);
				Student tempStu = null;
				for (Student s : stus) {
					if (s.getCode().equals(data[0])) {
						tempStu = s;
						break;
					}
				}
				if (tempStu != null) {
					for (StudentQuestion s : result) {
						if (s.getStudent().equals(result)) {
							s.getQuestions().add(q);
							continue sb;
						}
					}

					result.add(new StudentQuestion(tempStu, q));

				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Student> getStudentCodes(Session session) {
		Query query = session
				.createQuery("select student.studentCode,student.name "
						+ "from InstructorInfo as instructor,StudentInfo as student "
						+ "where instructor.teacherCode = ? and instructor.classCode = student.classCode");
		query.setString(0, teacherCode);
		List<Object[]> datas = query.list();
		List<Student> result = new ArrayList<Student>();
		for (Object[] data : datas) {
			if (data.length >= 2) {
				result.add(new Student(data[0].toString(), data[1].toString()));
			}
		}

		return result;
	}

	public Question CreateQuestion(Hashtable<String, String> table) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		QuestionBank question = doCreateQuestionBank(table);
		question.setStatus(QuestionStatus.NotSubmited);
		session.save(question);
		
		if (table.containsKey("Zhishidian") && table.get("Zhishidian") != "") {
			String[] ks = table.get("Zhishidian").split(",");
			for (String kCode : ks) {
				kCode = kCode.trim();
				if (!kCode.equals("")) {
					KnowledgePointAndQuestion kaq = new KnowledgePointAndQuestion();
					kaq.setKnowledgePointCode(kCode);
					kaq.setQuestionCode(question.getCode());
					session.save(kaq);
				}
			}
		}

		if (table.containsKey("Zhangjie") && table.get("Zhangjie") != "") {
			String[] ss = table.get("Zhangjie").split(",");
			for (String sCode : ss) {
				sCode = sCode.trim();
				if (!sCode.equals("")) {
					SectionAndQuestion saq = new SectionAndQuestion();
					saq.setSectionPointCode(sCode);
					saq.setQuestionCode(question.getCode());
					session.save(saq);
				}
			}
		}
		
		session.getTransaction().commit();

		return QuestionManager.GetInstance().CreateQuestionByQuestionBank(
				question);
	}

	public TestPaper CreateTestPaper(Hashtable<String, String> table,
			List<TestPaperQuestionDetail> details) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		TestPaperInfo paper = doCreateTestPaper(table);
		paper.setStatus(QuestionStatus.NotSubmited);
		session.save(paper);
		int count = 0;
		for (TestPaperQuestionDetail detail : details) {
			TestPaperDetailInfo info = new TestPaperDetailInfo();
			info.setPaperCode(paper.getCode());
			info.setQuestionCode(detail.getQuestionCode());
			info.setScore(detail.getScore());
			info.setSequence(count);
			info.setUpdateTime(new Date(new java.util.Date().getTime()));
			Criteria c = session.createCriteria(QuestionBank.class);
			c.add(Restrictions.eq("code", detail.getQuestionCode()));
			QuestionBank q = (QuestionBank) c.uniqueResult();
			if (q != null) {
				q.setUpdateTime(new java.util.Date());
				q.setCount(q.getCount() + 1);
				session.save(q);
			}
			session.save(info);
			count++;
		}
		session.getTransaction().commit();
		return QuestionManager.GetInstance().CreateTestPaperByTestPaperInfo(
				paper);
	}

	private TestPaperInfo doCreateTestPaper(Hashtable<String, String> table) {
		TestPaperInfo paper = new TestPaperInfo();
		if (table.containsKey("Code") && table.get("Code") != "") {
			paper.setCode(Integer.parseInt(table.get("Code")));
		}

		if (table.containsKey("Name") && table.get("Name") != "") {
			paper.setName(table.get("Name"));
		}

		if (table.containsKey("TeacherCode") && table.get("TeacherCode") != "") {
			paper.setTeacherCode(table.get("TeacherCode"));
		} else {
			paper.setTeacherCode(this.teacherCode);
		}

		if (table.containsKey("CourseCode") && table.get("CourseCode") != "") {
			paper.setCourseCode(table.get("CourseCode"));
		}
		if (table.containsKey("TeachingMaterialVersion")
				&& table.get("TeachingMaterialVersion") != "") {
			paper.setTeachingMaterialVersion(table
					.get("TeachingMaterialVersion"));
		}
		if (table.containsKey("Year") && table.get("Year") != "") {
			paper.setYear(table.get("Year"));
		}
		if (table.containsKey("Organization")
				&& table.get("Organization") != "") {
			paper.setOrganization(table.get("Organization"));
		} else {
			paper.setOrganization(this.schoolCode);
		}
		if (table.containsKey("SubmitComment")
				&& table.get("SubmitComment") != "") {
			paper.setSubmitComment(table.get("SubmitComment"));
		}
		if (table.containsKey("ReversalTeacherCode")
				&& table.get("ReversalTeacherCode") != "") {
			paper.setReversalTeacherCode(table.get("ReversalTeacherCode"));
		}
		if (table.containsKey("ReversalComment")
				&& table.get("ReversalComment") != "") {
			paper.setReversalComment(table.get("ReversalComment"));
		}
		if (table.containsKey("Types") && table.get("Types") != "") {
			paper.setTypes(table.get("Types"));
		}

		if (table.containsKey("WordPaperId") && table.get("WordPaperId") != "") {
			paper.setWordPaperId(table.get("WordPaperId"));
		}

		return paper;
	}

	public SelectQuestionResult GetAllQuestions(Hashtable<String, String> table) {
		if (!table.contains("TeacherCode")) {
			table.put("TeacherCode", teacherCode);
		}
		return QuestionManager.GetInstance().SelectQuestion(table);
	}

	public Question SaveQuestion(Hashtable<String, String> table) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		QuestionBank question = doCreateQuestionBank(table);
		question.setStatus(QuestionStatus.NotSubmited);
		session.save(question);
		
		Query q1 = session.createQuery("delete KnowledgePointAndQuestion where questionCode=?");
		q1.setParameter(0, question.getCode());
		q1.executeUpdate();
		
		Query q2 = session.createQuery("delete SectionAndQuestion where questionCode=?");
		q2.setParameter(0, question.getCode());
		q2.executeUpdate();

		if (table.containsKey("Zhishidian") && table.get("Zhishidian") != "") {
			String[] ks = table.get("Zhishidian").split(",");
			for (String kCode : ks) {
				kCode = kCode.trim();
				if (!kCode.equals("")) {
					KnowledgePointAndQuestion kaq = new KnowledgePointAndQuestion();
					kaq.setKnowledgePointCode(kCode);
					kaq.setQuestionCode(question.getCode());
					session.save(kaq);
				}
			}
		}

		if (table.containsKey("Zhangjie") && table.get("Zhangjie") != "") {
			String[] ss = table.get("Zhangjie").split(",");
			for (String sCode : ss) {
				sCode = sCode.trim();
				if (!sCode.equals("")) {
					SectionAndQuestion saq = new SectionAndQuestion();
					saq.setSectionPointCode(sCode);
					saq.setQuestionCode(question.getCode());
					session.save(saq);
				}
			}
		}

		session.getTransaction().commit();
		return QuestionManager.GetInstance().CreateQuestionByQuestionBank(
				question);
	}

	private QuestionBank doCreateQuestionBank(Hashtable<String, String> table) {
		QuestionBank question = new QuestionBank();
		if (table.containsKey("Code") && table.get("Code") != "") {
			question.setCode(Integer.parseInt(table.get("Code")));
		}

		if (table.containsKey("Name") && table.get("Name") != "") {
			question.setName(table.get("Name"));
		}

		if (table.containsKey("TeacherCode") && table.get("TeacherCode") != "") {
			question.setTeacherCode(table.get("TeacherCode"));
		} else {
			question.setTeacherCode(this.teacherCode);
		}

		if (table.containsKey("Answer") && table.get("Answer") != "") {
			question.setAnswer(table.get("Answer"));
		}
		if (table.containsKey("CourseCode") && table.get("CourseCode") != "") {
			question.setCourseCode(table.get("CourseCode"));
		}
		if (table.containsKey("TeachingMaterialVersion")
				&& table.get("TeachingMaterialVersion") != "") {
			question.setTeachingMaterialVersion(table
					.get("TeachingMaterialVersion"));
		}
		if (table.containsKey("Year") && table.get("Year") != "") {
			question.setYear(table.get("Year"));
		}
		if (table.containsKey("Difficulty") && table.get("Difficulty") != "") {
			question.setDifficulty(table.get("Difficulty"));
		}
		if (table.containsKey("QuestionTypes")
				&& table.get("QuestionTypes") != "") {
			question.setQuestionTypes(table.get("QuestionTypes"));
		}
		if (table.containsKey("Types") && table.get("Types") != "") {
			question.setTypes(table.get("Types"));
		}
		if (table.containsKey("Organization")
				&& table.get("Organization") != "") {
			question.setOrganization(table.get("Organization"));
		} else {
			question.setOrganization(this.schoolCode);
		}
		if (table.containsKey("SubmitComment")
				&& table.get("SubmitComment") != "") {
			question.setSubmitComment(table.get("SubmitComment"));
		}
		if (table.containsKey("ReversalTeacherCode")
				&& table.get("ReversalTeacherCode") != "") {
			question.setReversalTeacherCode(table.get("ReversalTeacherCode"));
		}
		if (table.containsKey("ReversalComment")
				&& table.get("ReversalComment") != "") {
			question.setReversalComment(table.get("ReversalComment"));
		}
		return question;

	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getPeopleCode() {
		return peopleCode;
	}

	public void setPeopleCode(String peopleCode) {
		this.peopleCode = peopleCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainCourseCode() {
		return mainCourseCode;
	}

	public void setMainCourseCode(String mainCourseCode) {
		this.mainCourseCode = mainCourseCode;
	}

	public String getSubCourseCode() {
		return subCourseCode;
	}

	public void setSubCourseCode(String subCourseCode) {
		this.subCourseCode = subCourseCode;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<ClassAndCourse> getClassAndCourses() {
		return classAndCourses;
	}

	public void setClassAndCourses(List<ClassAndCourse> classAndCourses) {
		this.classAndCourses = classAndCourses;
	}

	public void ApprovedQuestion(String sid, String qid) {
		QuestionManager.GetInstance().ApprovedQuestion(sid, qid);
	}

	public Question SubmitQuesiont(int qCode) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(QuestionBank.class);
		c.add(Restrictions.eq("code", qCode));
		QuestionBank q = (QuestionBank) c.uniqueResult();

		if (q != null) {
			q.setStatus(QuestionStatus.Submited);
			session.save(q);
			session.getTransaction().commit();
			return QuestionManager.GetInstance()
					.CreateQuestionByQuestionBank(q);
		}
		return null;
	}

	public TestPaper SubmitTestPaper(int qCode) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		Criteria c = session.createCriteria(TestPaperInfo.class);
		c.add(Restrictions.eq("code", qCode));
		TestPaperInfo q = (TestPaperInfo) c.uniqueResult();

		if (q != null) {
			q.setStatus(QuestionStatus.Submited);
			session.save(q);
			session.getTransaction().commit();
			return QuestionManager.GetInstance()
					.CreateTestPaperByTestPaperInfo(q);
		}
		return null;
	}
}
