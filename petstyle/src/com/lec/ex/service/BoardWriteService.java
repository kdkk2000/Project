package com.lec.ex.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lec.ex.dao.BoardDao;
import com.lec.ex.dto.BoardDto;
import com.lec.ex.dto.UserDto;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteService implements Service {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// 파일첨부 로직 + 파라미터들 받아 DB에 join
		String path = request.getRealPath("fileBoardUp");
		int maxSize = 1024*1024*10; // 최대업로드 사이즈는 10M
		MultipartRequest mRequest = null;
		String boardFileName = "";
		try {
			mRequest = new MultipartRequest(request, path, maxSize, "utf-8", new DefaultFileRenamePolicy());
			Enumeration<String> params = mRequest.getFileNames();
			String param = params.nextElement();
			boardFileName = mRequest.getFilesystemName(param);
			HttpSession httpSession = request.getSession();
			UserDto user = (UserDto)httpSession.getAttribute("user");
			if(user!=null) {
				String userId = user.getUserId(); // 로그인 한 사람의 userId
				String boardTitle = mRequest.getParameter("boardTitle");
				String boardContent = mRequest.getParameter("boardContent");
				String boardIp = request.getRemoteAddr();
				BoardDao boardDao = BoardDao.getInstance();
				BoardDto boardDto = new BoardDto(0, userId, null, boardTitle, boardContent, boardFileName, null, maxSize, 0, 0, 0, boardIp);
				int result = boardDao.writeBoard(boardDto);
				// joinUser결과에 따라 적절히 request.setAttribute
				if(result == BoardDao.SUCCESS) { // 회원가입 진행
					request.setAttribute("boareResult", "글쓰기 성공");
				}else {
					request.setAttribute("boareResult", "글쓰기 실패");
				}
			}else {
				request.setAttribute("boardResult", "로그인 한 사람만 글쓸 수 있어요");
			}
		} catch (IOException e) {
			System.out.println("BoardWriteService - " + e.getMessage());
			request.setAttribute("boardResult", "글쓰기 실패");
		}
		// 서버에 올라간 fileboardUp 파일을 소스폴더에 filecopy
		if(boardFileName != null) {
		    InputStream is = null;
		    OutputStream os = null;
		    try {
		        File serverFile = new File(path + "/" + boardFileName);
		        if (!serverFile.exists() || !serverFile.isFile()) {
		            throw new Exception("서버에 업로드된 파일을 찾을 수 없습니다.");
		        }
		        is = new FileInputStream(serverFile);
		        os = new FileOutputStream("C:/webPro1/source/08_project/petstyle/WebContent/fileBoardUp/" + boardFileName);
		        byte[] bs = new byte[(int)serverFile.length()];
		        while(true) {
		            int nByteCnt = is.read(bs);
		            if(nByteCnt == -1) break;
		            os.write(bs, 0, nByteCnt);
		        }
		        request.setAttribute("boardResult", "글쓰기 성공");
		    } catch (Exception e) {
		        System.out.println("BoardWriteService - " + e.getMessage());
		        request.setAttribute("boardResult", "글쓰기 실패");
		    } finally {
		        try {
		            if(os != null) os.close();
		            if(is != null) is.close();
		        } catch (Exception e) {
		            System.out.println("BoardWriteService - " + e.getMessage());
		        }
		    } // try
		}// 파일 복사 if
	}
}