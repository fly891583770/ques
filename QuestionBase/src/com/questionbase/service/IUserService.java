package com.questionbase.service;

import com.questionbase.bean.LoginForm;
import com.questionbase.logic.Person;

public interface IUserService {

	Person CheckUser(LoginForm form);
	
}
