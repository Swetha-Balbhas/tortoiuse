package com.om.springboot.dto.response.resourcemanager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.om.springboot.dto.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOpeningResponse extends ApiResponse {

    private Long token;
    private String role;
    private String department;
    private String jobDescription;
    private Long positions;
    private String skills;
    private String location;
    private String experience;
    private String status;
    private String createdOn;


    public JobOpeningResponse(Boolean success, String message) {
        super(success, message);
    }
}
