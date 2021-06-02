package com.om.springboot.service;

import com.om.springboot.dto.model.test.TestUserDto;
import com.om.springboot.exception.ApplicationException;
import com.om.springboot.exception.EntityType;
import com.om.springboot.exception.ExceptionType;
import com.om.springboot.mappers.TestUserMapper;
import com.om.springboot.model.TestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.om.springboot.exception.EntityType.TESTUSER;
import static com.om.springboot.exception.ExceptionType.DUPLICATE_ENTITY;


@Component
public class TestUserServiceImpl implements TestUserService {


    @Autowired
    @Qualifier("testUserMapper")
    private TestUserMapper testUserMapper;

    @Override
    public TestUserDto signup(TestUserDto testUserDto){
        TestUser testUser = testUserMapper.findUserById(testUserDto.getEmailId());
        if (testUser == null) {
            testUser = new TestUser()
                    .setEmailId(testUserDto.getEmailId())
                    .setPassword(testUserDto.getPassword())
                    .setFirstName(testUserDto.getFirstName())
                    .setLastName(testUserDto.getLastName())
                    .setMobileNumber(testUserDto.getMobileNumber());
            testUserMapper.insertTestUser(testUser);
            return com.om.springboot.dto.mapper.TestUserMapper.toUserDto(testUser);
        }else if (testUser != null) {
            return com.om.springboot.dto.mapper.TestUserMapper.toUserDto(testUser);
        }
        throw exception(TESTUSER, DUPLICATE_ENTITY, testUserDto.getEmailId());
    }

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ApplicationException.throwException(entityType, exceptionType, args);
    }
}
