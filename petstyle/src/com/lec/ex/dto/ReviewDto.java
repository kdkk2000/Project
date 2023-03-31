package com.lec.ex.dto;

import java.sql.Date;

public class ReviewDto {
    private int reviewId;
    private String reviewContent;
    private int reviewRating;
    private Date reviewDate;
    private int productId;
    private String userId;

    public ReviewDto(int reviewId, String reviewContent, int reviewRating, Date reviewDate, int productId, String userId) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.productId = productId;
        this.userId = userId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
	@Override
	public String toString() {
		return "ReviewDto [reviewRating=" + reviewRating + ", reviewDate=" + reviewDate + ", productId=" + productId + ", userId=" + userId + ", reviewContent="
				+ reviewContent + "]";
	}
}
