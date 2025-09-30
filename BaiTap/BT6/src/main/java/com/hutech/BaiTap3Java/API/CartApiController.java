package com.hutech.BaiTap3Java.API;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartService cartService;

    // Lấy thông tin chi tiết của giỏ hàng (danh sách sản phẩm, tổng số lượng, tổng tiền)
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getCartDetails() {
        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cartService.getCartItems());
        response.put("sumPrice", cartService.getSumPrice());
        response.put("sumQuantity", cartService.getSumQuantity());
        return ResponseEntity.ok(response);
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> payload) {
        Long productId = Long.parseLong(payload.get("productId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        cartService.addToCart(productId, quantity);
        return ResponseEntity.ok().build();
    }

    // Cập nhật số lượng sản phẩm
    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItem(@RequestBody Map<String, Object> payload) {
        Long productId = Long.parseLong(payload.get("productId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        cartService.updateCartItem(productId, quantity);
        return ResponseEntity.ok().build();
    }

    // Xóa sản phẩm khỏi giỏ
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok().build();
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }
}