package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.NoticeDao;

public class NoticeDeleteService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// 삭제하고자 하는 글만 삭제
		int noticeId = Integer.parseInt(request.getParameter("noticeId"));
		
		NoticeDao noticeDao = NoticeDao.getInstance();
		int result = noticeDao.deleteNotice(noticeId);
		if(result == NoticeDao.SUCCESS) {
			request.setAttribute("noticeResult", "글 삭제 성공");
		}else {
			request.setAttribute("noticeResult", "글삭제 실패");
		}
	}

}
 
