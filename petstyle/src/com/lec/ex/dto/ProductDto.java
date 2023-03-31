package com.lec.ex.dto;

public class ProductDto {
    private int productId;
    private String productName;
    private String productDescription;
    private String productImage;

    public ProductDto() {}

    public ProductDto(int productId, String productName, String productDescription, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

	@Override
	public String toString() {
		return "ProductDto [productId=" + productId + ", productName=" + productName + ", productDescription=" + productDescription + ", productImage=" + productImage
				+ "]";
	}
}