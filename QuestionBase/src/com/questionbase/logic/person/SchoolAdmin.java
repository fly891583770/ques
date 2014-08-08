package com.questionbase.logic.person;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.questionbase.logic.Person;
import com.questionbase.question.QuestionManager;

public class SchoolAdmin extends Person {

	private static final long serialVersionUID = 1L;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	private String schoolCode;

	public SchoolAdmin(String code, String schoolCode) {
		this.code = code;
		this.schoolCode = schoolCode;
	}

	public String getName() {
		return "学校管理员";
	}

	public void SaveKnowledgePoint(InputStream is, String courseCode,
			String teachingMaterialVersion, String year)
			throws ParserConfigurationException, SAXException, IOException {
		QuestionManager.GetInstance().SaveKnowledgePoint(is, courseCode,
				teachingMaterialVersion, year, schoolCode);
	}

	public void SaveSectionPoint(InputStream is, String courseCode,
			String teachingMaterialVersion, String year)
			throws ParserConfigurationException, SAXException, IOException {
		QuestionManager.GetInstance().SaveSectionPoint(is, courseCode,
				teachingMaterialVersion, year, schoolCode);

	}
}
