package com.questionbase.hbm.factory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.questionbase.hbm.ClassInfo;
import com.questionbase.hbm.CourseAndMaterialVersion;
import com.questionbase.hbm.CourseAndQuestionType;
import com.questionbase.hbm.CourseAndTypes;
import com.questionbase.hbm.CourseInfo;
import com.questionbase.hbm.DirectorRoleInfo;
import com.questionbase.hbm.ExerciseBookBank;
import com.questionbase.hbm.ExerciseBookInfo;
import com.questionbase.hbm.FamilyInfo;
import com.questionbase.hbm.InstructorInfo;
import com.questionbase.hbm.KnowledgePoint;
import com.questionbase.hbm.KnowledgePointAndQuestion;
import com.questionbase.hbm.QuestionBank;
import com.questionbase.hbm.QuestionTypeInfo;
import com.questionbase.hbm.RoleInfo;
import com.questionbase.hbm.SchoolDetail;
import com.questionbase.hbm.SchoolInfo;
import com.questionbase.hbm.SectionAndQuestion;
import com.questionbase.hbm.SectionPoint;
import com.questionbase.hbm.Stream;
import com.questionbase.hbm.StudentAndQuestion;
import com.questionbase.hbm.StudentInfo;
import com.questionbase.hbm.SystemUserInfo;
import com.questionbase.hbm.TeacherInfo;
import com.questionbase.hbm.TeachingMaterialVersionInfo;
import com.questionbase.hbm.TestPaperDetailInfo;
import com.questionbase.hbm.TestPaperInfo;
import com.questionbase.hbm.TypesInfo;
import com.questionbase.hbm.WordPaper;

public class QuestionBaseFactory {

	private static volatile QuestionBaseFactory instance;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private QuestionBaseFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(ClassInfo.class);
		configuration.addAnnotatedClass(FamilyInfo.class);
		configuration.addAnnotatedClass(StudentInfo.class);
		configuration.addAnnotatedClass(TeacherInfo.class);
		configuration.addAnnotatedClass(InstructorInfo.class);
		configuration.addAnnotatedClass(SystemUserInfo.class);
		configuration.addAnnotatedClass(CourseInfo.class);
		configuration.addAnnotatedClass(SchoolInfo.class);
		configuration.addAnnotatedClass(RoleInfo.class);
		configuration.addAnnotatedClass(QuestionBank.class);
		configuration.addAnnotatedClass(ExerciseBookBank.class);
		configuration.addAnnotatedClass(ExerciseBookInfo.class);
		configuration.addAnnotatedClass(StudentAndQuestion.class);
		configuration.addAnnotatedClass(KnowledgePointAndQuestion.class);
		configuration.addAnnotatedClass(SectionAndQuestion.class);
		configuration.addAnnotatedClass(DirectorRoleInfo.class);
		configuration.addAnnotatedClass(TestPaperInfo.class);
		configuration.addAnnotatedClass(TestPaperDetailInfo.class);
		configuration.addAnnotatedClass(KnowledgePoint.class);
		configuration.addAnnotatedClass(SectionPoint.class);
		configuration.addAnnotatedClass(SchoolDetail.class);
		configuration.addAnnotatedClass(TeachingMaterialVersionInfo.class);
		configuration.addAnnotatedClass(Stream.class);
		configuration.addAnnotatedClass(WordPaper.class);
		configuration.addAnnotatedClass(QuestionTypeInfo.class);
		configuration.addAnnotatedClass(TypesInfo.class);
		configuration.addAnnotatedClass(CourseAndMaterialVersion.class);
		configuration.addAnnotatedClass(CourseAndQuestionType.class);
		configuration.addAnnotatedClass(CourseAndTypes.class);
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		this.sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
	}

	public static QuestionBaseFactory GetInstance() {
		if (instance == null) {

			synchronized (QuestionBaseFactory.class) {
				if (instance == null) {
					instance = new QuestionBaseFactory();
				}
			}
		}

		return instance;
	}
}
