package com.lec.ex.controller;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.ex.service.*;
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		actionDo(request, response);
	}
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String command = uri.substring(conPath.length());
		String viewPage = null;
		Service service = null;
		if(command.equals("/main.do")) { // 첫화면
			viewPage = "main/main.jsp";
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * 
		 * * * * * * * * * * user 관련 요청  * * * * * * * * * *
		  * * * * * * * * * * * * * * * * * * * * * * * * * * */
		} else if(command.equals("/joinView.do")) {
			viewPage = "user/join.jsp";
			
		} else if(command.equals("/join.do")) {
			service = new UserJoinService();
			service.execute(request, response);
			viewPage = "user/login.jsp";
			
		} else if(command.equals("/userIdConfirm.do")) {
			service = new UserIdConfirmService();
			service.execute(request, response);
			viewPage = "user/userIdConfirm.jsp";
			
		} else if(command.equals("/userEmailConfirm.do")) {
			service = new UserEmailConfirmService();
			service.execute(request, response);
			viewPage = "user/userEmailConfirm.jsp";
			
		} else if(command.equals("/loginView.do")) {
			viewPage = "user/login.jsp";
			
		} else if(command.equals("/login.do")) {
			service = new UserLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
            
		} else if(command.equals("/logout.do")) {
			service = new UserLogoutService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		} else if(command.equals("/modifyView.do")) {
			viewPage = "user/modify.jsp";
			
		} else if(command.equals("/modify.do")) {
			service = new UserModifyService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		} else if(command.equals("/allUser.do")) {
			service = new UserListService();
			service.execute(request, response);
			viewPage = "admin/allUser.jsp";
			
		} else if(command.equals("/withdrawal.do")) {
			service = new UserWithdrawalService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * 
		 * * * * * * * * * * admin 관련 요청  * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * */
		} else if(command.equals("/adminJoinView.do")) {
			viewPage = "admin/adminJoin.jsp";
			
		} else if(command.equals("/adminJoin.do")) {
			service = new AdminJoinService();
			service.execute(request, response);
			viewPage = "admin/adminLogin.jsp";
			
		} else if(command.equals("/adminLoginView.do")) {
			viewPage = "admin/adminLogin.jsp";
			
		} else if (command.equals("/adminLogin.do")) {
			service = new AdminLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		} else if(command.equals("/adminLogin.do")) {
			service = new AdminLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
			
		} else if(command.equals("/adminIdConfirm.do")) {
			service = new AdminIdConfirmService();
			service.execute(request, response);
			viewPage = "admin/adminIdConfirm.jsp";
		    
		} else if(command.equals("/adminModify.do")) {
			viewPage = "admin/adminModify.jsp";
		    
		} else if(command.equals("/adminModifyView.do")) {
		    service = new AdminModifyService();
		    service.execute(request, response);
		    viewPage = "main/main.jsp";
			
		} else if(command.equals("/logout.do")) {
			service = new AdminLogoutService();
			service.execute(request, response);
			viewPage = "main/main.jsp";

		/* * * * * * * * * * * * * * * * * * * * * * * * * * * 
		 * * * * * * * * 파일첨부 게시판 관련 요청  * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * */
		} else if(command.equals("/boardList.do")) {
			service = new BoardListService();
			service.execute(request, response);
			viewPage = "board/boardList.jsp";
			
		} else if(command.equals("/boardWriteView.do")) {
			viewPage = "board/boardWrite.jsp";
			
		} else if(command.equals("/boardWrite.do")) {
			service = new BoardWriteService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		} else if(command.equals("/boardContent.do")) {
			service = new BoardContentService();
			service.execute(request, response);
			viewPage = "board/boardContent.jsp";
			
		} else if(command.equals("/boardModifyView.do")) {
			service = new BoardModifyViewService();
			service.execute(request, response);
			viewPage = "board/boardModify.jsp";
			
		} else if(command.equals("/boardModify.do")) {
			service = new BoardModifyService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		} else if(command.equals("/boardDelete.do")) {
			service = new BoardDeleteService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		} else if(command.equals("/boardReplyView.do")) {
			service = new BoardReplyViewService();
			service.execute(request, response);
			viewPage = "board/boardReply.jsp";
			
		} else if(command.equals("/boardReply.do")) {
			service = new BoardReplyService();
			service.execute(request, response);
			viewPage = "boardList.do";
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * 
		 * * * * * * * * 공지사항 게시판 관련 요청  * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * */		
		} else if (command.equals("/noticeList.do")) {
            service = new NoticeListService();
            service.execute(request, response);
            viewPage = "notice/noticeList.jsp";
            
        } else if (command.equals("/noticeContent.do")) {
            service = new NoticeContentService();
            service.execute(request, response);
            viewPage = "notice/noticeContent.jsp";
            
        } else if (command.equals("/noticeWriteView.do")) {
            viewPage = "notice/noticeWrite.jsp";
            
		} else if(command.equals("/noticeWrite.do")) {
			service = new NoticeWriteService();
			service.execute(request, response);
			viewPage = "noticeList.do";
			
		} else if(command.equals("/noticeModifyView.do")) {
			service = new NoticeModifyViewService();
			service.execute(request, response);
			viewPage = "notice/noticeModify.jsp";
			
        } else if (command.equals("/noticeModify.do")) {
            service = new NoticeModifyService();
            service.execute(request, response);
            viewPage = "noticeList.do";
            
        } else if (command.equals("/noticeDelete.do")) {
            service = new NoticeDeleteService();
            service.execute(request, response);
            viewPage = "noticeList.do";
        }
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);		
	}
}
