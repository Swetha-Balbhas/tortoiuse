package com.om.springboot.mappers.resourcemanager;

import com.om.springboot.model.resourcemanager.Opportunity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpportunityMapper {
    Boolean insertOpportunities(Opportunity opportunity);

    Long getMaxToken();

    List<Opportunity> getAllOpportunityDetails();

    Boolean updateJobOpportunityByToken(Opportunity opportunity);

    Boolean updateStatusByToken(Opportunity opportunity);

    Boolean findByToken(Long token);

    List<Opportunity> getOpenedJobOpportunities();

    Opportunity getJobOpeningByToken(Long token);
    Boolean updateApplicationReceivedByToken(Opportunity opportunity);
}
