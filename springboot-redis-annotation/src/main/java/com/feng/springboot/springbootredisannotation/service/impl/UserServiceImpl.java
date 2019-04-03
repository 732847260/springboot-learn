package com.feng.springboot.springbootredisannotation.service.impl;

import com.feng.springboot.springbootredisannotation.damain.User;
import com.feng.springboot.springbootredisannotation.dao.UserMapper;
import com.feng.springboot.springbootredisannotation.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 16:21 2019/3/22
 * @Modified By: LiangZF
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public User getUserByUserName(String username) {
        System.out.println(username);
        return userMapper.getUserByUserName(username);
    }
}
