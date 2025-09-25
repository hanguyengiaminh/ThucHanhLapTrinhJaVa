package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.model.Order;
import com.hutech.BaiTap3Java.model.Product;
import com.hutech.BaiTap3Java.service.CartService;
import com.hutech.BaiTap3Java.service.OrderService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @GetMapping("/checkout")
    public String checkout() {
        return "/cart/checkout";
    }
    @PostMapping("/submit")
    public String submitOrder(String customerName) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }
        orderService.createOrder(customerName, cartItems);
        return "redirect:/order/confirmation";
    }
    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Đơn hàng của bạn đã được đặt thành công.");
        return "cart/order-confirmation";
    }
}