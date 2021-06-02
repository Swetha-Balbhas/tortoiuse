package com.om.springboot.service.resourcemanager;

import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.dto.model.resourcemanager.ResourceManagerProfileDto;

public interface ResourceManagerService {
    Boolean existsByEmailAndRoleId(String email, Long roleId);
    public ResourceManagerProfileDto getProfileByEmail(String email);
    public ResourceManagerProfileDto getProfileById(Long id);
    Boolean existsByEmail(String email);
    public Boolean resetPassword(ResourceManagerProfileDto resourceManagerProfileDto);
}
