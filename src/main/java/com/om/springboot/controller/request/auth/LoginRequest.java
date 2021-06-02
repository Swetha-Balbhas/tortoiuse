package com.om.springboot.controller.request.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String usernameOrEmail;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String password;
}
