package com.questionbase.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.questionbase.question.Question;

public class ChutirenuyanTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String disabled = "false";
	private String value;

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
				Question x = (Question) req.getAttribute(value.replace("{", "")
						.replace("}", "").replace("$", "").split("[.]")[0]);
				if(x==null)
					value=null;
				else
					value = x.getTeacherName();

			}

			doInit();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	private void doInit() throws IOException {

		if (disabled.equalsIgnoreCase("true")
				|| disabled.equalsIgnoreCase("disabled")) {
			pageContext.getOut().print(
					"<input type=\"text\" disabled=\"disabled\" name=\"" + name
							+ "\" id=\"" + id + "\"");
		} else {
			pageContext.getOut().print(
					"<input type=\"text\" name=\"" + name + "\" id=\"" + id
							+ "\"");
		}

		if (value != null)
			pageContext.getOut().print(" value=\"" + value +"\"");
		

		pageContext.getOut().print("/>");

	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
