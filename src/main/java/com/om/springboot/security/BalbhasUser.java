package com.om.springboot.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class BalbhasUser {

    private Long id;
    private String email;
    private String role;
    private String password;

}
