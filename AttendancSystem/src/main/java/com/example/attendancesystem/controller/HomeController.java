package com.example.attendancesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // 默认访问的主页直接跳转到登录页面
        return "redirect:/login.html";
    }
}

