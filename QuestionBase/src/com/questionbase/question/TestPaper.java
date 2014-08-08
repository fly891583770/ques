package com.questionbase.question;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.questionbase.hbm.TestPaperInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.Teacher;

public class TestPaper {
	protected int code;

	protected String name;

	protected String teacherCode;

	private String courseCode;

	private String teachingMaterialVersion;

	private String year;

	private String organization;

	private QuestionStatus status;

	private String submitComment;

	private String reversalTeacherCode;

	private String reversalComment;

	private String teacherName;

	private String types;
	
	private String wordPaperId;

	public TestPaper() {

	}

	public TestPaper(int code) {
		this.code = code;
		init();
	}

	
	public String getWordPaperId() {
		return wordPaperId;
	}

	public void setWordPaperId(String wordPaperId) {
		this.wordPaperId = wordPaperId;
	}

	private void init() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria c = session.createCriteria(TestPaperInfo.class);
		c.add(Restrictions.eq("code", code));
		TestPaperInfo q = (TestPaperInfo) c.uniqueResult();
		this.name = q.getName();
		this.teacherCode = q.getTeacherCode();
		this.code = q.getCode();
		this.courseCode = q.getCourseCode();
		this.organization = q.getOrganization();
		this.reversalComment = q.getReversalComment();
		this.reversalTeacherCode = q.getReversalTeacherCode();
		this.status = q.getStatus();
		this.submitComment = q.getSubmitComment();
		this.teachingMaterialVersion = q.getTeachingMaterialVersion();
		this.types = q.getTypes();
		this.wordPaperId = q.getWordPaperId();
		this.year = q.getYear();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public int getCode() {
		return code;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getTeachingMaterialVersion() {
		return teachingMaterialVersion;
	}

	public void setTeachingMaterialVersion(String teachingMaterialVersion) {
		this.teachingMaterialVersion = teachingMaterialVersion;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(TestPaper.class)) {
			return false;
		}

		TestPaper q = (TestPaper) o;
		if (q.getCode() == this.getCode()) {
			return true;
		}
		return false;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		TestPaper q = new TestPaper(this.getCode());
		q.setName(this.getName());
		q.setTeacherCode(this.getTeacherCode());
		return q;
	}

	public QuestionStatus getStatus() {
		return status;
	}

	public void setStatus(QuestionStatus status) {
		this.status = status;
	}

	public String getSubmitComment() {
		return submitComment;
	}

	public void setSubmitComment(String submitComment) {
		this.submitComment = submitComment;
	}

	public String getReversalTeacherCode() {
		return reversalTeacherCode;
	}

	public void setReversalTeacherCode(String reversalTeacherCode) {
		this.reversalTeacherCode = reversalTeacherCode;
	}

	public String getReversalComment() {
		return reversalComment;
	}

	public void setReversalComment(String reversalComment) {
		this.reversalComment = reversalComment;
	}

	public String getTeacherName() {
		if (teacherName != null) {
			return teacherName;
		}

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");

		teacherName = t.getName();
		return teacherName;
	}

	public TestPaperDetail getDetail() {
		return new TestPaperDetail(code);
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

}
