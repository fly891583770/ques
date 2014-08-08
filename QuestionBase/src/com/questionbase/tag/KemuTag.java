package com.questionbase.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.hibernate.Query;
import org.hibernate.Session;

import com.questionbase.hbm.CourseInfo;
import com.questionbase.hbm.SchoolDetail;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionStatus;

public class KemuTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String disabled = "false";
	private String value;
	private Question q;
	private Teacher t;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest req = (HttpServletRequest) pageContext
					.getRequest();

			if (value != null) {
				q = (Question) req.getAttribute(value.replace("{", "")
						.replace("}", "").replace("$", "").split("[.]")[0]);
				if (q == null)
					value = null;
				else
					value = q.getCourseCode();
			}

			Object user = req.getSession().getAttribute("LoginUser");
			if (user != null) {
				if (user instanceof Student) {
					Student stu = (Student) user;
					String schoolCode = stu.getSchoolCode();
					doInit(schoolCode);
				}

				if (user instanceof Parent) {
					Parent parent = (Parent) user;
					Student s = (Student) parent.getChild();
					String schoolCode = s.getSchoolCode();
					doInit(schoolCode);
				}

				if (user instanceof Teacher) {
					Teacher teacher = (Teacher) user;
					String schoolCode = teacher.getSchoolCode();
					t = (Teacher) user;
					doInit(schoolCode);
				}

				if (user instanceof SchoolAdmin) {
					SchoolAdmin admin = (SchoolAdmin) user;
					String schoolCode = admin.getSchoolCode();
					doInit(schoolCode);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	@SuppressWarnings("unchecked")
	private void doInit(String schoolCode) throws IOException {

		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from SchoolDetail o where o.code =?");
		query.setParameter(0, schoolCode);
		SchoolDetail schoolInfo = (SchoolDetail) query.uniqueResult();

		StringBuffer sb = new StringBuffer();
		sb.append("<select ");

		if (disabled.equalsIgnoreCase("true")
				|| disabled.equalsIgnoreCase("disabled")) {
			sb.append("disabled=\"disabled\" ");

		}

		if (disabled.equalsIgnoreCase("check")) {
			if (q != null && t != null) {
				if (!(q.getTeacherCode().equals(t.getTeacherCode()) && q
						.getStatus() != QuestionStatus.Accept)) {
					sb.append("disabled=\"disabled\" ");
				}
			}
		}

		sb.append(" name=\"" + name + "\" id=\"" + id + "\" class=\"kemu\">");

		ArrayList<String> result = new ArrayList<>();
		String code = "";

		if (schoolInfo == null || schoolInfo.getCourseCode() == null
				|| schoolInfo.getCourseCode().equals("")) {
			Query query2 = session.createQuery("select o from CourseInfo o");

			List<CourseInfo> cs = query2.list();

			for (CourseInfo c : cs) {
				if (value == null) {
					result.add("<option value=\"" + c.getCourseCode() + "\">"
							+ c.getName() + "</option>");
				} else {
					if (c.getCourseCode().equals(value)) {
						result.add("<option selected=\"selected\" value=\""
								+ c.getCourseCode() + "\">" + c.getName()
								+ "</option>");
					} else {
						result.add("<option value=\"" + c.getCourseCode()
								+ "\">" + c.getName() + "</option>");
					}
				}

				code = code + String.valueOf(c.getCourseCode()) + ",";
			}
		} else {
			Query query2 = session
					.createQuery("select o from CourseInfo o where o.code in (:alist)");
			query2.setParameterList("alist",
					schoolInfo.getCourseCode().split(","));
			List<CourseInfo> cs = query2.list();

			for (CourseInfo c : cs) {
				if (value == null) {
					result.add("<option value=\"" + c.getCourseCode() + "\">"
							+ c.getName() + "</option>");
				} else {
					if (c.getCourseCode().equals(value)) {
						result.add("<option selected=\"selected\" value=\""
								+ c.getCourseCode() + "\">" + c.getName()
								+ "</option>");
					} else {
						result.add("<option value=\"" + c.getCourseCode()
								+ "\">" + c.getName() + "</option>");
					}
				}
				code = code + String.valueOf(c.getCourseCode()) + ",";
			}
		}

		if (code.length() > 0) {
			code = code.substring(0, code.length() - 1);
		}

		result.add(0, "<option value=\"" + code + "\">全部科目</option>");

		pageContext.getOut().print(sb.toString());

		for (String s : result) {
			pageContext.getOut().print(s);
		}

		pageContext.getOut().print("</select>");

	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
