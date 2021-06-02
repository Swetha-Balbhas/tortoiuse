package com.om.springboot.mappers.resourcemanager;

import com.om.springboot.model.resourcemanager.Opportunity;
import com.om.springboot.model.resourcemanager.ResourceManagerProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceManagerProfileMapper {
    Boolean findByEmailAndRoleId(String email, Long roleId);
    ResourceManagerProfile getProfileByEmail(String email);
    ResourceManagerProfile getProfileById(Long id);
    Boolean findByEmail(String email);
    Boolean updatePasswordByEmail(ResourceManagerProfile resourceManagerProfile);
}
