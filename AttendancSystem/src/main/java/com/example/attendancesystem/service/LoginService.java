package com.example.attendancesystem.service;

import com.example.attendancesystem.mapper.UserMapper;
import com.example.attendancesystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpSession httpSession;
    public String getCurrentUserRole(){
        return (String) httpSession.getAttribute("currentUserRole");
    }

    public String register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return "Username already exists.";
        }

        user.setLocked(false);
        user.setLoginAttempts(0);

        // 插入新用户
        userMapper.insert(user);
        return "Registration successful.";
    }

    // 登录用户
    public String login(User user, HttpSession httpSession) {
        User foundUser = userMapper.findByUsername(user.getUsername());
        String role = foundUser.getRole();
        httpSession.setAttribute("currentUserRole", role);
        System.out.println(role);
        if (foundUser == null || foundUser.isLocked()) {
            return "Account locked or does not exist.";
        }

        // 验证密码
        if (!(user.getPassword().equals(foundUser.getPassword()))) {
            int attempts = foundUser.getLoginAttempts() + 1;
            boolean isLocked = attempts >= 3;
            userMapper.updateLoginAttempts(user.getUsername(), attempts, isLocked);
            return isLocked ? "Account locked after 3 failed attempts." : "Invalid credentials.";
        }

        // 登录成功，重置登录失败次数
        userMapper.updateLoginAttempts(user.getUsername(), 0, false);
        return "Login successful.";
    }
}

