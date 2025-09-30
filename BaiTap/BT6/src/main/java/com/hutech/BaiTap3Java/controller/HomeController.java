package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String home(Model model) {
        // Thêm một vài sản phẩm mẫu để hiển thị
        if (productService.getAllProducts().isEmpty()) {

        }
        model.addAttribute("products", productService.getAllProducts());
        return "products/user-product-list";
    }

    // Giữ lại trang admin home nhưng đổi đường dẫn
    @GetMapping("/admin")
    public String adminHome() {
        return "home-api";
    }
}