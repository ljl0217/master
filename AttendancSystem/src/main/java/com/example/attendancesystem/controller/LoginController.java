package com.example.attendancesystem.controller;

import com.example.attendancesystem.model.User;
import com.example.attendancesystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;


    // 注册功能
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return loginService.register(user);
    }

    // 登录功能
    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession httpSession) {
        return loginService.login(user, httpSession);
    }

    @GetMapping("/currentRole")
    public ResponseEntity<String> getCurrentUserRole(HttpSession httpSession){
        String role = (String) httpSession.getAttribute("currentUserRole");
        System.out.println(role);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("无效的会话");
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logout successful.";
    }
}


