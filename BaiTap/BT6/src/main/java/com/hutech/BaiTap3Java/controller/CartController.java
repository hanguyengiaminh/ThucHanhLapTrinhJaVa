package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Hiển thị giỏ hàng
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        // Thêm tổng tiền và tổng số lượng vào model để hiển thị
        model.addAttribute("sumPrice", cartService.getSumPrice());
        model.addAttribute("sumQuantity", cartService.getSumQuantity());
        return "/cart/cart"; // Đường dẫn tới file Thymeleaf
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.addToCart(productId, quantity);
        return "redirect:/cart";
    }

    // Xóa sản phẩm khỏi giỏ
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    // Xóa toàn bộ giỏ hàng
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }

    // **PHƯƠNG THỨC MỚI: Cập nhật số lượng sản phẩm**
    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateCartItem(productId, quantity);
        return "redirect:/cart";
    }
}