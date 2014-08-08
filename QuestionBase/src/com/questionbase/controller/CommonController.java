package com.questionbase.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.questionbase.hbm.KnowledgePoint;
import com.questionbase.hbm.SectionPoint;
import com.questionbase.question.QuestionManager;

@Controller
@RequestMapping("/common")
public class CommonController {

	@RequestMapping(value = "/zsd", produces = "text/html;charset=UTF-8")
	public @ResponseBody()
	String getZSD(String courceCode, String teachingMaterialVersion,
			String year, String schoolCode) {

		String root = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getParameter("root");

		ArrayList<Hashtable<String, String>> list = new ArrayList<>();

		if (root == null || root.equals("source")) {
			List<KnowledgePoint> ks = QuestionManager.GetInstance()
					.GetKnowledgePoint(courceCode, teachingMaterialVersion,
							year, schoolCode, null);
			for (KnowledgePoint k : ks) {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("text", k.getCaption());
				table.put("id", String.valueOf(k.getCode()));
				table.put("hasChildren", "true");
				list.add(table);
			}

			if (ks.size() == 0) {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("text", "没有登录知识点");
				table.put("id", "");
				table.put("hasChildren", "false");
				list.add(table);
			}
		} else {
			List<KnowledgePoint> ks = QuestionManager.GetInstance()
					.GetKnowledgePoint(null, null, null, null, root);
			for (KnowledgePoint k : ks) {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("text", k.getCaption());
				table.put("id", String.valueOf(k.getCode()));
				table.put("hasChildren", "true");
				list.add(table);
			}
		}

		JSONArray json = JSONArray.fromObject(list);

		return json.toString();
	}

	@RequestMapping(value = "/zhangjie", produces = "text/html;charset=UTF-8")
	public @ResponseBody()
	String getZhangJie(String courceCode, String teachingMaterialVersion,
			String year, String schoolCode) {

		String root = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getParameter("root");

		ArrayList<Hashtable<String, String>> list = new ArrayList<>();

		if (root == null || root.equals("source")) {
			List<SectionPoint> ks = QuestionManager.GetInstance()
					.GetSectionPoint(courceCode, teachingMaterialVersion, year,
							schoolCode, null);
			for (SectionPoint k : ks) {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("text", k.getCaption());
				table.put("id", String.valueOf(k.getCode()));
				table.put("hasChildren", "true");
				list.add(table);
			}

			if (ks.size() == 0) {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("text", "没有登录章节");
				table.put("id", "");
				table.put("hasChildren", "false");
				list.add(table);
			}
		} else {
			List<SectionPoint> ks = QuestionManager.GetInstance()
					.GetSectionPoint(null, null, null, null, root);
			for (SectionPoint k : ks) {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("text", k.getCaption());
				table.put("id", String.valueOf(k.getCode()));
				table.put("hasChildren", "true");
				list.add(table);
			}
		}

		JSONArray json = JSONArray.fromObject(list);

		return json.toString();
	}

	@RequestMapping(value = "/PageOfficeNewWord")
	public String getPageOfficeNewWord() {
		return "common/PageOfficeNewWord";
	}

	@RequestMapping(value = "/PageOfficeEditWord")
	public String getPageOfficeEditWord() {
		return "common/PageOfficeEditWord";
	}

	@RequestMapping(value = "/Openstream")
	public String getOpenstream() {
		return "common/Openstream";
	}

	@RequestMapping(value = "/SaveNewFile")
	public String getSaveNewFile() {
		return "common/SaveNewFile";
	}

	@RequestMapping(value = "/Compose")
	public String getCompose() {
		return "common/Compose";
	}
	
	@RequestMapping(value = "/ShijuanYuLan")
	public String getShijuanYuLan() {
		return "common/ShijuanYuLan";
	}
	
	@RequestMapping(value = "/PageOfficeReadOnlyWord")
	public String getPageOfficeReadOnlyWord() {
		return "common/PageOfficeReadOnlyWord";
	}
	
	@RequestMapping(value = "/TitleAndAnswerWord")
	public String getTitleAndAnswerWord() {
		return "common/TitleAndAnswerWord";
	}
	
	@RequestMapping(value = "/SavePaper")
	public String getSavePaper() {
		return "common/SavePaper";
	}
	
	@RequestMapping(value = "/OpenWordPaperStream")
	public String getOpenWordPaperStream() {
		return "common/OpenWordPaperStream";
	}
}
