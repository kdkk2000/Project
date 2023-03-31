package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lec.ex.dao.AdminDao;
import com.lec.ex.dto.AdminDto;

public class AdminJoinService implements Service {
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String adminId = request.getParameter("adminId");
        String adminPw = request.getParameter("adminPw");
        String adminName = request.getParameter("adminName");
        String adminEmail = request.getParameter("adminEmail");
        String adminPhone = request.getParameter("adminPhone");
        
        AdminDao mDao = AdminDao.getInstance();
		// adminId 중복 체크
		int result = mDao.adminIdConfirm(adminId);
		if (result == AdminDao.NONEXISTENT) { // 가입 가능한 adminId이면 회원가입
			AdminDto admin = new AdminDto(adminId, adminPw, adminName, adminEmail, adminPhone);
			// 회원가입
			result = mDao.joinAdmin(admin);
			if (result == AdminDao.SUCCESS) {
				HttpSession session = request.getSession(); // 세션은 request로부터
				session.setAttribute("adminId", adminId);
				request.setAttribute("joinResult", "회원가입이 완료되었습니다.");
			} else {
				request.setAttribute("joinErrorMsg", "회원가입에 실패하였습니다.");
			}
		} else {
			request.setAttribute("joinErrorMsg", "중복된 ID가 존재합니다.");
		}
	}
}
