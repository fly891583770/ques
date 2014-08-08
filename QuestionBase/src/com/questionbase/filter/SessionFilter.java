package com.questionbase.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionFilter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handler) throws IOException {
		HttpSession session = req.getSession(true);

		if (req.getRequestURI().equals("/QuestionBase/account/logout")) {
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
			return false;
		}

		if (req.getRequestURI().equals("/QuestionBase/account/login")) {
			return true;
		}
		if (session == null || session.getAttribute("LoginUser") == null) {
			resp.sendRedirect(req.getContextPath() + "/index.jsp?TimeOut=True");
			return false;
		}
		return true;
	}
}
