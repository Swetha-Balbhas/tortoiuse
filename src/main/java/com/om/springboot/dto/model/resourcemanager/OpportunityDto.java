package com.om.springboot.dto.model.resourcemanager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunityDto {
    private Long token;
    private String role;
    private String department;
    private String jobDescription;
    private Long positions;
    private String skills;
    private String location;
    private String experience;
    private String status;
    private Instant createdAt;
    private Long applicationCount;

}
