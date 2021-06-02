package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.UserDto;

public interface UserService {

    /**
     * Check any user exists with the same user name
     *
     * @param userName
     * @return
     */
    Boolean existsByUsername(String userName);

    /**
     * Check any user exists with the same emailId
     *
     * @param emailId
     * @return
     */
    Boolean existsByEmail(String emailId);

    /**
     * Check any user exists with the same emailId
     *
     * @param emailId
     * @return
     */
    UserDto getUserByEmail(String emailId);

    /**
     * User registration
     *
     * @param userDto
     * @return
     */
    Boolean signup(UserDto userDto);
}
