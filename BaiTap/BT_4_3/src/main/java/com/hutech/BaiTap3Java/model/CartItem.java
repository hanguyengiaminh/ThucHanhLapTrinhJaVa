package com.hutech.BaiTap3Java.model;

public class CartItem {
    private Product product;
    private int quantity;

    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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
}

