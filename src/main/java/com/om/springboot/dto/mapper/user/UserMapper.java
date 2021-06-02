package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.UserDto;
import com.om.springboot.model.user.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setId(user.getId())
                .setUsername(user.getUsername());
    }
}
