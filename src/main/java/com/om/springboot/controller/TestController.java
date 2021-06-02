package com.om.springboot.controller;

import com.om.springboot.controller.request.TestRequest;
import com.om.springboot.dto.model.test.TestUserDto;
import com.om.springboot.dto.response.Response;
import com.om.springboot.mappers.TestMapper;
import com.om.springboot.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    @Qualifier("testMapper")
    private TestMapper testMapper;

    @GetMapping("/testapi")
    public String testAPI() {
        return "Greetings from Spring Boot!";
    }

    @Autowired
    private TestUserService testUserService;

    @PostMapping("/api")
    public Response testapi(@RequestBody @Valid TestRequest testRequest) {
        return Response.ok().setPayload(registerUser(testRequest, false));
    }

    /**
     * Register a new user in the database
     *
     * @param testRequest
     * @return
     */
    private TestUserDto registerUser(TestRequest testRequest, boolean isAdmin) {
        TestUserDto testDto = new TestUserDto()
                .setEmailId(testRequest.getEmailId())
                .setPassword(testRequest.getPassword())
                .setFirstName(testRequest.getFirstName())
                .setLastName(testRequest.getLastName())
                .setMobileNumber(testRequest.getMobileNumber())
                .setAdmin(isAdmin);

        return testUserService.signup(testDto);
    }


}
