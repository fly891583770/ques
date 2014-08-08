package com.questionbase.question;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.TeacherInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;

public class Question {
	protected int code;

	protected String name;

	protected String teacherCode;

	protected String answer;

	private String courseCode;

	private String teachingMaterialVersion;

	private String year;

	private String difficulty;

	private String questionTypes;

	private String types;

	private String organization;

	private QuestionStatus status;

	private String submitComment;

	private String reversalTeacherCode;

	private String reversalComment;

	private String teacherName;

	private int count;

	private Date updateTime;

	private String summary;

	public Question() {

	}

	public Question(int code) {
		this.code = code;
		init();
	}

	private void init() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria c = session.createCriteria(QuestionBank.class);
		c.add(Restrictions.eq("code", code));
		QuestionBank q = (QuestionBank) c.uniqueResult();
		this.name = q.getName();
		this.teacherCode = q.getTeacherCode();
		this.answer = q.getAnswer();
		this.courseCode = q.getCourseCode();
		this.teachingMaterialVersion = q.getTeachingMaterialVersion();
		this.year = q.getYear();
		this.difficulty = q.getDifficulty();
		this.questionTypes = q.getQuestionTypes();
		this.types = q.getTypes();
		this.organization = q.getOrganization();
		this.status = q.getStatus();
		this.submitComment = q.getSubmitComment();
		this.reversalTeacherCode = q.getReversalTeacherCode();
		this.reversalComment = q.getReversalComment();
		this.count = q.getCount();
		this.updateTime = q.getUpdateTime();
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getQuestionTypes() {
		return questionTypes;
	}

	public void setQuestionTypes(String questionTypes) {
		this.questionTypes = questionTypes;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
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

	public Date getUpdateTime() {
		if (updateTime == null) {
			return new Date();
		}

		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(Question.class)) {
			return false;
		}

		Question q = (Question) o;
		if (q.getCode() == this.getCode()) {
			return true;
		}
		return false;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Question q = new Question(this.getCode());
		q.setAnswer(this.getAnswer());
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

		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Criteria c = session.createCriteria(TeacherInfo.class);
		c.add(Restrictions.eq("teacherCode", teacherCode));
		TeacherInfo t = (TeacherInfo) c.uniqueResult();
		if (t != null) {
			teacherName = t.getName();
			return teacherName;
		}

		return "";

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSummary() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o.summary from Stream o where o.code=?");

		try {
			query.setParameter(0, Integer.parseInt(name));

			summary = query.uniqueResult().toString();
			return summary;
		} catch (Exception e) {
			return "";
		}

	}

}
