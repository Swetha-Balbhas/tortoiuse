package com.om.springboot.dto.mapper.resourcemanager;

import com.om.springboot.dto.model.master.StatusMasterDto;
import com.om.springboot.dto.model.resourcemanager.ResourceManagerProfileDto;
import com.om.springboot.model.master.StatusMaster;
import com.om.springboot.model.resourcemanager.ResourceManagerProfile;

import java.util.ArrayList;
import java.util.List;

public class ResourceManagerDtoMapper {
    public static ResourceManagerProfileDto toResourceManagerDto(ResourceManagerProfile resourceManagerProfile) {
        return new ResourceManagerProfileDto()
                .setId(resourceManagerProfile.getId())
                .setEmail(resourceManagerProfile.getEmail())
                .setPassword(resourceManagerProfile.getPassword())
                .setRoleId(resourceManagerProfile.getRoleId())
                ;
    }

}