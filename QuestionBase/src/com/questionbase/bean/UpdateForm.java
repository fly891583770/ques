package com.questionbase.bean;

import org.springframework.web.multipart.MultipartFile;

public class UpdateForm {

		private MultipartFile xmlFile;
		private String kemu;
		private String edittype;
		public String getEdittype() {
			return edittype;
		}

		public void setEdittype(String edittype) {
			this.edittype = edittype;
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

		private String jiaocaibanben;
		private String nianji;
		
		public MultipartFile getXmlFile() {
			return xmlFile;
		}

		public void setXmlFile(MultipartFile xmlFile) {
			this.xmlFile = xmlFile;
		}


}
