package com.hutech.BaiTap3Java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    // Hiển thị trang thanh toán
    @GetMapping("/checkout")
    public String checkout() {
        return "/cart/checkout";
    }

    // Hiển thị trang xác nhận đơn hàng thành công
    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Đơn hàng của bạn đã được đặt thành công.");
        return "cart/order-confirmation";
    }
}