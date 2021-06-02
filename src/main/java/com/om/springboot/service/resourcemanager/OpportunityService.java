package com.om.springboot.service.resourcemanager;

import com.om.springboot.dto.model.resourcemanager.OpportunityDto;

import java.util.List;

public interface OpportunityService {
    public Boolean insertJobOpportunity(OpportunityDto opportunityDto);
    public  Long getMaxToken();
    List<OpportunityDto> getAllOpportunityList();
    public Boolean updateJobOpportunityByToken(OpportunityDto opportunityDto);
    public Boolean existsByToken(Long token);
    Boolean closeJobOpportunity(Long token);
    public List<OpportunityDto> getAllOpenedOpportunities();
    public OpportunityDto getJobOpeningByToken(Long token);
    Boolean updateApplicationReceived(OpportunityDto opportunityDto);
}
