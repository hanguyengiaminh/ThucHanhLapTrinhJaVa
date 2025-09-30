package com.hutech.BaiTap3Java.controller;

import com.hutech.BaiTap3Java.model.User;
import com.hutech.BaiTap3Java.repository.IUserRepository; // Import IUserRepository
import com.hutech.BaiTap3Java.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final IUserRepository userRepository; // Thêm IUserRepository

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.user", "Tên đăng nhập đã được sử dụng");
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.user", "Email này đã được đăng ký");
        }

        // Kiểm tra xem số điện thoại đã tồn tại chưa
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            if (userRepository.findByPhone(user.getPhone()).isPresent()) {
                bindingResult.rejectValue("phone", "error.user", "Số điện thoại này đã được sử dụng");
            }
        }

        // Nếu có lỗi, trả lại form đăng ký và hiển thị lỗi
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register";
        }

        userService.save(user);
        userService.setDefaultRole(user.getUsername());
        return "redirect:/login";
    }
}