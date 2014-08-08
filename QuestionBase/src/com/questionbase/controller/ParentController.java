package com.questionbase.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.questionbase.bean.SearchQuestionForm;
import com.questionbase.exception.QuestionBaseException;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.Student;
import com.questionbase.question.ExerciseBook;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.SelectQuestionResult;

@Controller
@RequestMapping("/parent")
public class ParentController {

	@RequestMapping("/searchquestion")
	public String searchquestion() {
		return "parent/searchquestion";
	}
	
	@RequestMapping("/questionlist")
	public String questionlist(@ModelAttribute SearchQuestionForm form,
			Model model, @RequestParam(value = "page", required = false)Integer page) {
		Hashtable<String, String> table = new Hashtable<String, String>();
		String nowpage = (StringUtils.isEmpty(form.getPagehao()) ? "0" : form
				.getPagehao());
		table.put("Page", nowpage);
		table.put("CourseCode", form.getKemu());
		table.put("TeachingMaterialVersion", form.getJiaocaibanben());
		table.put("Year", form.getNianji());
		table.put("Difficulty", form.getNandu());
		table.put("QuestionTypes", form.getTixing());
		table.put("Types", form.getLeixing());
		table.put("Organization", form.getJigoumingcheng());
		table.put("TeacherName", form.getChutirenyuan());
		table.put("KnowledgePoint", form.getZhishidian());
		table.put("SectionPoint", form.getZhangjie());
		table.put("Status", String.valueOf(QuestionStatus.Accept.ordinal()));
		SelectQuestionResult qsResult = QuestionManager.GetInstance()
				.SelectQuestion(table);

		model.addAttribute("currentpage", nowpage);
		model.addAttribute("totalrecord", qsResult.getPageCount());
		model.addAttribute("questions", qsResult.getQuestions());

		return "parent/questionlist";
	}

	@RequestMapping("/editquestion")
	public String editquestion(Model model, String id) {
		Question q1 = QuestionManager.GetInstance().GetQuestion(
				Integer.parseInt(id));
		model.addAttribute("questionname", q1.getName());
		model.addAttribute("answer", q1.getAnswer());
		model.addAttribute("question", q1);
		return "parent/editquestion";
	}

	@RequestMapping("/createexercisebook")
	public String CreateExerciseBook(Model model, String name, String questions)
			throws QuestionBaseException {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Student s = (Student) session.getAttribute("LoginUser");
		String[] q = questions.split(",");
		List<Integer> result = new ArrayList<Integer>();
		for (String string : q) {
			if (!StringUtils.isEmpty(string)) {
				result.add(Integer.parseInt(string));
			}
		}
		s.CreateExerciseBook(name, result);

		return exerciseBook(model);
	}

	@RequestMapping("/exercisebook")
	public String exerciseBook(Model model)
			throws QuestionBaseException {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Parent p = (Parent) session.getAttribute("LoginUser");
		Student s = (Student) p.getChild();
		model.addAttribute("name", p.getName());
		model.addAttribute("exercisebooks", s.getExerciseBooks());
		return "parent/exercisebook";
	}

	@RequestMapping("/delexercisebook")
	public String delexercisebook() {
		return "parent/exercisebook";
	}

	@RequestMapping("/exercisebookquestionlist")
	public String exercisebookquestionlist(Model model, String id)
			throws CloneNotSupportedException {

		ExerciseBook book = new ExerciseBook(id);
		List<Question> qs = book.getQuestions();
		model.addAttribute("questions", qs);
		return "parent/exercisebookquestionlist";
	}

	@RequestMapping("/getapplyquestion")
	public String getApplyQuestion(Model model) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Parent p = (Parent) session.getAttribute("LoginUser");
		Hashtable<Student, List<Question>> hash = p.getChildApplyQuestions();

		model.addAttribute("hash", hash);

		return "parent/apllyanswerlist";
	}

	@RequestMapping("/approvedquestion")
	public void approvedQuestion(Model model, String id) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Parent t = (Parent) session.getAttribute("LoginUser");

		String[] qs = id.split(":");

		for (String temp : qs) {
			if (temp != null && temp.length() != 0 && temp.contains("|")) {
				String[] te = temp.split("[|]");
				t.ApprovedQuestion(te[1]);
			}
		}
	}
	
	@RequestMapping("/parentbanner")
	public String parentbanner() {
		return "parent/parentbanner";
	}
}
