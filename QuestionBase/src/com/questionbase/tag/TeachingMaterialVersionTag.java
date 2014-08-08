package com.questionbase.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.hibernate.Query;
import org.hibernate.Session;

import com.questionbase.hbm.TeachingMaterialVersionInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.SchoolAdmin;

public class TeachingMaterialVersionTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {

		try {

			HttpServletRequest req = (HttpServletRequest) pageContext
					.getRequest();
			SchoolAdmin t = (SchoolAdmin) req.getSession().getAttribute(
					"LoginUser");
			String schoolCode = t.getSchoolCode();

			Session session = QuestionBaseFactory.GetInstance()
					.getSessionFactory().openSession();

			Query query = session
					.createQuery("select o from TeachingMaterialVersionInfo o where o.organization =?");
			query.setParameter(0, schoolCode);
			List<TeachingMaterialVersionInfo> list = query.list();

			pageContext.getOut().print("<table class=\"showtable\" id=\"teachingMaterialVersionTable\">");
			for (TeachingMaterialVersionInfo tInfo : list) {
				pageContext.getOut().print("<tr>");
				pageContext.getOut().print("<td>");
				pageContext.getOut().print(
						"<font color=\"#0000FF\">" + tInfo.getName()
								+ "</font>");
				pageContext.getOut().print("</td>");
				pageContext.getOut().print("</tr>");
			}

			pageContext.getOut().print("<tr>");
			pageContext.getOut().print("<td>");
			pageContext
					.getOut()
					.print("<input type=\"text\"  id=\"teachingMaterialVersion\"/>");
			pageContext
					.getOut()
					.print("<input type=\"button\" id=\"teachingMaterialVersionButton\" value =\"追加\"/>");
			pageContext.getOut().print("</td>");
			pageContext.getOut().print("</tr>");

			pageContext.getOut().print("</table>");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
