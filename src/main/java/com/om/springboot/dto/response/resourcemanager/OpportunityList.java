package com.om.springboot.dto.response.resourcemanager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunityList {
    private Long token;
    private String role;
    private String department;
    private String jobDescription;
    private Long positions;
    private String skills;
    private String location;
    private String experience;
    private String status;
    private Long applicationReceived;
    private String createdOn;


}
