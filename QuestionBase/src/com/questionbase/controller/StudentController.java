package com.questionbase.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.questionbase.bean.SearchPaperForm;
import com.questionbase.bean.SearchQuestionForm;
import com.questionbase.hbm.StudentQuestionStatus;
import com.questionbase.logic.person.Student;
import com.questionbase.question.ExerciseBook;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.S_Question;
import com.questionbase.question.SelectQuestionResult;
import com.questionbase.question.SelectTestPaperResult;
import com.questionbase.question.TestPaper;
import com.questionbase.question.TestPaperQuestionDetail;

@Controller
@RequestMapping("/student")
public class StudentController {

	@RequestMapping("/zhangjie")
	public String zhangjie() {
		return "common/zhangjie";
	}
	
	@RequestMapping("/zsd")
	public String zsd() {
		return "common/zsd";
	}

	@RequestMapping("/searchquestion")
	public String searchquestion() {

		return "student/searchquestion";
	}

	@RequestMapping("/searchpaper")
	public String searchpaper() {
		return "student/searchpaper";
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

		return "student/questionlist";
	}

	@RequestMapping("/paperlist")
	public String paperlist(@ModelAttribute SearchPaperForm form, Model model,
			@RequestParam(value = "page", required = false)Integer page, @RequestParam(value = "status", required = false)String status) {
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

		SelectTestPaperResult qsResult = QuestionManager.GetInstance()
				.SelectTestPaper(table);

		model.addAttribute("currentpage", nowpage);
		model.addAttribute("totalrecord", qsResult.getPageCount());
		model.addAttribute("papers", qsResult.getTestPapers());

		return "student/paperlist";
	}

	@RequestMapping("/paperview")
	public String paperview(Model model, String code) {
		TestPaper tp = new TestPaper(Integer.parseInt(code));

		model.addAttribute("name", tp.getName());

		List<TestPaperQuestionDetail> temlist = tp.getDetail().getResult();

		List<List<TestPaperQuestionDetail>> list = this.makeshowPaper(temlist);
		list = this.showpaixu(list);
		model.addAttribute("qlist", list);
		model.addAttribute("isTeacher", false);

		return "teacher/paperview";
	}

	private List<List<TestPaperQuestionDetail>> showpaixu(
			List<List<TestPaperQuestionDetail>> list) {

		ComparatorTestPaperQuestionDetailList comparator = new ComparatorTestPaperQuestionDetailList();
		Collections.sort(list, comparator);

		return list;
	}

	// 试卷详细显示用
	private List<List<TestPaperQuestionDetail>> makeshowPaper(
			List<TestPaperQuestionDetail> details) {

		List<List<TestPaperQuestionDetail>> result = new ArrayList<List<TestPaperQuestionDetail>>();

		// 按题型分组
		for (TestPaperQuestionDetail question : details) {
			if (result.size() != 0) {
				for (int i = 0; i < result.size(); i++) {
					List<TestPaperQuestionDetail> qlist = result.get(i);
					Question q= qlist.get(0).getQuestion();
					if(q==null){
						continue;
					}
					
					String qtype = q.getQuestionTypes();

					if (qtype.equals(question.getQuestion().getQuestionTypes())) {
						qlist.add(question);
						break;
					} else {
						if (i == result.size() - 1) {
							ArrayList<TestPaperQuestionDetail> newlist = new ArrayList<TestPaperQuestionDetail>();
							newlist.add(question);
							result.add(newlist);
							break;
						} else {
							continue;
						}
					}
				}
			} else {
				ArrayList<TestPaperQuestionDetail> newlist = new ArrayList<TestPaperQuestionDetail>();
				newlist.add(question);
				result.add(newlist);
			}
		}
		return result;
	}

	@RequestMapping("/editquestion")
	public String editquestion(Model model, String id) {
		// Question q1 =
		// QuestionManager.GetInstance().GetQuestion(Integer.parseInt(id));

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Student s = (Student) session.getAttribute("LoginUser");

		S_Question q = s.getQuestion(Integer.parseInt(id));
		boolean status = false;
		if (q.getGuard() == StudentQuestionStatus.Approved) {
			status = true;
		}

		model.addAttribute("status", status);
		model.addAttribute("sid", s.getCode());
		model.addAttribute("question", q);
		return "student/editquestion";
	}

	@RequestMapping("/askforanswer")
	public void AskForAnswer(String qid) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Student s = (Student) session.getAttribute("LoginUser");
		s.ApplyAnswer(Integer.parseInt(qid));
	}

	@RequestMapping("/createexercisebook")
	public String CreateExerciseBook(Model model, String name, String questions)
			throws UnsupportedEncodingException {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		name = new String(name.getBytes("ISO8859-1"), "UTF-8");
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
	public String exerciseBook(Model model) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Student s = (Student) session.getAttribute("LoginUser");
		model.addAttribute("name", s.getName());
		model.addAttribute("exercisebooks", s.getExerciseBooks());
		return "student/exercisebook";
	}

	@RequestMapping("/delexercisebook")
	public String delexercisebook(Model model, String id) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Student s = (Student) session.getAttribute("LoginUser");
		s.DeleteExerciseBook(Integer.parseInt(id));
		model.addAttribute("name", s.getName());
		model.addAttribute("exercisebooks", s.getExerciseBooks());
		return "student/exercisebook";
	}

	@RequestMapping("/exercisebookquestionlist")
	public String exercisebookquestionlist(Model model, String id)
			throws CloneNotSupportedException {
		ExerciseBook book = new ExerciseBook(id);
		List<Question> qs = book.getQuestions();
		model.addAttribute("exercisebookname", book.getName());
		model.addAttribute("questions", qs);
		return "student/exercisebookquestionlist";
	}

	@RequestMapping("/studentbanner")
	public String studentbanner() {
		return "student/studentbanner";
	}
}
