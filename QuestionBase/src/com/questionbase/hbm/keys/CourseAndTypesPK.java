package com.questionbase.hbm.keys;

import java.io.Serializable;

public class CourseAndTypesPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseCode;
	private int typesCode;
	private String schoolCode;

	public CourseAndTypesPK() {

	}

	public CourseAndTypesPK(String courseCode, int typesCode,
			String schoolCode) {

		this.courseCode = courseCode;
		this.typesCode = typesCode;
		this.schoolCode = schoolCode;

	}

	@Override
	public int hashCode() {

		final int PRIME = 31;

		int result = 1;

		result = PRIME * result + typesCode;

		result = PRIME * result
				+ ((courseCode == null) ? 0 : courseCode.hashCode());
		result = PRIME * result
				+ ((schoolCode == null) ? 0 : schoolCode.hashCode());

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

		final CourseAndTypesPK other = (CourseAndTypesPK) obj;

		if (typesCode != (other.typesCode))

			return false;

		if (courseCode == null) {

			if (other.courseCode != null)

				return false;

		} else if (!courseCode.equals(other.courseCode))

			return false;

		if (schoolCode == null) {

			if (other.schoolCode != null)

				return false;

		} else if (!schoolCode.equals(other.schoolCode))

			return false;

		return true;

	}
}
