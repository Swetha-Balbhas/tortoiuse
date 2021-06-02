package com.om.springboot.mappers;

import com.om.springboot.dto.Test;
import com.om.springboot.model.TestUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestUserMapper {

    void insertTestUser(TestUser testUser);
    TestUser findUserById(String emailId);
}
