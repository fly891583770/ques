package com.questionbase.bean;

import org.springframework.web.multipart.MultipartFile;

public class BatchAddQuestionForm {

	private MultipartFile[] tiGanFile;
	private MultipartFile[] daAnFile;
	private String kemu;
	private String jiaocaibanben;
	private String nianji;
	private String nandu;
	private String tixing;
	private String leixing;
	private String jigoumingcheng;
	private String zhishidian;
	private String zhangjie;
	private String summary;

	public MultipartFile[] getDaAnFile() {
		return daAnFile;
	}

	public MultipartFile[] getTiGanFile() {
		return tiGanFile;
	}

	public void setTiGanFile(MultipartFile[] tiGanFile) {
		this.tiGanFile = tiGanFile;
	}

	public void setDaAnFile(MultipartFile[] daAnFile) {
		this.daAnFile = daAnFile;
	}

	public String getKemu() {
		return kemu;
	}

	public void setKemu(String kemu) {
		this.kemu = kemu;
	}

	public String getJiaocaibanben() {
		return jiaocaibanben;
	}

	public void setJiaocaibanben(String jiaocaibanben) {
		this.jiaocaibanben = jiaocaibanben;
	}

	public String getNianji() {
		return nianji;
	}

	public void setNianji(String nianji) {
		this.nianji = nianji;
	}

	public String getNandu() {
		return nandu;
	}

	public void setNandu(String nandu) {
		this.nandu = nandu;
	}

	public String getTixing() {
		return tixing;
	}

	public void setTixing(String tixing) {
		this.tixing = tixing;
	}

	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public String getJigoumingcheng() {
		return jigoumingcheng;
	}

	public void setJigoumingcheng(String jigoumingcheng) {
		this.jigoumingcheng = jigoumingcheng;
	}

	public String getZhishidian() {
		return zhishidian;
	}

	public void setZhishidian(String zhishidian) {
		this.zhishidian = zhishidian;
	}

	public String getZhangjie() {
		return zhangjie;
	}

	public void setZhangjie(String zhangjie) {
		this.zhangjie = zhangjie;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
