package com.questionbase.hbm.keys;

import java.io.Serializable;

public class TestPaperDetailInfoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestPaperDetailInfoPK() {

	}

	public TestPaperDetailInfoPK(int paperCode, int questionCode) {

		this.paperCode = paperCode;

		this.questionCode = questionCode;

	}

	private int questionCode;

	public int getAccount() {
		return questionCode;
	}

	public void setAccount(int account) {
		this.questionCode = account;
	}

	private int paperCode;

	public int getName() {

		return paperCode;

	}

	public void setName(int name) {

		this.paperCode = name;

	}

	@Override
	public int hashCode() {

		final int PRIME = 31;

		int result = 1;

		result = PRIME * result + questionCode;

		result = PRIME * result + paperCode;

		return result;

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)

			return true;

		if (obj == null)

			return false;

		if (getClass() != obj.getClass())

			return false;

		final TestPaperDetailInfoPK other = (TestPaperDetailInfoPK) obj;

		if (questionCode != (other.questionCode)) {
			return false;
		} else if (paperCode != (other.paperCode)) {
			return false;
		}

		return true;

	}
}
