package com.om.springboot.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunityRequest {


    private Long token;

    @NotEmpty(message="{constraints.NotEmpty.message}")
    @Size(min = 1, max = 75)
    private String role;

    @NotEmpty(message="{constraints.NotEmpty.message}")
    @Size(min = 1, max = 55)
    private String department;

    @NotEmpty(message="{constraints.NotEmpty.message}")
    @Size(min = 1, max = 500)
    private String jobDescription;

    @NotNull
    private Long positions;

    @NotEmpty(message="{constraints.NotEmpty.message}")
    @Size(min = 1, max = 500)
    private String skills;

    @NotEmpty(message="{constraints.NotEmpty.message}")
    @Size(min = 1, max = 500)
    private String location;

    @NotEmpty(message="{constraints.NotEmpty.message}")
    @Size(min = 1, max = 75)
    private String experience;

    private String status;

}
