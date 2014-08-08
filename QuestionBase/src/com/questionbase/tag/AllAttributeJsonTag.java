package com.questionbase.tag;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;

import org.hibernate.Query;
import org.hibernate.Session;

import com.questionbase.hbm.CourseAndMaterialVersion;
import com.questionbase.hbm.CourseAndQuestionType;
import com.questionbase.hbm.CourseAndTypes;
import com.questionbase.hbm.CourseInfo;
import com.questionbase.hbm.QuestionTypeInfo;
import com.questionbase.hbm.SchoolInfo;
import com.questionbase.hbm.TeachingMaterialVersionInfo;
import com.questionbase.hbm.TypesInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;

public class AllAttributeJsonTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SchoolInfo> schoolInfos;

	private List<CourseInfo> cs;

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest req = (HttpServletRequest) pageContext
					.getRequest();

			pageContext.getOut().print("<script type=\"text/javascript\">");

			Object user = req.getSession().getAttribute("LoginUser");
			String schoolCode = "";
			if (user != null) {
				if (user instanceof Student) {
					Student stu = (Student) user;
					schoolCode = stu.getSchoolCode();
				}

				if (user instanceof Parent) {
					Parent parent = (Parent) user;
					Student s = (Student) parent.getChild();
					schoolCode = s.getSchoolCode();
				}

				if (user instanceof Teacher) {
					Teacher teacher = (Teacher) user;
					schoolCode = teacher.getSchoolCode();
				}
				if (user instanceof SchoolAdmin) {
					SchoolAdmin admin = (SchoolAdmin) user;
					schoolCode = admin.getSchoolCode();
				}
			}
			Session session = QuestionBaseFactory.GetInstance()
					.getSessionFactory().openSession();

			pageContext.getOut().print(
					"var schoolJson = " + doInitSchoolCode(schoolCode, session)
							+ ";" + "\r\n");

			pageContext.getOut().print(
					"var courseJson = " + doInitCourseCode(session) + ";"
							+ "\r\n");

			pageContext.getOut().print(
					"var tmvJson = " + doInitTeachingMaterialVersion(session)
							+ ";" + "\r\n");

			pageContext.getOut().print(
					"var questionTypeJson = " + doInitQuestionTypeJson(session)
							+ ";" + "\r\n");
			
			pageContext.getOut().print(
					"var typesJson = " + doInitTypesInfoJson(session)
							+ ";" + "\r\n");

			pageContext.getOut().print("</script>");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String doInitQuestionTypeJson(Session session) {
		Query query = session
				.createQuery("select o from QuestionTypeInfo o where o.organization in (:alist)");
		List<String> list = new ArrayList<String>();
		for (SchoolInfo sc : schoolInfos) {
			list.add(sc.getCode());
		}
		query.setParameterList("alist", list);
		List<QuestionTypeInfo> ts = query.list();

		Query query2 = session
				.createQuery("select o from CourseAndQuestionType o where o.schoolCode in (:alist)");
		query2.setParameterList("alist", list);
		List<CourseAndQuestionType> caqs = query2.list();

		ArrayList<Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>> arrayList = new ArrayList<Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>>();

		for (SchoolInfo sc : schoolInfos) {
			ArrayList<Map.Entry<String, Map.Entry<String, String>>> subList = new ArrayList<Map.Entry<String, Map.Entry<String, String>>>();

			for (CourseInfo c : cs) {
				ArrayList<Map.Entry<String, String>> subList2 = new ArrayList<Map.Entry<String, String>>();

				for (CourseAndQuestionType cam : caqs) {
					if (cam.getCourseCode().equals(c.getCourseCode())
							&& cam.getSchoolCode().equals(sc.getCode())) {
						int tCode = cam.getQuestionTypeCode();

						for (QuestionTypeInfo t : ts) {
							if (t.getCode() == tCode) {
								subList2.add(new AbstractMap.SimpleEntry(String
										.valueOf(t.getCode()), t.getName()));
							}
						}
					}
				}
				subList.add(new AbstractMap.SimpleEntry(c.getCourseCode(),
						subList2));
			}

			arrayList.add(new AbstractMap.SimpleEntry(sc.getCode(), subList));
		}

		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String doInitTypesInfoJson(Session session) {
		Query query = session
				.createQuery("select o from TypesInfo o where o.organization in (:alist)");
		List<String> list = new ArrayList<String>();
		for (SchoolInfo sc : schoolInfos) {
			list.add(sc.getCode());
		}
		query.setParameterList("alist", list);
		List<TypesInfo> ts = query.list();

		Query query2 = session
				.createQuery("select o from CourseAndTypes o where o.schoolCode in (:alist)");
		query2.setParameterList("alist", list);
		List<CourseAndTypes> cmvs = query2.list();

		ArrayList<Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>> arrayList = new ArrayList<Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>>();

		for (SchoolInfo sc : schoolInfos) {
			ArrayList<Map.Entry<String, Map.Entry<String, String>>> subList = new ArrayList<Map.Entry<String, Map.Entry<String, String>>>();

			for (CourseInfo c : cs) {
				ArrayList<Map.Entry<String, String>> subList2 = new ArrayList<Map.Entry<String, String>>();

				for (CourseAndTypes cam : cmvs) {
					if (cam.getCourseCode().equals(c.getCourseCode())
							&& cam.getSchoolCode().equals(sc.getCode())) {
						int tCode = cam.getTypesCode();

						for (TypesInfo t : ts) {
							if (t.getCode() == tCode) {
								subList2.add(new AbstractMap.SimpleEntry(String
										.valueOf(t.getCode()), t.getName()));
							}
						}
					}
				}
				subList.add(new AbstractMap.SimpleEntry(c.getCourseCode(),
						subList2));
			}

			arrayList.add(new AbstractMap.SimpleEntry(sc.getCode(), subList));
		}

		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String doInitTeachingMaterialVersion(Session session) {
		Query query = session
				.createQuery("select o from TeachingMaterialVersionInfo o where o.organization in (:alist)");
		List<String> list = new ArrayList<String>();
		for (SchoolInfo sc : schoolInfos) {
			list.add(sc.getCode());
		}
		query.setParameterList("alist", list);
		List<TeachingMaterialVersionInfo> ts = query.list();

		Query query2 = session
				.createQuery("select o from CourseAndMaterialVersion o where o.schoolCode in (:alist)");
		query2.setParameterList("alist", list);
		List<CourseAndMaterialVersion> cams = query2.list();

		ArrayList<Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>> arrayList = new ArrayList<Map.Entry<String, Map.Entry<String, Map.Entry<String, String>>>>();

		for (SchoolInfo sc : schoolInfos) {
			ArrayList<Map.Entry<String, Map.Entry<String, String>>> subList = new ArrayList<Map.Entry<String, Map.Entry<String, String>>>();

			for (CourseInfo c : cs) {
				ArrayList<Map.Entry<String, String>> subList2 = new ArrayList<Map.Entry<String, String>>();

				for (CourseAndMaterialVersion cam : cams) {
					if (cam.getCourseCode().equals(c.getCourseCode())
							&& cam.getSchoolCode().equals(sc.getCode())) {
						int tCode = cam.getTeachingMaterialVersionCode();

						for (TeachingMaterialVersionInfo t : ts) {
							if (t.getCode() == tCode) {
								subList2.add(new AbstractMap.SimpleEntry(String
										.valueOf(t.getCode()), t.getName()));
							}
						}
					}
				}
				subList.add(new AbstractMap.SimpleEntry(c.getCourseCode(),
						subList2));
			}

			arrayList.add(new AbstractMap.SimpleEntry(sc.getCode(), subList));
		}

		return JSONArray.fromObject(arrayList).toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String doInitCourseCode(Session session) {
		if (cs == null) {
			Query query2 = session.createQuery("select o from CourseInfo o");
			cs = query2.list();
		}

		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();

		for (CourseInfo cInfo : cs) {
			arrayList.add(new AbstractMap.SimpleEntry(cInfo.getCourseCode(),
					cInfo.getName()));
		}
		return JSONArray.fromObject(arrayList).toString();
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String doInitSchoolCode(String schoolCode, Session session)
			throws IOException {
		if (schoolInfos == null) {
			ArrayList<String> cList = new ArrayList<String>();
			cList.add(schoolCode);
			cList.add(schoolCode.substring(0, 6) + "0000");
			cList.add(schoolCode.substring(0, 6) + "0001");
			cList.add(schoolCode.substring(0, 5) + "00000");
			cList.add(schoolCode.substring(0, 5) + "00001");
			Query query = session
					.createQuery("select o from SchoolInfo o where o.code in (:alist)");
			query.setParameterList("alist", cList);
			schoolInfos = query.list();
		}

		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>();

		for (SchoolInfo schoolInfo : schoolInfos) {
			arrayList.add(new AbstractMap.SimpleEntry(schoolInfo.getCode(),
					schoolInfo.getName()));
		}
		return JSONArray.fromObject(arrayList).toString();
	}
}
