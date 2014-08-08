package com.questionbase.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionStatus;

public class JiaocaibanbenTag extends TagSupport {

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
					value = q.getTeachingMaterialVersion();

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

	private void doInit(String schoolCode) throws IOException {

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

		if (value != null && !value.equals("")) {
			sb.append("value=\"" + value + "\" ");
		}
		sb.append("name=\"" + name + "\" id=\"" + id + "\">");
		sb.append("</select>");

		pageContext.getOut().print(sb.toString());

	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
