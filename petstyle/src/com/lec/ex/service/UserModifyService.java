package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.ex.dao.UserDao;
import com.lec.ex.dto.UserDto;

public class UserModifyService implements Service {
	
	@Override
		public void execute(HttpServletRequest request, HttpServletResponse response) {
		UserDao dao = UserDao.getInstance();
		HttpSession session = request.getSession();
		UserDto user = (UserDto)session.getAttribute("user");
		user.setUserPw(request.getParameter("userPw"));
		user.setUserPw(request.getParameter("newUserPw"));
		String newUserPw = request.getParameter("newUserPw");
		if (newUserPw != null && !newUserPw.isEmpty()) {
		    user.setUserPw(newUserPw);
		} else {
		    user.setUserPw(user.getUserPw());
		}
		user.setUserName(request.getParameter("userName"));
		user.setUserEmail(request.getParameter("userEmail"));
		user.setUserPhone(request.getParameter("userPhone"));
		user.setUserAddress(request.getParameter("userAddress"));
		int result = dao.modifyUser(user);
		if(result==UserDao.SUCCESS) {
			session.setAttribute("modifyResult", "회원정보 수정 성공");
		}else {
			session.setAttribute("modifyResultError", "회원정보 수정 실패");
		}
	}
}
