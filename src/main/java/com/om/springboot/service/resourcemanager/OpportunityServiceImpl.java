package com.om.springboot.service.resourcemanager;


import com.om.springboot.dto.mapper.resourcemanager.OpportunityDtoMapper;
import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.mappers.resourcemanager.OpportunityMapper;
import com.om.springboot.model.resourcemanager.Opportunity;
import com.om.springboot.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Component
public class OpportunityServiceImpl implements OpportunityService {


    @Autowired
    @Qualifier("opportunityMapper")
    private OpportunityMapper opportunityMapper;


    @Override
    public Long getMaxToken() {
        Long token = opportunityMapper.getMaxToken();
        if (null != token) {
            return token + 1;
        } else {
            return AppConstants.DEFAULT_TOKEN;
        }
    }


    @Override
    @Transactional
    public synchronized Boolean insertJobOpportunity(OpportunityDto opportunityDto) {
        Opportunity opportunity = new Opportunity();
        Long token = this.getMaxToken();
        opportunity.setToken(token);
        opportunity.setRole(opportunityDto.getRole());
        opportunity.setDepartment(opportunityDto.getDepartment());
        opportunity.setExperience(opportunityDto.getExperience());
        opportunity.setJobDescription(opportunityDto.getJobDescription());
        opportunity.setLocation(opportunityDto.getLocation());
        opportunity.setPositions(opportunityDto.getPositions());
        opportunity.setSkills(opportunityDto.getSkills());
        opportunity.setStatus(opportunityDto.getStatus());
        opportunity.setCreatedAt(Instant.now());

        Boolean isInserted = opportunityMapper.insertOpportunities(opportunity);
        if (null != isInserted) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<OpportunityDto> getAllOpportunityList() {
        return OpportunityDtoMapper.toOpportunityDtoListy(opportunityMapper.getAllOpportunityDetails());
    }

    @Override
    public Boolean updateJobOpportunityByToken(OpportunityDto opportunityDto) {
        Opportunity opportunity = new Opportunity();
        opportunity.setToken(opportunityDto.getToken());
        opportunity.setRole(opportunityDto.getRole());
        opportunity.setDepartment(opportunityDto.getDepartment());
        opportunity.setLocation(opportunityDto.getLocation());
        opportunity.setStatus(opportunityDto.getStatus());
        opportunity.setSkills(opportunityDto.getSkills());
        opportunity.setJobDescription(opportunityDto.getJobDescription());
        opportunity.setPositions(opportunityDto.getPositions());
        opportunity.setExperience(opportunityDto.getExperience());
        opportunity.setStatus(opportunityDto.getStatus());
        return opportunityMapper.updateJobOpportunityByToken(opportunity);
    }

    @Override
    public Boolean existsByToken(Long token) {
        return opportunityMapper.findByToken(token);
    }

    @Override
    public Boolean closeJobOpportunity(Long token) {
        Opportunity opportunity = new Opportunity();
        opportunity.setToken(token);
        opportunity.setStatus(AppConstants.CLOSE_STATUS);
        return opportunityMapper.updateStatusByToken(opportunity);
    }

    @Override
    public List<OpportunityDto> getAllOpenedOpportunities() {
        return OpportunityDtoMapper.toOpportunityDtoListy(opportunityMapper.getOpenedJobOpportunities());
    }

    @Override
    public OpportunityDto getJobOpeningByToken(Long token) {
        return OpportunityDtoMapper.toOpportunityDto(opportunityMapper.getJobOpeningByToken(token));
    }

    @Override
    public Boolean updateApplicationReceived(OpportunityDto opportunityDto){
        Opportunity opportunity =new Opportunity();
        opportunity.setToken(opportunityDto.getToken());
        opportunity.setApplicationCount(opportunityDto.getApplicationCount());
        return opportunityMapper.updateApplicationReceivedByToken(opportunity);
    }
}