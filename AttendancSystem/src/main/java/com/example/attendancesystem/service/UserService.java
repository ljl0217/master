package com.example.attendancesystem.service;

import com.example.attendancesystem.mapper.UserMapper;
import com.example.attendancesystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginService loginService;

    // 获取所有用户
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    // 添加用户（根据角色权限）
    public String addUser(User user) {
        String currentUserRole = loginService.getCurrentUserRole(); // 从 Session 获取当前用户的角色
        System.out.println(currentUserRole);
        if ("PM".equals(currentUserRole) && "PG".equals(user.getRole())) {
            user.setLocked(false);
            user.setLoginAttempts(0);
            userMapper.insert(user);
            return "用户添加成功";
        } else if ("SYSTEM".equals(currentUserRole) && "PM".equals(user.getRole())) {
            user.setLocked(false);
            user.setLoginAttempts(0);
            userMapper.insert(user);
            return "用户添加成功";
        }
        return "无权限添加该角色用户";
    }

    // 删除用户（根据角色权限）
    public String deleteUser(Long id) {
        User user = userMapper.findById(id);
        String currentUserRole = loginService.getCurrentUserRole(); // 从 Session 获取当前用户的角色

        if ("PM".equals(currentUserRole) && "PG".equals(user.getRole())) {
            userMapper.delete(id);
            return "用户删除成功";
        } else if ("SYSTEM".equals(currentUserRole) && "PM".equals(user.getRole())) {
            userMapper.delete(id);
            return "用户删除成功";
        }
        return "无权限删除该用户";
    }
}


