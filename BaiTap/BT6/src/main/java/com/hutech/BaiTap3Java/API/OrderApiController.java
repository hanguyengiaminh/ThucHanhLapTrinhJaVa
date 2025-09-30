package com.hutech.BaiTap3Java.API;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.service.CartService;
import com.hutech.BaiTap3Java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitOrder(@RequestBody Map<String, String> payload) {
        String customerName = payload.get("customerName");
        List<CartItem> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Giỏ hàng của bạn đang trống.");
        }

        orderService.createOrder(customerName, cartItems);
        return ResponseEntity.ok("Đặt hàng thành công.");
    }
}