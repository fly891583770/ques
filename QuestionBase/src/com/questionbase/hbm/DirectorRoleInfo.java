package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.questionbase.hbm.keys.DirectorRoleInfoPK;

@Entity
@Table(name = "FS_ORG_ROLE_PSN_MAP")
@IdClass(DirectorRoleInfoPK.class)
public class DirectorRoleInfo {
	@Column(name = "ROLE_NAME")
	@Id
	private String name;

	@Column(name = "PERSON_ACCOUNT")
	@Id
	private String account;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
