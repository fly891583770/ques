package com.questionbase.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.questionbase.hbm.SectionPoint;
import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.QuestionStatus;

public class ZhangjieTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String disabled = "false";
	private String value;
	private Question q;
	private Teacher t;

	private List<SectionPoint> ks;

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
					this.ks = QuestionManager.GetInstance()
							.GetSectionPointByQuestionCode(q.getCode());

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
		sb.append("<input type=\"hidden\"	name=\"" + name + "\" id=\"" + id
				+ "\" />");
		sb.append("<br/>");
		sb.append(" <textarea id=\""
				+ id
				+ "Area\" name=\""
				+ name
				+ "Area\" readonly=\"readonly\" cols=\"21\" rows=\"5\" style=\"font-size: 12px; color: #F00\">");
		if (ks != null) {
			for (SectionPoint k : ks) {
				sb.append(k.getCaption());
				sb.append("\r\n");
			}
		}
		sb.append("</textarea>");
		sb.append("<br/>");

		boolean flag = false;

		if (!disabled.equalsIgnoreCase("true")
				|| disabled.equalsIgnoreCase("disabled")) {
			flag = true;
		}

		if (disabled.equalsIgnoreCase("check")) {
			if (q != null && t != null) {
				if (!(q.getTeacherCode().equals(t.getTeacherCode()) && q
						.getStatus() != QuestionStatus.Submited)) {
					flag = false;
				}
			}
		}

		if (flag) {
			sb.append("<input type=\"button\" id=\"zhangjieButton"
					+ id.replaceAll("zhangjie", "")
					+ "\" class=\"zhangjieButton\" value=\"展开\" />");
		}

		pageContext.getOut().print(sb.toString());

	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
