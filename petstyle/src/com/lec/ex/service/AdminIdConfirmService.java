package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.AdminDao;

public class AdminIdConfirmService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String adminId = request.getParameter("adminId");
		AdminDao dao = AdminDao.getInstance();
		int result = dao.adminIdConfirm(adminId);
		if (result == AdminDao.EXISTENT) {
			request.setAttribute("adminIdConfirmResult", "사용 불가한 아이디입니다");
		} else {
			request.setAttribute("adminIdConfirmResult", "사용 가능한 ID입니다");
		}
	}

}
