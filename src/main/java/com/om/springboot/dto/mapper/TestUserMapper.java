package com.om.springboot.dto.mapper;

import com.om.springboot.dto.model.test.TestUserDto;
import com.om.springboot.model.TestUser;

public class TestUserMapper {

    public static TestUserDto toUserDto(TestUser user) {
        return new TestUserDto()
                .setEmailId(user.getEmailId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber());
    }
}
