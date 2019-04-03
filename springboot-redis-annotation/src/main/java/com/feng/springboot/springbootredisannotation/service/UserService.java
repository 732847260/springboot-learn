package com.feng.springboot.springbootredisannotation.service;

import com.feng.springboot.springbootredisannotation.damain.User;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 16:20 2019/3/22
 * @Modified By: LiangZF
 */
public interface UserService {
    User getUserByUserName(String username);
}
