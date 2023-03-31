package com.lec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lec.ex.dto.AdminDto;
import com.lec.ex.dto.ProductDto;

public class ProductDao {
    private static ProductDao instance = new ProductDao();
    private DataSource ds;
    
    public static ProductDao getInstance() {
        return instance;
    }

	private ProductDao() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		}
	}

    // 1. 상품 전체 조회
    public List<ProductDto> selectAll() {
        List<ProductDto> list = new ArrayList<ProductDto>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Product ORDER BY productId DESC";
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                String productImage = rs.getString("productImage");

                ProductDto dto = new ProductDto(productId, productName, productDescription, productImage);
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

    // 2. 상품 상세 조회
    public ProductDto selectById(int productId) {
        ProductDto dto = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Product WHERE productId = ?";
        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String productName = rs.getString("productName");
                String productDescription = rs.getString("productDescription");
                String productImage = rs.getString("productImage");

                dto = new ProductDto(productId, productName, productDescription, productImage);
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

        return dto;
    }

    // 3. 상품 등록
    public int insert(ProductDto dto, String adminId, String adminPw) throws SQLException {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        AdminDto adminDto = AdminDao.getInstance().getAdmin(adminId);
        String sql = "INSERT INTO Product VALUES (Product_seq.nextval, ?, ?, ?)";
        if (adminDto != null && adminDto.getAdminPw().equals(adminPw)) {
            try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dto.getProductName());
                pstmt.setString(2, dto.getProductDescription());
                pstmt.setString(3, dto.getProductImage());

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
        }

        return result;
    }

    // 4. 상품 수정
    public int update(ProductDto dto, String adminId, String adminPw) throws SQLException {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        AdminDto adminDto = AdminDao.getInstance().getAdmin(adminId);
        String sql = "UPDATE Product SET productName=?, productDescription=?, productImage=? WHERE productId=?";
        if (adminDto != null && adminDto.getAdminPw().equals(adminPw)) {
            try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dto.getProductName());
                pstmt.setString(2, dto.getProductDescription());
                pstmt.setString(3, dto.getProductImage());
                pstmt.setInt(4, dto.getProductId());

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
        }

        return result;
    }

    // 5. 상품 삭제
    public int delete(int productId, String adminId, String adminPw) throws SQLException {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        AdminDto adminDto = AdminDao.getInstance().getAdmin(adminId);
        String sql = "DELETE FROM Product WHERE productId = ?";
        if (adminDto != null && adminDto.getAdminPw().equals(adminPw)) {
            try {
                conn = ds.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, productId);

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
        }

        return result;
   }
}
