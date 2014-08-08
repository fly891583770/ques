package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZX_XX_JBXX")
public class SchoolInfo {

	@Column(name = "XXDM")
	@Id
	private String code;

	@Column(name = "XXMC")
	private String name;

	@Column(name = "XZQHM")
	private String regionCode;

	@Column(name = "XXXZ")
	private String sXZ;

	@Column(name = "CZXZ")
	private String mXZ;

	@Column(name = "GAOZHONGXUEZHI")
	private String hXZ;

	public String getsXZ() {
		return sXZ;
	}

	public void setsXZ(String sXZ) {
		this.sXZ = sXZ;
	}

	public String getmXZ() {
		return mXZ;
	}

	public void setmXZ(String mXZ) {
		this.mXZ = mXZ;
	}

	public String gethXZ() {
		return hXZ;
	}

	public void sethXZ(String hXZ) {
		this.hXZ = hXZ;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

}
