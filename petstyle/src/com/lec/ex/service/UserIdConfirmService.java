package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.UserDao;

public class UserIdConfirmService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		UserDao dao = UserDao.getInstance();
		int result = dao.userIdConfirm(userId);
		if(result == UserDao.EXISTENT){
			request.setAttribute("userIdConfirmResult","사용 불가한 아이디입니다");
		}else{
			request.setAttribute("userIdConfirmResult","사용 가능한 ID입니다");
		}
	}

}
