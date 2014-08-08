package com.questionbase.controller;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONArray;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.questionbase.bean.SaveAttributeForm;
import com.questionbase.bean.SaveAttributeRelationForm;
import com.questionbase.bean.UpdateForm;
import com.questionbase.hbm.CourseAndMaterialVersion;
import com.questionbase.hbm.CourseAndQuestionType;
import com.questionbase.hbm.CourseAndTypes;
import com.questionbase.hbm.CourseInfo;
import com.questionbase.hbm.QuestionTypeInfo;
import com.questionbase.hbm.TeachingMaterialVersionInfo;
import com.questionbase.hbm.TypesInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.SchoolAdmin;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping("/savezjxx")
	public String savezjxx(@ModelAttribute UpdateForm form, Model model)
			throws ParserConfigurationException, SAXException, IOException {

		return saveZjFile(form, model);
	}

	@RequestMapping("/savezsd")
	public String savezsd(@ModelAttribute UpdateForm form, Model model)
			throws ParserConfigurationException, SAXException, IOException {

		return saveZsdFile(form, model);
	}

	@RequestMapping("/editzjxx")
	public String editzjxx(Model model) {
		model.addAttribute("edittype", "章节信息");
		return "admin/editinfo";
	}

	@RequestMapping("/editzsd")
	public String editzsd(Model model) {
		model.addAttribute("edittype", "知识点");
		return "admin/editinfo";
	}

	@RequestMapping("/addattribute")
	public String addAttribute(Model model) {
		return "admin/addAttribute";
	}

	@RequestMapping("/addAttributeRelation")
	public String addAttributeRelation(Model model) {
		String schoolCode = ((SchoolAdmin) ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getAttribute("LoginUser")).getSchoolCode();
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		model.addAttribute("courseData", MakCourseJsonData(session));
		model.addAttribute("teachingMaterialVersionInfoData",
				MakTeachingMaterialVersionInfoJsonData(session, schoolCode));
		model.addAttribute("questionTypeData",
				MakQuestionTypeDataJsonData(session, schoolCode));
		model.addAttribute("typesInfoData",
				MakTypeInfoDataJsonData(session, schoolCode));
		model.addAttribute("courseAndMaterialVersion",
				MakcourseAndMaterialVersionJsonData(session, schoolCode));
		model.addAttribute("courseAndQuestionType",
				MakcourseAndQuestionTypeJsonData(session, schoolCode));
		model.addAttribute("courseAndTypes",
				MakcourseAndTypesJsonData(session, schoolCode));

		return "admin/addAttributeRelation";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object MakcourseAndTypesJsonData(Session session, String schoolCode) {
		Query query = session
				.createQuery("select c,t from CourseInfo c,TypesInfo t,"
						+ " CourseAndTypes ct "
						+ "where t.organization=? and c.courseCode = ct.courseCode and t.code = ct.typesCode");
		query.setParameter(0, schoolCode);
		List<Object[]> list = query.list();

		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();

		for (Object[] objList : list) {
			CourseInfo cInfo = (CourseInfo) objList[0];
			TypesInfo tInfo = (TypesInfo) objList[1];
			arrayList.add(new AbstractMap.SimpleEntry(cInfo.getCourseCode(),
					String.valueOf(tInfo.getCode())));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object MakcourseAndQuestionTypeJsonData(Session session,
			String schoolCode) {
		Query query = session
				.createQuery("select c,t from CourseInfo c,QuestionTypeInfo t,"
						+ " CourseAndQuestionType ct "
						+ "where t.organization=? and c.courseCode = ct.courseCode and t.code = ct.questionTypeCode");
		query.setParameter(0, schoolCode);
		List<Object[]> list = query.list();

		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();

		for (Object[] objList : list) {
			CourseInfo cInfo = (CourseInfo) objList[0];
			QuestionTypeInfo tInfo = (QuestionTypeInfo) objList[1];
			arrayList.add(new AbstractMap.SimpleEntry(cInfo.getCourseCode(),
					String.valueOf(tInfo.getCode())));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object MakTypeInfoDataJsonData(Session session, String schoolCode) {
		Query query = session.createQuery("select t from TypesInfo t where organization=?");
		query.setParameter(0, schoolCode);
		List<TypesInfo> list = query.list();
		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();
		for (TypesInfo t : list) {
			arrayList.add(new AbstractMap.SimpleEntry(String.valueOf(t
					.getCode()), t.getName()));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object MakQuestionTypeDataJsonData(Session session,
			String schoolCode) {
		Query query = session.createQuery("select t from QuestionTypeInfo t where organization=?");
		query.setParameter(0, schoolCode);
		List<QuestionTypeInfo> list = query.list();
		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();
		for (QuestionTypeInfo t : list) {
			arrayList.add(new AbstractMap.SimpleEntry(String.valueOf(t
					.getCode()), t.getName()));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object MakcourseAndMaterialVersionJsonData(Session session,
			String schoolCode) {
		Query query = session
				.createQuery("select c,t from CourseInfo c,TeachingMaterialVersionInfo t,"
						+ " CourseAndMaterialVersion ct "
						+ "where t.organization=? and c.courseCode = ct.courseCode and t.code = ct.teachingMaterialVersionCode");
		query.setParameter(0, schoolCode);
		List<Object[]> list = query.list();

		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();

		for (Object[] objList : list) {
			CourseInfo cInfo = (CourseInfo) objList[0];
			TeachingMaterialVersionInfo tInfo = (TeachingMaterialVersionInfo) objList[1];
			arrayList.add(new AbstractMap.SimpleEntry(cInfo.getCourseCode(),
					String.valueOf(tInfo.getCode())));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object MakTeachingMaterialVersionInfoJsonData(Session session,
			String schoolCode) {
		Query query = session
				.createQuery("select t from TeachingMaterialVersionInfo t where organization=?");
		query.setParameter(0, schoolCode);
		List<TeachingMaterialVersionInfo> list = query.list();
		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();
		for (TeachingMaterialVersionInfo t : list) {
			arrayList.add(new AbstractMap.SimpleEntry(String.valueOf(t
					.getCode()), t.getName()));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String MakCourseJsonData(Session session) {
		Query query = session.createQuery("select c from CourseInfo c");
		List<CourseInfo> list = query.list();
		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();
		for (CourseInfo c : list) {
			arrayList.add(new AbstractMap.SimpleEntry(c.getCourseCode(), c
					.getName()));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	@RequestMapping("/saveAttributeRelation")
	public String saveAttributeRelation(
			@ModelAttribute SaveAttributeRelationForm form, Model model) {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		String schoolCode = ((SchoolAdmin) ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getAttribute("LoginUser")).getSchoolCode();

		saveCourseAndMaterialVersion(form, session, schoolCode);
		saveCourseAndQuestionType(form, session, schoolCode);
		saveCourseAndTypes(form, session, schoolCode);

		session.getTransaction().commit();
		return addAttributeRelation(model);
	}

	private void saveCourseAndTypes(SaveAttributeRelationForm form,
			Session session, String schoolCode) {
		JSONArray jsonArray = JSONArray.fromObject(form.getCourseAndTypes());
		try {
			if (jsonArray.getJSONObject(0).isNullObject()) {
				return;
			}

			Query deleteQuery = session
					.createQuery("delete from CourseAndTypes where schoolCode =?");
			deleteQuery.setParameter(0, schoolCode);
			deleteQuery.executeUpdate();
			for (int i = 0; i < jsonArray.size(); i++) {

				String cCode = jsonArray.getJSONObject(i).getString("key");
				String tCode = jsonArray.getJSONObject(i).getString("value");

				CourseAndTypes data = new CourseAndTypes();
				data.setCourseCode(cCode);
				data.setTypesCode(Integer.parseInt(tCode));
				data.setSchoolCode(schoolCode);
				session.saveOrUpdate(data);

			}
		} catch (Exception e) {
		}

	}

	private void saveCourseAndQuestionType(SaveAttributeRelationForm form,
			Session session, String schoolCode) {
		JSONArray jsonArray = JSONArray.fromObject(form
				.getCourseAndQuestionType());
		try {
			if (jsonArray.getJSONObject(0).isNullObject()) {
				return;
			}

			Query deleteQuery = session
					.createQuery("delete from CourseAndQuestionType where schoolCode =?");
			deleteQuery.setParameter(0, schoolCode);
			deleteQuery.executeUpdate();
			for (int i = 0; i < jsonArray.size(); i++) {

				String cCode = jsonArray.getJSONObject(i).getString("key");
				String tCode = jsonArray.getJSONObject(i).getString("value");

				CourseAndQuestionType data = new CourseAndQuestionType();
				data.setCourseCode(cCode);
				data.setQuestionTypeCode(Integer.parseInt(tCode));
				data.setSchoolCode(schoolCode);
				session.saveOrUpdate(data);

			}
		} catch (Exception e) {
		}

	}

	private void saveCourseAndMaterialVersion(SaveAttributeRelationForm form,
			Session session, String schoolCode) {
		JSONArray jsonArray = JSONArray.fromObject(form
				.getCourseAndMaterialVersion());
		try {
			if (jsonArray.getJSONObject(0).isNullObject()) {
				return;
			}

			Query deleteQuery = session
					.createQuery("delete from CourseAndMaterialVersion where schoolCode =?");
			deleteQuery.setParameter(0, schoolCode);
			deleteQuery.executeUpdate();
			for (int i = 0; i < jsonArray.size(); i++) {

				String cCode = jsonArray.getJSONObject(i).getString("key");
				String tCode = jsonArray.getJSONObject(i).getString("value");

				CourseAndMaterialVersion data = new CourseAndMaterialVersion();
				data.setCourseCode(cCode);
				data.setTeachingMaterialVersionCode(Integer.parseInt(tCode));
				data.setSchoolCode(schoolCode);
				session.saveOrUpdate(data);

			}
		} catch (Exception e) {
		}

	}

	@RequestMapping(value = "/saveAttribute")
	public String saveAttribute(@ModelAttribute SaveAttributeForm form,
			Model model) throws ParserConfigurationException, SAXException,
			IOException {
		SchoolAdmin p2 = (SchoolAdmin) ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getAttribute("LoginUser");
		String schoolCode = p2.getSchoolCode();
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		String teachingMaterialVersions = form.getTeachingMaterialVersion();
		String questionType = form.getQuestionType();
		String types = form.getTypes();
		if (teachingMaterialVersions != null
				&& !teachingMaterialVersions.trim().equals("")) {
			for (String name : teachingMaterialVersions.split(",")) {
				TeachingMaterialVersionInfo t = new TeachingMaterialVersionInfo();
				t.setName(name);
				t.setOrganization(schoolCode);
				session.save(t);
			}
		}

		if (questionType != null && !questionType.trim().equals("")) {
			for (String name : questionType.split(",")) {
				QuestionTypeInfo t = new QuestionTypeInfo();
				t.setName(name);
				t.setOrganization(schoolCode);
				session.save(t);
			}
		}

		if (types != null && !types.trim().equals("")) {
			for (String name : types.split(",")) {
				TypesInfo t = new TypesInfo();
				t.setName(name);
				t.setOrganization(schoolCode);
				session.save(t);
			}
		}

		session.getTransaction().commit();
		return "admin/addAttribute";
	}

	private String saveZsdFile(UpdateForm form, Model model) {
		model.addAttribute("edittype", form.getEdittype());
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		SchoolAdmin p2 = (SchoolAdmin) session.getAttribute("LoginUser");

		MultipartFile file = form.getXmlFile();

		try {
			p2.SaveKnowledgePoint(file.getInputStream(), form.getKemu(),
					form.getJiaocaibanben(), form.getNianji());
			model.addAttribute("status", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status", "error");
		}
		return "admin/editinfo";
	}

	private String saveZjFile(UpdateForm form, Model model) {
		model.addAttribute("edittype", form.getEdittype());
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		SchoolAdmin p2 = (SchoolAdmin) session.getAttribute("LoginUser");

		MultipartFile file = form.getXmlFile();

		try {
			p2.SaveSectionPoint(file.getInputStream(), form.getKemu(),
					form.getJiaocaibanben(), form.getNianji());
			model.addAttribute("status", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status", "error");
		}
		return "admin/editinfo";
	}
}