package com.example.attendancesystem.mapper;

import com.example.attendancesystem.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 插入新用户
    @Insert("INSERT INTO users (username, password, role, isLocked, loginAttempts) " +
            "VALUES (#{username}, #{password}, #{role}, #{isLocked}, #{loginAttempts})")
    void insert(User user);

    // 根据用户名查找用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    // 更新登录失败次数和锁定状态
    @Update("UPDATE users SET loginAttempts = #{loginAttempts}, isLocked = #{isLocked} WHERE username = #{username}")
    void updateLoginAttempts(@Param("username") String username,
                             @Param("loginAttempts") int loginAttempts,
                             @Param("isLocked") boolean locked);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);


    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Long id);

}



