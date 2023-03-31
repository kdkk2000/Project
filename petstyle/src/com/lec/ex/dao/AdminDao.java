package com.lec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.lec.ex.dto.AdminDto;

public class AdminDao {
	public static final int EXISTENT = 0;
	public static final int NONEXISTENT = 1;
	public static final int LOGIN_FAIL = 0;
	public static final int LOGIN_SUCCESS = 1;
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	private static AdminDao instance = new AdminDao();

	private DataSource ds;

	private AdminDao() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static AdminDao getInstance() {
		return instance;
	}
	// 1. 관리자id 중복체크
	public int adminIdConfirm(String adminId) {
		int result = EXISTENT;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ADMINS WHERE adminId=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adminId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = EXISTENT;
			} else {
				result = NONEXISTENT;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
	// 1. 관리자 등록
	public int joinAdmin(AdminDto admin) {
		int result = FAIL;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO ADMINS (adminId, adminPw, adminName, adminEmail, adminPhone)"
	    		+ "		VALUES (?, ?, ?, ?, ?)";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, admin.getAdminId());
	        pstmt.setString(2, admin.getAdminPw());
	        pstmt.setString(3, admin.getAdminName());
	        pstmt.setString(4, admin.getAdminEmail());
	        pstmt.setString(5, admin.getAdminPhone());
	        return pstmt.executeUpdate();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }
		return result;
	}

	// 2. 관리자 로그인
	public int login(String adminId, String adminPw) {
	    int result = LOGIN_FAIL;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM ADMINS WHERE adminId = ?";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, adminId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            String dbPw = rs.getString("adminPw");
	            if (dbPw.equals(adminPw)) {
	                result = LOGIN_SUCCESS;
	            }
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    return result;
	}
	
	// 3. 특정 아이디를 가진 관리자 보기
	public AdminDto getAdmin(String adminId) {
	    AdminDto admin = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM ADMINS WHERE adminId=?";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, adminId);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            admin = new AdminDto();
	            String adminPw = rs.getString("adminPw");
	            String adminName = rs.getString("adminName");
	            String adminEmail = rs.getString("adminEmail");
	            String adminPhone = rs.getString("adminPhone");
	            
	            admin = new AdminDto(adminId, adminPw, adminName, adminEmail, adminPhone);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    } finally {
	        try {
	            if(rs    != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(conn  != null) conn.close();
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    return admin;
	}

	// 4. 관리자 정보 변경
	public int modifyAdmin(AdminDto admin) {
	    int result = FAIL;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "UPDATE ADMINS SET adminPw=?, adminName=?, adminEmail=?, adminPhone=? WHERE adminId=?";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, admin.getAdminPw());
	        pstmt.setString(2, admin.getAdminName());
	        pstmt.setString(3, admin.getAdminEmail());
	        pstmt.setString(4, admin.getAdminPhone());
	        pstmt.setString(5, admin.getAdminId());
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    return result;
	}
}