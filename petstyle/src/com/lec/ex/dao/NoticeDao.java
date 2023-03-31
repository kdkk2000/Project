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
import com.lec.ex.dto.NoticeDto;

public class NoticeDao {
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	private DataSource ds;
	// 싱글톤
	private static NoticeDao instance = new NoticeDao();
	public static NoticeDao getInstance() {
		return instance;
	}
	private NoticeDao() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
	}
	// (1) 글목록
	public ArrayList<NoticeDto> listNotice(int startRow, int endRow){
	    ArrayList<NoticeDto> dtos = new ArrayList<NoticeDto>();
	    Connection        conn  = null;
	    PreparedStatement pstmt = null;
	    ResultSet         rs    = null;
	    String sql = "SELECT * FROM" + 
	    		"  (SELECT ROWNUM NUM, NUM.* FROM (SELECT N.*, adminName FROM NOTICE N, ADMINS A" + 
	    		"                              WHERE N.adminId=A.adminId" + 
	    		"                              ORDER BY noticeGroup DESC, noticeStep) NUM)" + 
	    		"  WHERE NUM BETWEEN ? AND ?";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, startRow);
	        pstmt.setInt(2, endRow);
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            int    noticeId      = rs.getInt("noticeId");
	            String adminId      = rs.getString("adminId");
	            String adminName    = rs.getString("adminName");
	            String noticeTitle   = rs.getString("noticeTitle");
	            String noticeContent = rs.getString("noticeContent");
	            String noticeFileName= rs.getString("noticeFileName");
	            Timestamp noticeDate= rs.getTimestamp("noticeDate");
	            int    noticeHit     = rs.getInt("noticeHit");
	            int    noticeGroup   = rs.getInt("noticeGroup");
	            int    noticeStep    = rs.getInt("noticeStep");
	            int    noticeIndent  = rs.getInt("noticeIndent");
	            String noticeIp      = rs.getString("noticeIp");
	            dtos.add(new NoticeDto(noticeId, adminId, adminName, noticeTitle, noticeContent,
	                    noticeFileName, noticeDate, noticeHit, noticeGroup, noticeStep, noticeIndent, noticeIp));
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
	public int getNoticeTotCnt() {
		int totCnt = 0;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		String sql = "SELECT COUNT(*) FROM NOTICE";
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
	public int writeNotice(NoticeDto dto) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO NOTICE (noticeId, adminId, noticeTitle, noticeContent, noticeFileName, noticeGroup, noticeStep, noticeIndent, noticeIp)" + 
					"  VALUES (NOTICE_SEQ.NEXTVAL, ?, ?, ?, ?, " + 
					"    NOTICE_SEQ.CURRVAL, 0, 0, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getadminId());
			pstmt.setString(2, dto.getNoticeTitle());
			pstmt.setString(3, dto.getNoticeContent());
			pstmt.setString(4, dto.getNoticeFileName());
			pstmt.setString(5, dto.getNoticeIp());
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
	private void hitUpBoard(int noticeId) {
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE NOTICE SET noticeHit = noticeHit + 1 WHERE noticeId = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeId);
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
	// (5) 글번호(noticeId)로 글전체 내용(NoticeDto) 가져오기 - 글상세보기용
	public NoticeDto contentNotice(int noticeId) {
		NoticeDto dto = null;
		hitUpBoard(noticeId); // 글 상세보기 시 조회수 1 올리기
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		String sql = "SELECT N.*, adminName" + 
				"  FROM NOTICE N, ADMINS A WHERE N.adminId=A.adminId AND noticeId=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String adminId = rs.getString("adminId");
				String adminName = rs.getString("adminName");
				String noticeTitle = rs.getString("noticeTitle");
				String noticeContent = rs.getString("noticeContent");
				String noticeFileName = rs.getString("noticeFileName");
				Timestamp noticeDate = rs.getTimestamp("noticeDate");
				int    noticeHit = rs.getInt("noticeHit");
				int    noticeGroup= rs.getInt("noticeGroup");
				int    noticeStep= rs.getInt("noticeStep");
				int    noticeIndent= rs.getInt("noticeIndent");
				String noticeIp = rs.getString("noticeIp");
				dto = new NoticeDto(noticeId, adminId, adminName, noticeTitle, noticeContent,noticeFileName,  
							noticeDate, noticeHit, noticeGroup, noticeStep, noticeIndent, noticeIp);
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
	// (6) 글번호(noticeId)로 글전체 내용(NoticeDto) 가져오기 - 글수정VIEW, 답변글VIEW 용
	public NoticeDto modifyViewNotice_replyViewNotice(int noticeId) {
		NoticeDto dto = null;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		ResultSet         rs    = null;
		String sql = "SELECT N.*, adminName" + 
				"  FROM NOTICE N, ADMINS A WHERE N.adminId=A.adminId AND noticeId=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String adminId = rs.getString("adminId");
				String adminName = rs.getString("adminName");
				String noticeTitle = rs.getString("noticeTitle");
				String noticeContent = rs.getString("noticeContent");
				String noticeFileName = rs.getString("noticeFileName");
				Timestamp noticeDate = rs.getTimestamp("noticeDate");
				int    noticeHit = rs.getInt("noticeHit");
				int    noticeGroup= rs.getInt("noticeGroup");
				int    noticeStep= rs.getInt("noticeStep");
				int    noticeIndent= rs.getInt("noticeIndent");
				String noticeIp = rs.getString("noticeIp");
				dto = new NoticeDto(noticeId, adminId, adminName, noticeTitle, noticeContent,noticeFileName,  
							noticeDate, noticeHit, noticeGroup, noticeStep, noticeIndent, noticeIp);
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
	// (7) 글 수정하기(noticeId, noticeTitle, noticeContent, noticeFileName, noticeDate(SYSDATE), noticeIp 수정)
	public int modifyNotice(NoticeDto dto) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE NOTICE SET noticeTitle = ?," + 
				"                    noticeContent = ?," + 
				"                    noticeFileName = ?," + 
				"                    noticeIp = ?," + 
				"                    noticeDate = SYSDATE" + 
				"            WHERE noticeId = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getNoticeTitle());
			pstmt.setString(2, dto.getNoticeContent());
			pstmt.setString(3, dto.getNoticeFileName());
			pstmt.setString(4, dto.getNoticeIp());
			pstmt.setInt(5, dto.getNoticeId());
			result = pstmt.executeUpdate();
			System.out.println(result == SUCCESS ? "글수정 성공":"글번호(noticeId) 오류");
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
	// (8) 글 삭제하기(noticeId로)
	public int deleteNotice(int noticeId) {
		int result = FAIL;
		Connection        conn  = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM NOTICE WHERE noticeId = ?";
		try {
		    conn = ds.getConnection();
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, noticeId);
		    result = pstmt.executeUpdate();
		    System.out.println(result == SUCCESS ? "글삭제 성공":"글번호(noticeId) 오류");
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
}