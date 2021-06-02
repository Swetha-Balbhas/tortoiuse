package com.om.springboot.controller.request.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 4, max = 40)
    private String name;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 3, max = 15)
    private String username;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(max = 40)
    private String email;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 6, max = 80)
    private String password;
}
