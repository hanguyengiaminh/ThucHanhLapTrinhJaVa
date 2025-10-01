package com.hutech.BaiTap3Java.model;

import java.math.BigDecimal; // <-- Thêm import này

public class CartItem {
    private Product product;
    private int quantity;
    private BigDecimal price; // <-- Thay đổi kiểu dữ liệu từ double sang BigDecimal

    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice(); // <-- Bây giờ hai kiểu dữ liệu đã tương thích
    }

    // Getter cho product
    public Product getProduct() {
        return product;
    }

    // Setter cho product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter cho quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter cho quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // --- Cập nhật Getter và Setter cho price ---
    public BigDecimal getPrice() { // <-- Thay đổi kiểu trả về
        return price;
    }

    public void setPrice(BigDecimal price) { // <-- Thay đổi kiểu tham số
        this.price = price;
    }
}