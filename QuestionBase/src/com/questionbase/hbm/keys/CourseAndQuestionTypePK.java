package com.questionbase.hbm.keys;

import java.io.Serializable;

public class CourseAndQuestionTypePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String courseCode;
	private int questionTypeCode;
	private String schoolCode;

	public CourseAndQuestionTypePK() {

	}

	public CourseAndQuestionTypePK(String courseCode, int questionTypeCode,
			String schoolCode) {

		this.courseCode = courseCode;
		this.questionTypeCode = questionTypeCode;
		this.schoolCode = schoolCode;

	}

	@Override
	public int hashCode() {

		final int PRIME = 31;

		int result = 1;

		result = PRIME * result + questionTypeCode;

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

		final CourseAndQuestionTypePK other = (CourseAndQuestionTypePK) obj;

		if (questionTypeCode != (other.questionTypeCode))

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
