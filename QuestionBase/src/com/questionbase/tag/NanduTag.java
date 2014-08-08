package com.questionbase.tag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionStatus;

public class NanduTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String disabled = "false";
	private String value;
	private Question q;
	private Teacher t;

	private String[] nanduType = new String[] { "简单", "中等", "困难" };

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
					value = q.getDifficulty();
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

		String code = "";

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

		ArrayList<String> result = new ArrayList<>();

		for (int i = 0; i < nanduType.length; i++) {
			if (value != null && value.equals(String.valueOf(i + 1))) {
				result.add("<option selected=\"selected\" value=\"" + (i + 1)
						+ "\">" + nanduType[i] + "</option>");
			} else {
				result.add("<option value=\"" + (i + 1) + "\">" + nanduType[i]
						+ "</option>");
			}

			code = code + String.valueOf((i + 1)) + ",";

		}

		if (code.length() > 0) {
			code = code.substring(0, code.length() - 1);
		}
		result.add(0, "<option value=\"" + code + "\">全部难度</option>");

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
