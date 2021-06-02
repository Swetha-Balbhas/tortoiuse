package com.om.springboot.controller.ui.user;

import com.om.springboot.dto.response.Response;
import com.om.springboot.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user/checkUsernameAvailability")
    public Response checkUsernameAvailability(@RequestBody @Valid @RequestParam(value = "username") String username) {
        Boolean isUserExists = userService.existsByUsername(username);
        if(isUserExists) {
            return Response.ok();
        }else{
            return Response.notFound();
        }
    }

    @GetMapping("/user/checkEmailAvailability")
    public Response checkEmailAvailability(@RequestBody @Valid @RequestParam(value = "email") String email) {
        Boolean isUserExists = userService.existsByEmail(email);
        if(isUserExists) {
            return Response.ok();
        }else{
            return Response.notFound();
        }
    }
}
