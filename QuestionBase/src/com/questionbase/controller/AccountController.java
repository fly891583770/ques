package com.questionbase.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.questionbase.bean.LoginForm;
import com.questionbase.exception.QuestionBaseException;
import com.questionbase.logic.Person;
import com.questionbase.logic.PersonUtil;
import com.questionbase.logic.person.Director;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;

@Controller
@RequestMapping("/account")
public class AccountController {

	@RequestMapping("/login")
	public String login(@ModelAttribute LoginForm form, Model model) {
		// Person person = (Person)session.getAttribute("LoginUser");
		Person person = null;
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		session.setAttribute("type", "");
		if (person == null) {
			try {
				person = PersonUtil.getInstance().Login(form.getUsername(),
						form.getPassword());
			} catch (QuestionBaseException e) {
				// e.printStackTrace();
			}
		}

		if (person != null) {
			// 家长
			if (person instanceof Parent) {
				Parent p = (Parent) person;
				model.addAttribute("user", p);
				model.addAttribute("type", "家长");
				model.addAttribute("id", p.getFamilyCode());
				session.setAttribute("type", "家长");
			}
			// 管理员
			else if (person instanceof SchoolAdmin) {
				SchoolAdmin t = (SchoolAdmin) person;
				model.addAttribute("user", t);
				model.addAttribute("type", "管理员");
				model.addAttribute("id", t.getCode());
				session.setAttribute("type", "管理员");
			}
			// 主任
			else if (person instanceof Director) {
				Director t = (Director) person;
				model.addAttribute("user", t);
				model.addAttribute("type", "主任");
				model.addAttribute("id", t.getTeacherCode());
				session.setAttribute("type", "主任");
			}
			// 老师
			else if (person instanceof Teacher) {
				Teacher t = (Teacher) person;
				model.addAttribute("user", t);
				model.addAttribute("type", "老师");
				model.addAttribute("id", t.getTeacherCode());
				session.setAttribute("type", "老师");
			}
			// 学士
			else {
				Student s = (Student) person;
				model.addAttribute("user", s);
				model.addAttribute("type", "学生");
				model.addAttribute("id", s.getCode());
				session.setAttribute("type", "学生");
			}
			
			session.setAttribute("LoginUser", person);

			return "common/top";
		} else {
			return "common/error";
		}
	}

}
