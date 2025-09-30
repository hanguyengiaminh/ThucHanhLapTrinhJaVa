package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.CartItem;
import com.hutech.BaiTap3Java.model.Order;
import com.hutech.BaiTap3Java.service.CartService;
import com.hutech.BaiTap3Java.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String checkout(Model model) {
        // Lấy thông tin giỏ hàng để hiển thị
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getCartItems().stream()
                .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
                .sum());

        // Tạo một đối tượng Order trống để Thymeleaf binding dữ liệu từ form
        model.addAttribute("order", new Order());

        return "/cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(@Valid @ModelAttribute("order") Order order,
                              BindingResult bindingResult,
                              Model model) { // <-- 1. Thêm Model vào tham số

        List<CartItem> cartItems = cartService.getCartItems();

        // Kiểm tra nếu có lỗi validation
        if (bindingResult.hasErrors()) {
            // 2. NẾU CÓ LỖI: Gửi lại thông tin giỏ hàng về trang checkout
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", cartItems.stream()
                    .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
                    .sum());
            return "/cart/checkout"; // Quay lại trang checkout để hiển thị lỗi
        }

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        orderService.createOrder(order, cartItems);

        // Xóa giỏ hàng sau khi đã đặt hàng thành công
        cartService.clearCart();

        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Đơn hàng của bạn đã được đặt thành công.");
        return "cart/order-confirmation";
    }
}