package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.service.CartService;
import com.hutech.BaiTap3Java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String submitOrder(@RequestParam String customerName,
                              @RequestParam String address,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam String note,
                              @RequestParam String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }
        orderService.createOrder(customerName, address, phone, email, note, paymentMethod, cartItems);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Đơn hàng của bạn đã được đặt thành công!");
        return "cart/order-confirmation";
    }
}