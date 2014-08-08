package com.questionbase.hbm.keys;

import java.io.Serializable;

public class DirectorRoleInfoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectorRoleInfoPK() {

	}

	public DirectorRoleInfoPK(String name, String account) {

		this.name = name;

		this.account = account;

	}

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	private String name;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	@Override
	public int hashCode() {

		final int PRIME = 31;

		int result = 1;

		result = PRIME * result + ((account == null) ? 0 : account.hashCode());

		result = PRIME * result + ((name == null) ? 0 : name.hashCode());

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

		final DirectorRoleInfoPK other = (DirectorRoleInfoPK) obj;

		if (account == null) {

			if (other.account != null)

				return false;

		} else if (!account.equals(other.account))

			return false;

		if (name == null) {

			if (other.name != null)

				return false;

		} else if (!name.equals(other.name))

			return false;

		return true;

	}
}
