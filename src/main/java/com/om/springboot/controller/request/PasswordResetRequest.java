package com.om.springboot.controller.request;

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
/**
 * 1. In reset page, give new password to the existing mobile number
 */
public class PasswordResetRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min =10, max =45 )
    private String email;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @Size(min = 6 ,max = 12)
    private String newPassword;


}