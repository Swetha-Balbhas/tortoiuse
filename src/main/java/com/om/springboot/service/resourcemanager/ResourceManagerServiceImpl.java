package com.om.springboot.service.resourcemanager;


import com.om.springboot.dto.mapper.resourcemanager.ResourceManagerDtoMapper;
import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.dto.model.resourcemanager.ResourceManagerProfileDto;
import com.om.springboot.mappers.resourcemanager.OpportunityMapper;
import com.om.springboot.mappers.resourcemanager.ResourceManagerProfileMapper;
import com.om.springboot.model.resourcemanager.Opportunity;
import com.om.springboot.model.resourcemanager.ResourceManagerProfile;
import com.om.springboot.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;

@Component
public class ResourceManagerServiceImpl implements ResourceManagerService{


    @Autowired
    @Qualifier("resourceManagerProfileMapper")
    private ResourceManagerProfileMapper resourceManagerProfileMapper;


   @Override
    public Boolean existsByEmailAndRoleId(String email, Long roleId){
       return resourceManagerProfileMapper.findByEmailAndRoleId(email, roleId);
   }

   @Override
    public ResourceManagerProfileDto getProfileByEmail(String email){
       return ResourceManagerDtoMapper.toResourceManagerDto(resourceManagerProfileMapper.getProfileByEmail(email));
   }

    @Override
    public ResourceManagerProfileDto getProfileById(Long id){
        return ResourceManagerDtoMapper.toResourceManagerDto(resourceManagerProfileMapper.getProfileById(id));
    }

    @Override
    public Boolean existsByEmail(String email){
       return resourceManagerProfileMapper.findByEmail(email);
    }

    @Override
    public Boolean resetPassword(ResourceManagerProfileDto resourceManagerProfileDto){
        ResourceManagerProfile resourceManagerProfile = new ResourceManagerProfile();
        resourceManagerProfile.setEmail(resourceManagerProfileDto.getEmail());
        resourceManagerProfile.setPassword(resourceManagerProfileDto.getPassword());
        resourceManagerProfile.setRoleId(resourceManagerProfileDto.getRoleId());
     Boolean isUpdated = resourceManagerProfileMapper.updatePasswordByEmail(resourceManagerProfile);
     if(null != isUpdated && isUpdated){
         return Boolean.TRUE;
     }else{
         return Boolean.FALSE;
     }
    }


}
