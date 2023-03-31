package com.lec.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.dao.BoardDao;
import com.lec.ex.dto.BoardDto;

public class BoardModifyViewService implements Service {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		BoardDao boardDao = BoardDao.getInstance();
		BoardDto boardDto = boardDao.modifyViewBoard_replyViewBoard(boardId);
		request.setAttribute("board", boardDto);
	}

}