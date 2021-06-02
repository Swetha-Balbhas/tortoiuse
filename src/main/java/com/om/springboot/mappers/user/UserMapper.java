package com.om.springboot.mappers.user;


import com.om.springboot.model.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Boolean insertUser(User user);
    User getUserByEmail(String Email);
    User getUserById(Long id);
    Boolean findByUsername(String userName);
    Boolean findByEmail(String Email);
}
