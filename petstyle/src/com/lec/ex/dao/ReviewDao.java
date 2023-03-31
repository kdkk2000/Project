package com.lec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lec.ex.dto.ReviewDto;

public class ReviewDao {
	private static ReviewDao instance = new ReviewDao();
	private DataSource ds;
	public static ReviewDao getInstance() {
	    return instance;
	}

	private ReviewDao() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
	}

	// 1. 상품별 리뷰 전체 조회
	public List<ReviewDto> selectAll(int productId) {
	    List<ReviewDto> list = new ArrayList<ReviewDto>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT * FROM PRODUCT_REVIEW WHERE productId = ?";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, productId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int reviewId = rs.getInt("reviewId");
	            String reviewContent = rs.getString("reviewContent");
	            int reviewRating = rs.getInt("reviewRating");
	            Date reviewDate = rs.getDate("reviewDate");
	            String userId = rs.getString("userId");

	            ReviewDto dto = new ReviewDto(reviewId, reviewContent, reviewRating, reviewDate, productId, userId);
	            list.add(dto);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return list;
	}

	// 2. 리뷰 등록
	public int insert(ReviewDto dto) throws SQLException {
	    int result = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = "INSERT INTO PRODUCT_REVIEW VALUES (Product_review_seq.nextval, ?, ?, SYSDATE, ?, ?)";
	    try {
	        conn = ds.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getReviewContent());
	        pstmt.setInt(2, dto.getReviewRating());
	        pstmt.setInt(3, dto.getProductId());
	        pstmt.setString(4, dto.getUserId());

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return result;
	}
	// 3. 리뷰 수정
    public int update(ReviewDto dto) throws SQLException {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE PRODUCT_REVIEW SET reviewContent=?, reviewRating=? WHERE reviewId=?";
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getReviewContent());
            pstmt.setInt(2, dto.getReviewRating());
            pstmt.setInt(3, dto.getReviewId());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
	// 4. 리뷰 삭제
	public int delete(int reviewId, String userId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM PRODUCT_REVIEW WHERE reviewId = ? AND userId = ?";
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            pstmt.setString(2, userId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
