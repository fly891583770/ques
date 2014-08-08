import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.xml.sax.SAXException;

import com.questionbase.exception.QuestionBaseException;
import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.TeacherInfo;
import com.questionbase.hbm.factory.QuestionBaseFactory;
import com.questionbase.logic.Person;
import com.questionbase.logic.PersonUtil;
import com.questionbase.logic.person.Director;
import com.questionbase.logic.person.Parent;
import com.questionbase.logic.person.SchoolAdmin;
import com.questionbase.logic.person.Student;
import com.questionbase.logic.person.Teacher;
import com.questionbase.question.ExerciseBook;
import com.questionbase.question.Question;
import com.questionbase.question.QuestionManager;
import com.questionbase.question.QuestionStatus;
import com.questionbase.question.S_Question;
import com.questionbase.question.SelectTestPaperResult;
import com.questionbase.question.TestPaper;
import com.questionbase.question.TestPaperQuestionDetail;

/**
 * @author Administrator Start
 */
public class Main {

	public static void main(String[] args) throws QuestionBaseException,
			InterruptedException, FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		System.out.println("Start");

		// MakeQutsion();

		//DoTestQestion();
		DoTestLogin();
		DoTestTeacher();
		DoTestParent();
		DoTestStudent();

		System.out.println("End");
	}

	@SuppressWarnings("unchecked")
	private static void MakeQutsion() {
		Session session = QuestionBaseFactory.GetInstance().getSessionFactory()
				.openSession();
		session.beginTransaction();
		List<TeacherInfo> ts = session.createCriteria(TeacherInfo.class).list();
		int count = 0;
		for (TeacherInfo t : ts) {
			QuestionBank q = new QuestionBank();
			q.setAnswer(t.getName() + "的题的答案");
			q.setName(t.getName() + "出的题");
			q.setCourseCode(String.valueOf(13 + count % 2));
			q.setTeachingMaterialVersion(String.valueOf(1 + count % 2));
			q.setYear(String.valueOf(1 + count % 12));
			q.setDifficulty(String.valueOf(1 + count % 3));
			q.setQuestionTypes(String.valueOf(1 + count % 5));
			q.setTypes(String.valueOf(1 + count % 2));
			switch (count % 3) {
			case 0:
				q.setOrganization("2201040008");
				break;
			case 1:
				q.setOrganization("2201040001");
				break;
			case 2:
				q.setOrganization("2201040002");
				break;
			}
			q.setTeacherCode(t.getTeacherCode());

			count++;
			session.save(q);
		}

		session.getTransaction().commit();
	}

	private static void DoTestQestion() throws InterruptedException,
			QuestionBaseException {

		Hashtable<String, String> table = new Hashtable<String, String>();
		//
		ArrayList<Integer> list = new ArrayList<>();
		list.add(224);
		// QuestionManager.GetInstance().deleteQuestion(list);

		List<Question> sb = QuestionManager.GetInstance()
				.CreateQuestionByQuestionCode(list);

		String s = sb.get(0).getSummary();
		
		sb.get(0).getUpdateTime();

		// table.put("CourseCode", "13");
		// table.put("TeachingMaterialVersion", "1");
		// table.put("Year", "5");
		// table.put("Difficulty", "2");
		// table.put("QuestionTypes", "5");
		// table.put("Types", "1");
		// table.put("Organization", "2201040008");
		table.put("TeacherName", "孟宇萍");
		// table.put("KnowledgePoint", "0,2,1");
		// table.put("SectionPoint", "0,2,1");
		// table.put("Page", "1");
		table.put("Status", "0,1,2");

		SelectTestPaperResult qs = QuestionManager.GetInstance()
				.SelectTestPaper(table);

		Teacher teacher = (Teacher) PersonUtil.getInstance().Login("myp_19",
				"abcdefg");
		teacher.SubmitTestPaper(2);

		Director director = (Director) PersonUtil.getInstance().Login("zcf_19",
				"abcdefg");

		ArrayList<String> apllyPaperlist = new ArrayList<String>();
		for (TestPaper q : qs.getTestPapers()) {
			apllyPaperlist.add(String.valueOf(q.getCode()));
		}
		director.agreeApllyTestPaper(apllyPaperlist, QuestionStatus.NotSubmited);
	}

	@SuppressWarnings("unused")
	private static void DoTestLogin() throws QuestionBaseException,
			FileNotFoundException, ParserConfigurationException, SAXException,
			IOException {

		Student s = (Student)PersonUtil.getInstance().Login(
				"wangsijia", "0000");
		
		Teacher t = (Teacher)PersonUtil.getInstance().Login(
				"lishuyun", "0000");
		
		Director d = (Director)PersonUtil.getInstance().Login(
				"sunpingping", "0000");
		
		SchoolAdmin ap = (SchoolAdmin) PersonUtil.getInstance().Login(
				"admin", "abcdefg");

		QuestionManager.GetInstance().GetKnowledgePoint("13", "1", "1",
				"2201040008", null);

		// ap.SaveKnowledgePoint(new FileInputStream(new File(
		// "E:\\BaiduYunDownload\\ZSD.xml")), "13", "1", "1");
		// ap.SaveSectionPoint(new FileInputStream(new File(
		// "E:\\BaiduYunDownload\\ZhangJie.xml")), "13", "1", "1");
		Person student = PersonUtil.getInstance().Login("fl_19", "abcdefg");
		if (student instanceof Student) {

		}

		Person parent = PersonUtil.getInstance().Login("fjt_19", "abcdefg");
		if (parent instanceof Parent) {
			Parent p2 = (Parent) parent;
			Student stu = (Student) p2.getChild();
			List<S_Question> x = stu.getQuestions();
			stu.getQuestion(56);
		}

		Person director = PersonUtil.getInstance().Login("zcf_19", "abcdefg");
		if (director instanceof Director) {
			Director p2 = (Director) director;
			p2.getAllApllyQuestion();
			ArrayList<String> list = new ArrayList<String>();
			list.add("1");
			list.add("2");
			list.add("3");
			p2.agreeApllyQuestion(list, QuestionStatus.Accept);
		}

		Person teacher = PersonUtil.getInstance().Login("myp_19", "abcdefg");
		if (teacher instanceof Teacher) {
			Teacher p2 = (Teacher) teacher;
			Hashtable<String, String> table = new Hashtable<String, String>();
			table.put("Name", "来个新题");
			table.put("Answer", "来个新答案");
			table.put("CourseCode", "13");
			table.put("CourseCode", "1");
			table.put("Year", "1");
			table.put("Difficulty", "1");
			table.put("QuestionTypes", "1");
			table.put("SubmitComment", "你看看咋样？");

			ArrayList<TestPaperQuestionDetail> details = new ArrayList<TestPaperQuestionDetail>();
			TestPaperQuestionDetail t1 = new TestPaperQuestionDetail();
			t1.setQuestionCode(1);
			t1.setScore(50);
			TestPaperQuestionDetail t2 = new TestPaperQuestionDetail();
			t2.setQuestionCode(2);
			t2.setScore(50);

			details.add(t1);
			details.add(t2);

			TestPaper tp = p2.CreateTestPaper(table, details);
		}

	}

	private static void DoTestTeacher() {
		// Teacher t = new Teacher("220104001900210472");
		// Hashtable<String, String> table = new Hashtable<String, String>();
		// table.put("Name", "来个新题");
		// table.put("Answer", "来个新答案");
		// table.put("CourseCode", "13");
		// table.put("CourseCode", "1");
		// table.put("Year", "1");
		// table.put("Difficulty", "1");
		// table.put("QuestionTypes", "1");
		// table.put("SubmitComment", "你看看咋样？");
		// Question q = t.CreateQuestion(table);
		// Question q2 = t.SubmitQuesiont(q.getCode());
		//
		// Teacher t2 = new Teacher("220104001900210486");
		//
		// Hashtable<Student, List<Question>> sq =
		// t.getStudentsApplyQuestions();
		// for (Entry<Student, List<Question>> entry : sq.entrySet()) {
		// Student s = entry.getKey();
		// List<Question> qs = entry.getValue();
		// t.ApprovedQuestion(s, qs);
		// }
	}

	private static void DoTestParent() throws QuestionBaseException {
		Parent p = new Parent("7f1e0901-9f7f-4d6b-bfb5-5ffac4a30b65");
		Student s = (Student) p.getChild();

		ArrayList<Integer> questions = new ArrayList<Integer>();
		questions.add(12);
		questions.add(13);
		questions.add(16);
		questions.add(17);
		questions.add(18);
		questions.add(19);
		questions.add(20);

		s.ChooseQuestion(questions);

		s.SetAnswer(12, "哥也不会");
		s.ApplyAnswer(12);

		// p.ApprovedQuestion(12);
	}

	@SuppressWarnings("unused")
	private static void DoTestStudent() {
		System.out.println("Student Start");
		Student s = new Student("220104001900208198");
		List<Question> qs = QuestionManager.GetInstance().GetAllQuestions();

		ArrayList<Integer> questions = new ArrayList<Integer>();
		questions.add(12);
		questions.add(13);
		questions.add(16);
		questions.add(17);
		questions.add(18);
		questions.add(19);
		questions.add(20);

		s.ChooseQuestion(questions);

		s.SetAnswer(12, "哥不会");
		s.ApplyAnswer(12);

		// s.DeleteExerciseBook(2);

		// s.ChooseQuestion(questions);

		ExerciseBook book = s.CreateExerciseBook("sb001", questions);

		ArrayList<Integer> questions2 = new ArrayList<Integer>();
		questions2.add(1);
		questions2.add(2);
		book.AddQuestions(questions2);

		book.DeleteQuestions(questions2);
		s.DeleteExerciseBook(book.getCode());
		System.out.println("Student End");

	}
}
