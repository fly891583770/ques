package com.questionbase.hbm.keys;

import java.io.Serializable;

public class CourseAndMaterialVersionPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseCode;
	private int teachingMaterialVersionCode;

	public CourseAndMaterialVersionPK() {

	}

	public CourseAndMaterialVersionPK(String courseCode,
			int teachingMaterialVersionCode) {

		this.courseCode = courseCode;

		this.teachingMaterialVersionCode = teachingMaterialVersionCode;

	}

	@Override
	public int hashCode() {

		final int PRIME = 31;

		int result = 1;

		result = PRIME * result + teachingMaterialVersionCode;

		result = PRIME * result
				+ ((courseCode == null) ? 0 : courseCode.hashCode());

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

		final CourseAndMaterialVersionPK other = (CourseAndMaterialVersionPK) obj;

		if (teachingMaterialVersionCode != (other.teachingMaterialVersionCode))

			return false;

		if (courseCode == null) {

			if (other.courseCode != null)

				return false;

		} else if (!courseCode.equals(other.courseCode))

			return false;

		return true;

	}
}
