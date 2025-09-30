package com.hutech.BaiTap3Java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    // Phương thức này chỉ trả về view, không cần truyền model nữa
    @GetMapping
    public String showCart() {
        return "/cart/cart"; // Trả về trang cart.html
    }
}