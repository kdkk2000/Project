package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.ex.dao.UserDao;
import com.lec.ex.dto.UserDto;

public class UserLoginService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("next", request.getParameter("next"));
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		UserDao dao = UserDao.getInstance();
		int result = dao.loginCheck(userId, userPw);
		if(result==UserDao.LOGIN_SUCCESS) { // 로그인 성공
			HttpSession session = request.getSession();
			UserDto user = dao.getUser(userId);
			session.setAttribute("user", user);
		}else { // 로그인 실패
			request.setAttribute("loginErrorMsg", "아이디와 비밀번호를 확인하세요");
		}
	}

}