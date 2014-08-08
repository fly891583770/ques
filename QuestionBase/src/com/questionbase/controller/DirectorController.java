package com.questionbase.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.questionbase.logic.person.Director;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.TestPaper;

@Controller
@RequestMapping("/director")
public class DirectorController extends TeacherController {

	@RequestMapping("/getapllyquestions")
	public String getApplyQuestions(Model model) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Director t = (Director) session.getAttribute("LoginUser");
		List<Question> list = t.getAllApllyQuestion();
		model.addAttribute("questions", list);

		return "director/apllyquestionlist";
	}
	
	@RequestMapping("/getapllypaper")
	public String getApplyPaper(Model model) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Director t = (Director) session.getAttribute("LoginUser");
		List<TestPaper> list = t.getAllApllyTestPaper();
		model.addAttribute("papers", list);

		return "director/apllypaperlist";
	}

	@RequestMapping("/agreeapllyquestion")
	public void agreeApllyQuestion(String id) {
		this.setApllyQuestionStatus("question",id, QuestionStatus.Accept);
	}
	
	@RequestMapping("/disagreeapllyquestion")
	public void disagreeApllyQuestion(String id) {
		this.setApllyQuestionStatus("question",id, QuestionStatus.Reversal);
	}
	
	@RequestMapping("/agreeapllypaper")
	public void agreeApllyPaper(String id) {
		this.setApllyQuestionStatus("paper",id, QuestionStatus.Accept);
	}
	
	@RequestMapping("/disagreeapllypaper")
	public void disagreeApllyPaper(String id) {
		this.setApllyQuestionStatus("paper",id, QuestionStatus.Reversal);
	}
	
	private void setApllyQuestionStatus(String type ,String id,QuestionStatus s) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Director t = (Director) session.getAttribute("LoginUser");
		
		List<String> apllylist = new ArrayList<String>();
		
		String[] qs = id.split(":");
		
		for (String temp : qs) {
			if(temp!=null&&temp.length()!=0)
			{
				apllylist.add(temp);
			}
		}
		if("paper".equalsIgnoreCase(type))
		{
			t.agreeApllyTestPaper(apllylist, s);
		}
		else//question
		{
			t.agreeApllyQuestion(apllylist , s);
		}
	}
	
}
