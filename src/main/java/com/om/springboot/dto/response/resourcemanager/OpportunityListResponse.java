package com.om.springboot.dto.response.resourcemanager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.model.resourcemanager.Opportunity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpportunityListResponse extends ApiResponse {

    private List<OpportunityList> opportunityList;

    public OpportunityListResponse(Boolean success, String message) {
        super(success, message);
    }
}
