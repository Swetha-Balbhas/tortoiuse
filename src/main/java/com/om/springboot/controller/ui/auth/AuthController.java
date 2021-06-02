package com.om.springboot.controller.ui.auth;

import com.om.springboot.controller.request.auth.LoginRequest;
import com.om.springboot.controller.request.auth.SignUpRequest;
import com.om.springboot.dto.model.user.UserDto;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.auth.JwtAuthenticationResponse;
import com.om.springboot.security.JwtTokenProvider;
import com.om.springboot.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        Boolean isUserRegistered = registerUser(signUpRequest, false);
        if(isUserRegistered) {
            return new ResponseEntity(new ApiResponse(true, "User registered successfully!"),
                    HttpStatus.OK);
        }else {
            return new ResponseEntity(new ApiResponse(false, "User not registered"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Register a new user in the database
     *
     * @param signUpRequest
     * @return
     */
    private Boolean registerUser(SignUpRequest signUpRequest, boolean isAdmin) {
        UserDto userDto = new UserDto()
                .setEmail(signUpRequest.getEmail())
                .setUsername(signUpRequest.getUsername())
                .setName(signUpRequest.getName())
                .setPassword(signUpRequest.getPassword());
        return userService.signup(userDto);

    }
}
