package com.questionbase.service.impl;

import com.questionbase.bean.LoginForm;
import com.questionbase.exception.QuestionBaseException;
import com.questionbase.logic.Person;
import com.questionbase.logic.PersonUtil;
import com.questionbase.service.IUserService;

public class UserServiceImpl implements IUserService {

	@Override
	public Person CheckUser(LoginForm form) {
		
		
		try {
			
			Person person = PersonUtil.getInstance().Login(form.getUsername(), form.getPassword());
			return person;
			
		} catch (QuestionBaseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		

		
		
	}

}
