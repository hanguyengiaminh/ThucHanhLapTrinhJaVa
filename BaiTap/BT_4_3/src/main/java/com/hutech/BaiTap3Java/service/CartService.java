package com.hutech.BaiTap3Java.service;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {

    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    // Thêm sản phẩm vào giỏ
    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));
        cartItems.add(new CartItem(product, quantity));
    }

    // Lấy danh sách sản phẩm trong giỏ
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // Xóa sản phẩm khỏi giỏ
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    // Xóa toàn bộ giỏ hàng
    public void clearCart() {
        cartItems.clear();
    }
}

