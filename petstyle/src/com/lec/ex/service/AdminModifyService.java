package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.ex.dao.AdminDao;
import com.lec.ex.dto.AdminDto;

public class AdminModifyService implements Service {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		AdminDao dao = AdminDao.getInstance();
		HttpSession session = request.getSession();
		AdminDto admin = (AdminDto)session.getAttribute("admin");
		admin.setAdminPw(request.getParameter("adminPw"));
		admin.setAdminPw(request.getParameter("newAdminPw"));
		String newAdminPw = request.getParameter("newAdminPw");
		if (newAdminPw != null && !newAdminPw.isEmpty()) {
			admin.setAdminPw(newAdminPw);
		} else {
			admin.setAdminPw(admin.getAdminPw());
		}
		admin.setAdminName(request.getParameter("adminName"));
		admin.setAdminEmail(request.getParameter("adminEmail"));
		admin.setAdminPhone(request.getParameter("adminPhone"));
		int result = dao.modifyAdmin(admin);
		if(result==AdminDao.SUCCESS) {
			session.setAttribute("modifyResult", "회원정보 수정 성공");
		}else {
			session.setAttribute("modifyResultError", "회원정보 수정 실패");
		}
	}

}

