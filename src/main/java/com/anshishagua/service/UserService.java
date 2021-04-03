package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.UserMapper;
import com.anshishagua.object.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService.java
 *
 * @author lixiao
 * @date 2021-03-25
 */

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(String username, String password) {
        return userMapper.getByUsernamePassword(username, password);
    }

    public User getByName(String username) {
        return userMapper.getByUsername(username);
    }

    public void addUser(String username, String password, String gender) {
        userMapper.addUser(username, password, gender);
    }
}
