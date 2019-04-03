package com.feng.springboot.springbootredisannotation.dao;

import com.feng.springboot.springbootredisannotation.damain.User;

public interface UserMapper {
    User getUserByUserName(String username);
}