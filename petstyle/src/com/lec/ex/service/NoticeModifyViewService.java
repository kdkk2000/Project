package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.NoticeDao;
import com.lec.ex.dto.NoticeDto;

public class NoticeModifyViewService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int noticeId = Integer.parseInt(request.getParameter("noticeId"));
		NoticeDao noticeDao = NoticeDao.getInstance();
		NoticeDto noticeDto = noticeDao.modifyViewNotice_replyViewNotice(noticeId);
		request.setAttribute("notice", noticeDto);
	}

}