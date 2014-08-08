package com.questionbase.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionStatus;

public class LeixingTag extends TagSupport {

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
					value = q.getTypes();
			}
			Object user = req.getSession().getAttribute("LoginUser");
			if (user != null) {
				if (user instanceof Teacher) {
					t = (Teacher) user;
				}
			}
			doInit();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	private void doInit() throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<select ");

		if (disabled.equalsIgnoreCase("true")
				|| disabled.equalsIgnoreCase("disabled")) {
			sb.append(" disabled=\"disabled\" ");
		}

		if (disabled.equalsIgnoreCase("check")) {
			if (q != null && t != null) {
				if (!(q.getTeacherCode().equals(t.getTeacherCode()) && q
						.getStatus() != QuestionStatus.Accept)) {
					sb.append("disabled=\"disabled\" ");
				}
			}
		}

		sb.append(" name=\"" + name + "\" id=\"" + id + "\">");
		pageContext.getOut().print(sb.toString());
		pageContext.getOut().print("</select>");

	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
