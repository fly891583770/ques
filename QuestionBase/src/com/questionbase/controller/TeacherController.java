package com.questionbase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xml.sax.SAXException;

import com.questionbase.bean.BatchAddQuestionForm;
import com.questionbase.bean.SearchPaperForm;
import com.questionbase.bean.SearchQuestionForm;
import com.questionbase.hbm.KnowledgePointAndQuestion;
import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.SectionAndQuestion;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.SelectQuestionResult;
import com.questionbase.question.SelectTestPaperResult;
import com.questionbase.question.TestPaper;
import com.questionbase.question.TestPaperQuestionDetail;
import com.questionbase.word.WordStreamToDB;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@RequestMapping("/searchquestion")
	public String searchquestion() {
		return "teacher/searchquestion";
	}

	@RequestMapping("/searchpaper")
	public String searchpaper() {
		return "teacher/searchpaper";
	}

	@RequestMapping("/questionlist")
	public String searchquestionlist(@ModelAttribute SearchQuestionForm form,
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "status", required = false) String status) {
		return this.questionlist(form, model, page,
				String.valueOf(QuestionStatus.Accept.ordinal()));
	}

	private String questionlist(@ModelAttribute SearchQuestionForm form,
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "status", required = false) String status) {
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
		if (status != null) {
			table.put("Status", status);
		}

		SelectQuestionResult qsResult = QuestionManager.GetInstance()
				.SelectQuestion(table);

		model.addAttribute("currentpage", nowpage);
		model.addAttribute("totalrecord", qsResult.getPageCount());
		model.addAttribute("questions", qsResult.getQuestions());

		return "teacher/questionlist";
	}

	@RequestMapping("/paperlist")
	public String paperlist(@ModelAttribute SearchPaperForm form, Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "status", required = false) String status) {
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
		if (status != null) {
			table.put("Status", status);
		}else{
			//table.put("Status", String.valueOf(QuestionStatus.Accept.ordinal()));  // add by zm
		}

		SelectTestPaperResult qsResult = QuestionManager.GetInstance()
				.SelectTestPaper(table);

		model.addAttribute("currentpage", nowpage);
		model.addAttribute("totalrecord", qsResult.getPageCount());
		model.addAttribute("papers", qsResult.getTestPapers());

		return "teacher/paperlist";
	}

	@RequestMapping("/creatpaper")
	public String creatpaper() {
		return "teacher/zujuan";
	}

	@RequestMapping("/zujuantilist")
	public String zujuantilist(@ModelAttribute SearchQuestionForm form,
			Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "status", required = false) String status) {
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
		if (status != null) {
			table.put("Status", status);
		}

		SelectQuestionResult qsResult = QuestionManager.GetInstance()
				.SelectQuestion(table);

		model.addAttribute("currentpage", nowpage);
		model.addAttribute("totalrecord", qsResult.getPageCount());
		model.addAttribute("questions", qsResult.getQuestions());

		return "teacher/zujuantilist";
	}

	@RequestMapping("/editquestion")
	public String editquestion(Model model, String id) {
		model.addAttribute("question", QuestionManager.GetInstance()
				.GetQuestion(Integer.parseInt(id)));
		return "teacher/editquestion";
	}

	@RequestMapping("/submitquestionlist")
	public String submitquestionlist(@ModelAttribute SearchQuestionForm form,
			Model model) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");
		model.addAttribute("name", t.getName());
		return "teacher/submitquestionlist";
	}

	@RequestMapping("/submitpaperlist")
	public String submitpaperlist(@ModelAttribute SearchPaperForm form,
			Model model) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");
		model.addAttribute("name", t.getName());
		return "teacher/submitpaperlist";
	}

	@RequestMapping("/searchsubmitquestionlist")
	public String searchsubmitquestionlist(
			@ModelAttribute SearchQuestionForm form, Model model,
			@RequestParam(value = "page", required = false) Integer page) {
		return this.questionlist(
				form,
				model,
				page,
				String.valueOf(QuestionStatus.NotSubmited.ordinal()) + ","
						+ String.valueOf(QuestionStatus.Submited.ordinal())
						+ ","
						+ String.valueOf(QuestionStatus.Reversal.ordinal()));
	}

	@RequestMapping("/searchsubmitpaperlist")
	public String searchsubmitpaperlist(@ModelAttribute SearchPaperForm form,
			Model model) {

		String nowpage = (StringUtils.isEmpty(form.getPagehao()) ? "0" : form
				.getPagehao());

		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("CourseCode", form.getKemu());
		table.put("TeachingMaterialVersion", form.getJiaocaibanben());
		table.put("Year", form.getNianji());
		table.put("TestPaerName", form.getShijuanmingcheng());
		// table.put("Difficulty", "2");
		// table.put("QuestionTypes", "5");
		// table.put("Types", "1");
		table.put("Organization", form.getJigoumingcheng());
		table.put("TeacherName", form.getChutirenyuan());
		table.put("KnowledgePoint", form.getZhishidian());
		table.put("SectionPoint", form.getZhangjie());
		table.put("Page", nowpage);
		table.put("Status", "0,1,2");
		SelectTestPaperResult qs = QuestionManager.GetInstance()
				.SelectTestPaper(table);

		model.addAttribute("currentpage", nowpage);
		model.addAttribute("totalrecord", qs.getPageCount());
		model.addAttribute("papers", qs.getTestPapers());

		return "teacher/paperlist";
	}

	@RequestMapping("/submitquestion")
	public String submitquestion(@ModelAttribute SearchQuestionForm form,
			String id) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");

		String[] qs = id.split(",");

		for (String temp : qs) {
			if (temp != null && temp.length() != 0) {
				t.SubmitQuesiont(Integer.parseInt(temp));
			}
		}

		return "teacher/submitquestionlist";
	}

	@RequestMapping("/submitpaper")
	public String submitpaper(@ModelAttribute SearchPaperForm form, String id) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");

		String[] qs = id.split(",");

		for (String temp : qs) {
			if (temp != null && temp.length() != 0) {
				t.SubmitTestPaper(Integer.parseInt(temp));
			}
		}

		return "teacher/submitpaperlist";
	}

	@RequestMapping("/zujuanyulan")
	public String zujuanyulan(@ModelAttribute SearchPaperForm form, Model model) {
		List<List<Question>> list = this.makePaper(form.getXuanzhongti());

		list = this.paixu(list);

		model.addAttribute("qlist", list);
		model.addAttribute("shijuanmingcheng", form.getShijuanmingcheng());
		return "teacher/shijuanyulan";
	}

	private List<List<Question>> paixu(List<List<Question>> list) {

		ComparatorQuestionList comparator = new ComparatorQuestionList();
		Collections.sort(list, comparator);

		return list;
	}

	private List<List<TestPaperQuestionDetail>> showpaixu(
			List<List<TestPaperQuestionDetail>> list) {

		ComparatorTestPaperQuestionDetailList comparator = new ComparatorTestPaperQuestionDetailList();
		Collections.sort(list, comparator);

		return list;
	}

	// 组卷显示用
	private List<List<Question>> makePaper(String questions) {
		String[] qs = questions.split(",");
		List<Integer> ql = new ArrayList<>();
		for (int i = 0; i < qs.length; i++) {
			if (qs[i] != null && qs[i].length() != 0) {
				ql.add(Integer.parseInt(qs[i]));
			}
		}

		List<List<Question>> result = new ArrayList<List<Question>>();
		// 拿到所有的题
		List<Question> temAll = QuestionManager.GetInstance()
				.CreateQuestionByQuestionCode(ql);

		// 按题型分组
		for (Question question : temAll) {
			if (result.size() != 0) {
				for (int i = 0; i < result.size(); i++) {
					List<Question> qlist = result.get(i);
					String qtype = qlist.get(0).getQuestionTypes();
					if (qtype.equals(question.getQuestionTypes())) {
						qlist.add(question);
						break;
					} else {
						if (i == result.size() - 1) {
							ArrayList<Question> newlist = new ArrayList<Question>();
							newlist.add(question);
							result.add(newlist);
							break;
						} else {
							continue;
						}
					}
				}
			} else {
				ArrayList<Question> newlist = new ArrayList<Question>();
				newlist.add(question);
				result.add(newlist);
			}
		}
		return result;
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
					Question q = qlist.get(0).getQuestion();
					if (q == null) {
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

	@RequestMapping("/savepaper")
	public String savepaper(Model model, String ti, String fen, String name,
			String wordPaperId) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher p2 = (Teacher) session.getAttribute("LoginUser");

		ArrayList<TestPaperQuestionDetail> details = new ArrayList<TestPaperQuestionDetail>();

		String[] ts = ti.split(",");
		String[] fs = fen.split(",");

		for (int i = 0; i < ts.length; i++) {
			if (ts[i] != null && !ts[i].equals("")) {
				TestPaperQuestionDetail t1 = new TestPaperQuestionDetail();
				int code = Integer.parseInt(ts[i]);
				t1.setQuestion(new Question(code));
				t1.setQuestionCode(code);
				t1.setScore(Integer.parseInt(fs[i]));
				details.add(t1);
			}
		}
		int count = 0;
		while (true) {
			String temp = ts[count];
			if (temp.equals("")) {
				count++;
			} else {
				count = Integer.parseInt(temp);
				break;
			}
		}
		Question question = new Question(count);
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("Name", name);
		table.put("CourseCode", question.getCourseCode());
		table.put("Year", question.getYear());
		table.put("Types", question.getTypes());
		table.put("TeachingMaterialVersion",
				question.getTeachingMaterialVersion());
		table.put("WordPaperId", wordPaperId);

		TestPaper tp = p2.CreateTestPaper(table, details);
		model.addAttribute("name", tp.getName());
		List<List<TestPaperQuestionDetail>> list = this.makeshowPaper(details);
		list = this.showpaixu(list);
		model.addAttribute("qlist", list);

		String tihaos = "";
		for (List<TestPaperQuestionDetail> list2 : list) {
			for (TestPaperQuestionDetail q : list2) {
				tihaos = tihaos + "," + q.getQuestion().getCode();
			}
		}

		String fens = "";
		for (List<TestPaperQuestionDetail> list2 : list) {
			for (TestPaperQuestionDetail q : list2) {
				fens = fens + "," + q.getScore();
			}
		}
		model.addAttribute("tihaos", tihaos.substring(1, tihaos.length()));
		model.addAttribute("fens", fens.substring(1, fens.length()));
		model.addAttribute("wordPaperId", tp.getWordPaperId());
		return "teacher/paperview";
	}

	@RequestMapping("/paperview")
	public String paperview(Model model, String code) {
		TestPaper tp = new TestPaper(Integer.parseInt(code));

		model.addAttribute("name", tp.getName());

		List<TestPaperQuestionDetail> temlist = tp.getDetail().getResult();

		List<List<TestPaperQuestionDetail>> list = this.makeshowPaper(temlist);
		list = this.showpaixu(list);
		model.addAttribute("qlist", list);
		model.addAttribute("isTeacher", true);
		return "teacher/paperview";
	}

	@RequestMapping("/addquestion")
	public String addquestion() {
		return "teacher/addquestion";
	}

	@RequestMapping("/batchAddQuestion")
	public String batchAddQuestion() {
		return "teacher/batchAddQuestion";
	}

	@RequestMapping("/delquestion")
	public void delquestion(Model model, String ti) {

		String[] ts = ti.split(",");
		List<Integer> codes = new ArrayList<Integer>();
		for (int i = 0; i < ts.length; i++) {
			if (ts[i] != null && !ts[i].equals("")) {
				codes.add(Integer.parseInt(ts[i]));
			}
		}
		QuestionManager.GetInstance().deleteQuestion(codes);
	}

	@RequestMapping("/savequestion")
	public String savequestion(@ModelAttribute SearchQuestionForm form,
			Model model) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");

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
		table.put("Name", form.getName());
		table.put("Answer", form.getAnswer());
		table.put("SubmitComment", form.getSubmitComment());
		table.put("Zhishidian", form.getZhishidian());
		table.put("Zhangjie", form.getZhangjie());

		table.put("status",
				String.valueOf(QuestionStatus.NotSubmited.ordinal()));

		t.CreateQuestion(table);

		return "teacher/submitquestionlist";
	}

	@RequestMapping("/getapplyquestion")
	public String getApplyQuestion(Model model) {

		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");
		Hashtable<Student, List<Question>> hash = t.getStudentsApplyQuestions();

		model.addAttribute("hash", hash);

		return "teacher/apllyanswerlist";
	}

	@RequestMapping("/approvedquestion")
	public void approvedQuestion(Model model, String id) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
		Teacher t = (Teacher) session.getAttribute("LoginUser");

		String[] qs = id.split(":");

		for (String temp : qs) {
			if (temp != null && temp.length() != 0 && temp.contains("|")) {
				String[] te = temp.split("[|]");
				t.ApprovedQuestion(te[0], te[1]);
			}
		}
	}

	@RequestMapping(value = "/saveBatchQuestion", produces = "text/html;charset=UTF-8")
	public String saveBatchQuestion(@ModelAttribute BatchAddQuestionForm form,
			Model model) throws ParserConfigurationException, SAXException,
			IOException {

		Teacher t = (Teacher) ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getAttribute("LoginUser");

		String absPath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession()
				.getServletContext().getRealPath("");

		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		String[] kemus = form.getKemu().split(",");
		String[] jiaocaibanbens = form.getJiaocaibanben().split(",");
		String[] nianjis = form.getNianji().split(",");
		String[] nandus = form.getNandu().split(",");
		String[] tixings = form.getTixing().split(",");
		String[] leixings = form.getLeixing().split(",");
		String[] jigoumingchengs = form.getJigoumingcheng().split(",");
		String[] zhishidians = form.getZhishidian().split(",,");
		String[] zhangjies = form.getZhangjie().split(",,");
		String[] summarys = form.getSummary().split(",");
		for (int i = 0; i < form.getTiGanFile().length; i++) {
			QuestionBank q = new QuestionBank();
			q.setCourseCode(kemus[i]);
			q.setTeachingMaterialVersion(jiaocaibanbens[i]);
			q.setYear(nianjis[i]);
			q.setDifficulty(nandus[i]);
			q.setQuestionTypes(tixings[i]);
			q.setTypes(leixings[i]);
			q.setTeacherCode(t.getTeacherCode());
			q.setOrganization(jigoumingchengs[i]);
			q.setStatus(QuestionStatus.Submited);
			String docId = WordStreamToDB.createQuestionToDB(summarys[i],
					form.getTiGanFile()[i].getInputStream(),
					form.getDaAnFile()[i].getInputStream(), absPath);
			q.setAnswer(docId);
			q.setName(docId);

			session.save(q);
			if (zhishidians.length > i) {
				String[] ks = zhishidians[i].split(",");
				for (String kCode : ks) {
					kCode = kCode.trim();
					if (!kCode.equals("")) {
						KnowledgePointAndQuestion kaq = new KnowledgePointAndQuestion();
						kaq.setKnowledgePointCode(kCode);
						kaq.setQuestionCode(q.getCode());
						session.save(kaq);
					}
				}
			}

			if (zhangjies.length > i) {
				String[] ss = zhangjies[i].split(",");
				for (String sCode : ss) {
					sCode = sCode.trim();
					if (!sCode.equals("")) {
						SectionAndQuestion saq = new SectionAndQuestion();
						saq.setSectionPointCode(sCode);
						saq.setQuestionCode(q.getCode());
						session.save(saq);
					}
				}
			}
		}

		session.getTransaction().commit();
		return "teacher/batchAddQuestionSucced";
	}
}

class ComparatorQuestionList implements Comparator<Object> {

	@SuppressWarnings("unchecked")
	public int compare(Object arg0, Object arg1) {
		List<Question> ql0 = (List<Question>) arg0;
		List<Question> ql1 = (List<Question>) arg1;
		return ql0.get(0).getQuestionTypes()
				.compareTo(ql1.get(0).getQuestionTypes());
	}
}

class ComparatorTestPaperQuestionDetailList implements Comparator<Object> {

	@SuppressWarnings("unchecked")
	public int compare(Object arg0, Object arg1) {
		List<TestPaperQuestionDetail> ql0 = (List<TestPaperQuestionDetail>) arg0;
		List<TestPaperQuestionDetail> ql1 = (List<TestPaperQuestionDetail>) arg1;
		return ql0.get(0).getQuestion().getQuestionTypes()
				.compareTo(ql1.get(0).getQuestion().getQuestionTypes());
	}
}
