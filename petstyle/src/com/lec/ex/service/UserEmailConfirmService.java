package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.UserDao;

public class UserEmailConfirmService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String userEmail = request.getParameter("userEmail");
		UserDao dao = UserDao.getInstance();
		int result = dao.userEmailConfirm(userEmail);
		if(result == UserDao.EXISTENT){
			request.setAttribute("userEmailConfirmResult","<b>사용 불가한 중복된 메일</b>");
		}else{
			request.setAttribute("userEmailConfirmResult","사용 가능한 메일");
		}
	}

}
