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
public class RMLoginRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min=1, max=75)
    private String email;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(max = 12)
    private String password;
}
