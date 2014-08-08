package com.questionbase.logic;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.questionbase.exception.QuestionBaseException;
import com.questionbase.hbm.DirectorRoleInfo;
import com.questionbase.hbm.FamilyInfo;
import com.questionbase.hbm.StudentInfo;
import com.questionbase.hbm.SystemUserInfo;
import com.questionbase.hbm.TeacherInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.Director;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;

public class PersonUtil {

	private static volatile PersonUtil instance;

	private PersonUtil() {

	}

	public static PersonUtil getInstance() {
		if (instance == null) {

			synchronized (QuestionBaseFactory.class) {
				if (instance == null) {
					instance = new PersonUtil();
				}
			}
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public Person getPerson(String sCode, Session session, String username,
			String schoolCode) throws QuestionBaseException {
		Criteria c = session.createCriteria(DirectorRoleInfo.class);
		c.add(Restrictions.eq("account", username));

		String role = "";

		List<DirectorRoleInfo> dr = c.list();
		for (DirectorRoleInfo info : dr) {
			if (info.getName().equals("教研室主任（组长）")||info.getName().equals("年级主任（组长）")) {
				role = "Director";
			}
			if (info.getName().equals("学校管理员")) {
				role = "SchoolAdmin";
			}
		}

		if (role.equals("SchoolAdmin")) {
			return new SchoolAdmin(username, schoolCode);
		}

		List<StudentInfo> stu = session.createCriteria(StudentInfo.class)
				.add(Restrictions.eq("peopleCode", sCode)).list();
		if (stu.size() > 0) {
			return new Student(stu.get(0).getStudentCode());
		}

		List<FamilyInfo> ps = session.createCriteria(FamilyInfo.class)
				.add(Restrictions.eq("familyCode", sCode)).list();
		if (ps.size() > 0) {
			return new Parent(ps.get(0).getFamilyCode());
		}

		List<TeacherInfo> ts = session.createCriteria(TeacherInfo.class)
				.add(Restrictions.eq("peopleCode", sCode)).list();
		if (ts.size() > 0) {
			if (role.equals("Director"))
				return new Director(ts.get(0).getTeacherCode());
			return new Teacher(ts.get(0).getTeacherCode());
		}

		throw new QuestionBaseException(sCode + "这个人员编号不存在");
	}

	@SuppressWarnings("unchecked")
	public Person Login(String username, String password)
			throws QuestionBaseException {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		List<SystemUserInfo> infos = session
				.createCriteria(SystemUserInfo.class)
				.add(Restrictions.eq("account", username)).list();
		if (infos.size() == 0) {
			throw new QuestionBaseException(username + "这个用户名不存在");
		}

		SystemUserInfo user = infos.get(0);
		if (user.getPassword() == null) {
			return getPerson(user.getPeopleCode(), session, username,
					user.getSchoolCode());
		}

		if (password.equals(Encipher.DecodePasswd(user.getPassword()))) {
			return getPerson(user.getPeopleCode(), session, username,
					user.getSchoolCode());
		}

		throw new QuestionBaseException(username + "的密碼輸入錯誤");
	}

}
