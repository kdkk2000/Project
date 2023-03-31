package com.lec.ex.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.lec.ex.dto.BoardDto;

public class BoardDao {
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	private DataSource ds;
	// 싱글톤
	private static BoardDao instance = new BoardDao();
	public static BoardDao getInstance() {
		return instance;
	}
	private BoardDao() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
	}
	// (1) 글목록(startRow~endRow)
	public ArrayList<BoardDto> listBoard(int startRow, int endRow){
	    ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();
	    Connection        conn  = null;
	    PreparedStatement pstmt = null;
	    ResultSet         rs    = null;
	    String sql = "SELECT * FROM" + 
	    		"  (SELECT ROWNUM RN, A.* FROM (SELECT F.*, USERNAME FROM FILEBOARD F, USERS U" + 
	    		"                              WHERE F.USERID=U.USERID" + 
	    		"                              ORDER BY BOARDGROUP DESC, BOARDSTEP) A)" + 
	    		"  WHERE RN BETWEEN ? AND ?";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, startRow);
	        pstmt.setInt(2, endRow);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            int    boardId      = rs.getInt("boardId");
	            String userId      = rs.getString("userId");
	            String userName    = rs.getString("userName");
	            String boardTitle   = rs.getString("boardTitle");
	            String boardContent = rs.getString("boardContent");
	            String boardFileName= rs.getString("boardFileName");
	            Timestamp boardDate= rs.getTimestamp("boardDate");
	            int    boardHit     = rs.getInt("boardHit");
	            int    boardGroup   = rs.getInt("boardGroup");
	            int    boardStep    = rs.getInt("boardStep");
	            int    boardIndent  = rs.getInt("boardIndent");
	            String boardIp      = rs.getString("boardIp");
	            dtos.add(new BoardDto(boardId, userId, userName, boardTitle, boardContent,
	                    boardFileName, boardDate, boardHit, boardGroup, boardStep, boardIndent, boardIp));
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    } finally {
	        try {
	            if(rs    != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(conn  != null) conn.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        } 
	    }
	    return dtos;
	}
	// (2) 글갯수
	public int getBoardTotCnt() {
		int totCnt = 0;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		String sql = "SELECT COUNT(*) FROM FILEBOARD";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			totCnt = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs    != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return totCnt;
	}
	// (3) 글쓰기(원글쓰기)
	public int writeBoard(BoardDto dto) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO FILEBOARD (boardId, userId, boardTitle, boardContent, boardFileName, "
				+ 							" boardGroup, boardStep, boardIndent, boardIp)" + 
				"  VALUES (FILEBOARD_SEQ.NEXTVAL, ?, ?, ?, ?, " + 
				"    FILEBOARD_SEQ.CURRVAL, 0, 0, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBoardTitle());
			pstmt.setString(3, dto.getBoardContent());
			pstmt.setString(4, dto.getBoardFileName());
			pstmt.setString(5, dto.getBoardIp());
			pstmt.executeUpdate();
			result = SUCCESS;
			System.out.println("원글쓰기 성공");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " 원글쓰기 실패 :");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return result;
	}
	// (4) hit 1회 올리기
	private void hitUpBoard(int boardId) {
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE FILEBOARD SET boardHit = boardHit + 1 WHERE boardId = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " 조회수 up 실패");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
	}
	// (5) 글번호(boardId)로 글전체 내용(BoardDto) 가져오기 - 글상세보기용
	public BoardDto contentBoard(int boardId) {
		BoardDto dto = null;
		hitUpBoard(boardId); // 글 상세보기 시 조회수 1 올리기
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		String sql = "SELECT F.*, USERNAME" + 
				"  FROM FILEBOARD F, USERS U WHERE F.USERID=U.USERID AND BOARDID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String userId = rs.getString("userId");
				String userName = rs.getString("userName");
				String boardTitle = rs.getString("boardTitle");
				String boardContent = rs.getString("boardContent");
				String boardFileName = rs.getString("boardFileName");
				Timestamp boardDate = rs.getTimestamp("boardDate");
				int    boardHit = rs.getInt("boardHit");
				int    boardGroup= rs.getInt("boardGroup");
				int    boardStep= rs.getInt("boardStep");
				int    boardIndent= rs.getInt("boardIndent");
				String boardIp = rs.getString("boardIp");
				dto = new BoardDto(boardId, userId, userName, boardTitle, boardContent,boardFileName,  
							boardDate, boardHit, boardGroup, boardStep, boardIndent, boardIp);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs    != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return dto;
	}
	// (5) 글번호(boardId)로 글전체 내용(BoardDto) 가져오기 - 글상세보기용
	public BoardDto modifyViewBoard_replyViewBoard(int boardId) {
		BoardDto dto = null;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		String sql = "SELECT F.*, USERNAME" + 
				"  FROM FILEBOARD F, USERS U WHERE F.USERID=U.USERID AND BOARDID=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String userId = rs.getString("userId");
				String userName = rs.getString("userName");
				String boardTitle = rs.getString("boardTitle");
				String boardContent = rs.getString("boardContent");
				String boardFileName = rs.getString("boardFileName");
				Timestamp boardDate = rs.getTimestamp("boardDate");
				int    boardHit = rs.getInt("boardHit");
				int    boardGroup= rs.getInt("boardGroup");
				int    boardStep= rs.getInt("boardStep");
				int    boardIndent= rs.getInt("boardIndent");
				String boardIp = rs.getString("boardIp");
				dto = new BoardDto(boardId, userId, userName, boardTitle, boardContent,boardFileName,  
							boardDate, boardHit, boardGroup, boardStep, boardIndent, boardIp);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs    != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return dto;
	}
	// (6) 글 수정하기(boardId, boardTitle, boardContent, boardFileName, boardDate(SYSDATE), boardIp 수정)
	public int modifyBoard(BoardDto dto) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE FILEBOARD SET boardTitle = ?," + 
				"                    boardContent = ?," + 
				"                    boardFileName = ?," + 
				"                    boardIp = ?," + 
				"                    boardDate = SYSDATE" + 
				"            WHERE boardId = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardTitle());
			pstmt.setString(2, dto.getBoardContent());
			pstmt.setString(3, dto.getBoardFileName());
			pstmt.setString(4, dto.getBoardIp());
			pstmt.setInt(5, dto.getBoardId());
			result = pstmt.executeUpdate();
			System.out.println(result == SUCCESS ? "글수정 성공":"글번호(boardId) 오류");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "글 수정 실패 ");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return result;
	}
	// (7) 글 삭제하기
	public int deleteBoard(int boardId) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM FILEBOARD WHERE boardId = ?";
		try {
		    conn = ds.getConnection();
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, boardId);
		    result = pstmt.executeUpdate();
		    System.out.println(result == SUCCESS ? "글삭제 성공":"글번호(boardId) 오류");
		} catch (SQLException e) {
		    System.out.println(e.getMessage() + "글 삭제 실패 ");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return result;
	}

	// (8) 답변글 쓰기 전 단계(원글의 boardGroup과 같고, 원글의 boardStep보다 크면 boardStep을 하나 증가하기)
	private void preReplyBoardStep(int boardGroup, int boardStep) {
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE FILEBOARD SET boardStep = boardStep + 1 WHERE boardGroup=? AND boardStep>?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardGroup);
			pstmt.setInt(2, boardStep);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " preReplyStep에서 오류");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
	}
	// (9) 답변글 쓰기
	public int reply(BoardDto dto) {
		int result = FAIL;
		preReplyBoardStep(dto.getBoardGroup(), dto.getBoardStep());
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO FILEBOARD (boardId, userId, boardTitle, boardContent," + 
						"boardFileName, boardGroup, boardStep, boardIndent, boardIp)" + 
						"VALUES (FILEBOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getBoardTitle());
			pstmt.setString(3, dto.getBoardContent());
			pstmt.setString(4, dto.getBoardFileName());
			pstmt.setInt(5, dto.getBoardGroup());
			pstmt.setInt(6, dto.getBoardStep() + 1);
			pstmt.setInt(7, dto.getBoardIndent() + 1);
			pstmt.setString(8, dto.getBoardIp());
			pstmt.executeUpdate();
			result = SUCCESS;
			System.out.println("답변글쓰기 성공");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " 답변글쓰기 실패 ");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return result;
	}
	// (10) 회원탈퇴시 탈퇴하는 회원(userId)이 쓴 글 모두 삭제하기
	public int preWithdrawalUserStep(String userId) {
		int cntBoard = 0;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM FILEBOARD WHERE userId = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			cntBoard = pstmt.executeUpdate();
			System.out.println(cntBoard+"개글 삭제 성공(회원탈퇴전)");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "탈퇴 전 글 삭제 실패 ");
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn  != null) conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} 
		}
		return cntBoard;
	}
}