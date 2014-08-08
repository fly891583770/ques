package com.questionbase.tag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.hibernate.Query;
import org.hibernate.Session;

import com.questionbase.common.Tool;
import com.questionbase.hbm.ClassInfo;
import com.questionbase.hbm.SchoolInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionStatus;

public class NianjiTag extends TagSupport {

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
				if(q==null)
					value=null;
				else
					value = q.getYear();

			}
			Object user = req.getSession().getAttribute("LoginUser");
			if (user != null) {
				if (user instanceof Student) {
					Student stu = (Student) user;
					String schoolCode = stu.getSchoolCode();
					doInit(schoolCode, stu.getClassCode());
				}
				if (user instanceof Parent) {
					Parent parent = (Parent) user;
					Student s = (Student) parent.getChild();
					String schoolCode = s.getSchoolCode();
					doInit(schoolCode, s.getClassCode());
				}

				if (user instanceof Teacher) {
					Teacher teacher = (Teacher) user;
					String schoolCode = teacher.getSchoolCode();
					t = (Teacher) user;
					doInit(schoolCode, "");
				}

				if (user instanceof SchoolAdmin) {
					SchoolAdmin admin = (SchoolAdmin) user;
					String schoolCode = admin.getSchoolCode();
					doInit(schoolCode, "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	private void doInit(String schoolCode, String classCode) throws IOException {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		Query query = session
				.createQuery("select o from SchoolInfo o where o.code =?");
		query.setParameter(0, schoolCode);
		SchoolInfo schoolInfo = (SchoolInfo) query.uniqueResult();

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
		
		sb.append("name=\"" + name + "\" id=\"" + id + "\">");
		
		ArrayList<String> result = new ArrayList<>();
		String code = "";

		if (schoolInfo == null
				|| (schoolInfo.getsXZ() == null && schoolInfo.getmXZ() == null && schoolInfo
						.gethXZ() == null)) {
			code = code + doInitNianji(6, "小学", "S", classCode, result);
			code = code + doInitNianji(4, "初中", "M", classCode, result);
			code = code + doInitNianji(3, "高中", "H", classCode, result);
		} else if (schoolInfo.getsXZ() != null) {
			int count = 0;
			try {
				count = Integer.parseInt(schoolInfo.getsXZ());
			} catch (Exception e) {
				count = 0;
			}
			code = code + doInitNianji(count, "小学", "S", classCode, result);

		} else if (schoolInfo.getmXZ() != null) {
			int count = 0;
			try {
				count = Integer.parseInt(schoolInfo.getmXZ());
			} catch (Exception e) {
				count = 0;
			}
			code = code + doInitNianji(count, "初中", "M", classCode, result);
		} else if (schoolInfo.gethXZ() != null) {
			int count = 0;
			try {
				count = Integer.parseInt(schoolInfo.gethXZ());
			} catch (Exception e) {
				count = 0;
			}
			code = code + doInitNianji(count, "高中", "H", classCode, result);
		}

		if (code.length() > 0) {
			code = code.substring(0, code.length() - 1);
		}

		result.add(0, "<option value=\"" + code + "\">全部学年</option>");
		
		pageContext.getOut().print(sb.toString());
		
		for (String s : result) {
			pageContext.getOut().print(s);
		}

		pageContext.getOut().print("</select>");

	}

	private String doInitNianji(int index, String firstName, String firstCode,
			String classCode, ArrayList<String> result) throws IOException {
		String code = "";

		for (int i = 1; i <= index; i++) {
			if (value == null) {
				if (GetYearByClassCode(classCode) != i) {
					result.add("<option value=\"" + firstCode + i + "\">"
							+ firstName + Tool.getBigNmuberFromNumber(i) + "年级"
							+ "</option>");
				}

				else {
					result.add("<option selected=\"selected\" value=\""
							+ firstCode + i + "\">" + firstName
							+ Tool.getBigNmuberFromNumber(i) + "年级"
							+ "</option>");
				}
				code = code + (firstCode + i) + ",";
			} else {
				if (!(firstCode + i).equals(value)) {
					result.add("<option value=\"" + firstCode + i + "\">"
							+ firstName + Tool.getBigNmuberFromNumber(i) + "年级"
							+ "</option>");
				} else {
					result.add("<option selected=\"selected\" value=\""
							+ firstCode + i + "\">" + firstName
							+ Tool.getBigNmuberFromNumber(i) + "年级"
							+ "</option>");
				}
				code = code + (firstCode + i) + ",";
			}

		}
		return code;
	}

	private int GetYearByClassCode(String classCode) {
		if (classCode == null || classCode.equals("")) {
			return -1;
		}

		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		Integer classYear = (Integer) req.getSession().getAttribute("ClassYear");

		//Object classYear = req.getSession().getAttribute("ClassYear");
		if (classYear == null) {
			Session session = QuestionBaseFactory.GetInstance()
					.getSessionFactory().openSession();
			Query query = session
					.createQuery("select o from ClassInfo o where o.code =?");
			query.setParameter(0, classCode);
			ClassInfo c = (ClassInfo) query.uniqueResult();
			if (c == null) {
				return -1;
			}
			int count = -1;
			try {
				count = Integer.parseInt(c.getcYear());
			} catch (Exception e) {
				count = -1;
			}

			req.getSession().setAttribute("ClassYear", count);

			return count;
		}

		//return (int) classYear;
		return classYear;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
