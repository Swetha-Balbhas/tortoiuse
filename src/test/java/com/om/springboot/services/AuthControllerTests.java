package com.om.springboot.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.om.springboot.controller.request.auth.LoginRequest;
import com.om.springboot.controller.request.auth.SignUpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAuthSignInAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.post("/api/auth/signin")
                .content(asJsonString(new LoginRequest().setUsernameOrEmail("siva@sc.com").setPassword("test")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //@Test
    //Commenting this because user will exists
    public void testAuthSignUpAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.post("/api/auth/signup")
                .content(asJsonString(new SignUpRequest().setName("testname").setUsername("Testusername").setEmail("testemail@test.com").setPassword("testpassword")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
