package com.hutech.BaiTap3Java.service;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartService {

    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    /**
     * Thêm sản phẩm vào giỏ hàng.
     * Nếu sản phẩm đã có trong giỏ, tăng số lượng.
     * Nếu chưa có, thêm sản phẩm mới vào.
     *
     * @param productId Id của sản phẩm
     * @param quantity  Số lượng
     */
    public void addToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));

        // Tìm xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Nếu có, tăng số lượng
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Nếu không, thêm sản phẩm mới vào giỏ
            cartItems.add(new CartItem(product, quantity));
        }
    }

    /**
     * Lấy danh sách các sản phẩm trong giỏ hàng.
     *
     * @return Danh sách CartItem
     */
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * Xóa một sản phẩm khỏi giỏ hàng.
     *
     * @param productId Id của sản phẩm cần xóa
     */
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    /**
     * Cập nhật số lượng của một sản phẩm trong giỏ hàng.
     * Nếu số lượng <= 0, sản phẩm sẽ bị xóa.
     *
     * @param productId Id của sản phẩm
     * @param quantity  Số lượng mới
     */
    public void updateCartItem(Long productId, int quantity) {
        if (quantity <= 0) {
            removeFromCart(productId);
            return;
        }

        Optional<CartItem> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(quantity);
        }
    }

    /**
     * Xóa toàn bộ sản phẩm khỏi giỏ hàng.
     */
    public void clearCart() {
        cartItems.clear();
    }

    /**
     * Tính tổng thành tiền của giỏ hàng.
     *
     * @return Tổng tiền
     */
    public double getSumPrice() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Đếm tổng số lượng sản phẩm trong giỏ hàng.
     *
     * @return Tổng số lượng
     */
    public int getSumQuantity() {
        return cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}