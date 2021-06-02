package com.om.springboot.dto.mapper.resourcemanager;

import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.dto.model.resourcemanager.ResourceManagerProfileDto;
import com.om.springboot.model.resourcemanager.Opportunity;
import com.om.springboot.model.resourcemanager.ResourceManagerProfile;

import java.util.ArrayList;
import java.util.List;

public class OpportunityDtoMapper {
    public static OpportunityDto toOpportunityDto(Opportunity opportunity) {
        return new OpportunityDto()
              //  .setId(opportunity.getId())
                .setToken(opportunity.getToken())
                .setRole(opportunity.getRole())
                .setPositions(opportunity.getPositions())
                .setDepartment(opportunity.getDepartment())
                .setSkills(opportunity.getSkills())
                .setLocation(opportunity.getLocation())
                .setExperience(opportunity.getExperience())
                .setJobDescription(opportunity.getJobDescription())
                .setStatus(opportunity.getStatus())
                .setApplicationCount(opportunity.getApplicationCount())
                .setCreatedAt(opportunity.getCreatedAt())
                ;
    }


    public static List<OpportunityDto> toOpportunityDtoListy(List<Opportunity> list) {
        if (null == list || list.isEmpty()) return null;
        List<OpportunityDto> dtoList = new ArrayList<OpportunityDto>();
        for (Opportunity user : list) {
            dtoList.add(toOpportunityDto(user));
        }
        return dtoList;
    }
}