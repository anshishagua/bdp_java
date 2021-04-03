package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper.java
 *
 * @author lixiao
 * @date 2021-03-25
 */

@Mapper
public interface UserMapper {
    User getByUsernamePassword(@Param("username") String username, @Param("password") String password);
    User getByUsername(@Param("username") String username);
    void addUser(@Param("username") String username, @Param("password") String password,
                 @Param("gender") String gender);
}
