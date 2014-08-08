package com.questionbase.hbm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ExerciseBookBank")
public class ExerciseBookBank {

	@Column(name = "ExerciseBookCode", unique = true, nullable = false)
	@Id
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "increment")
	private int code;

	@Column(name = "Owner")
	private String studentCode;

	@Column(name = "Name")
	private String name;

	@Column(name = "Permission")
	private boolean guard;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGuard() {
		return guard;
	}

	public void setGuard(boolean guard) {
		this.guard = guard;
	}

}
