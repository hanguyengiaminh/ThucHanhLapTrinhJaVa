package com.hutech.BaiTap3Java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String root() {
        return "redirect:/home"; // Chuyển hướng đến trang chủ admin
    }

    @GetMapping("/home")
    public String adminHome() {
        return "home-api";
    }
}