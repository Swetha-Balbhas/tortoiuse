package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.UserDto;
import com.om.springboot.exception.ApplicationException;
import com.om.springboot.exception.EntityType;
import com.om.springboot.exception.ExceptionType;
import com.om.springboot.mappers.user.UserMapper;
import com.om.springboot.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.om.springboot.exception.EntityType.TESTUSER;
import static com.om.springboot.exception.EntityType.USER;
import static com.om.springboot.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.om.springboot.exception.ExceptionType.ENTITY_NOT_FOUND;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public Boolean existsByUsername(String userName){
        Boolean isUserExists = userMapper.findByUsername(userName);
        if(isUserExists != null && isUserExists){
            return isUserExists;
        }else{
            return false;
        }
    }

    public Boolean existsByEmail(String emailId){
        Boolean isUserExists = userMapper.findByEmail(emailId);
        if(isUserExists != null && isUserExists){
            return isUserExists;
        }else{
            return false;
        }
    }

    public Boolean signup(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        Boolean isUserRegistered = userMapper.insertUser(user);
        return isUserRegistered;
    }

    public UserDto getUserByEmail(String emailId){
        User user = userMapper.getUserByEmail(emailId);
        if(user != null) {
            return com.om.springboot.dto.mapper.user.UserMapper.toUserDto(user);
        }
        throw exception(USER, ENTITY_NOT_FOUND,emailId);
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
