package com.om.springboot.security;

import com.om.springboot.exception.ResourceNotFoundException;
import com.om.springboot.mappers.master.RoleMasterMapper;
import com.om.springboot.mappers.resourcemanager.ResourceManagerProfileMapper;
import com.om.springboot.mappers.user.UserMapper;
import com.om.springboot.model.resourcemanager.ResourceManagerProfile;
import com.om.springboot.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //@Autowired
   // private UserMapper userMapper;

    @Autowired
    ResourceManagerProfileMapper resourceManagerProfileMapper;

    @Autowired
    RoleMasterMapper roleMasterMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        ResourceManagerProfile user = resourceManagerProfileMapper.getProfileByEmail(usernameOrEmail);
        if(user ==null){
            throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
        }
        return UserPrincipal.create(getBalbhasUserFromUser(user));
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        ResourceManagerProfile user = resourceManagerProfileMapper.getProfileById(id);
        if(user ==null){
            throw new ResourceNotFoundException("User", "id", id);
        }

        return UserPrincipal.create(getBalbhasUserFromUser(user));
    }

    private BalbhasUser getBalbhasUserFromUser(ResourceManagerProfile resourceManagerProfile) {
        BalbhasUser user = new BalbhasUser();
        user.setId(resourceManagerProfile.getId());
     //  user.setName(resourceManagerProfile.getName());
      //  user.setMobile(employeeProfile.getMobile());
        user.setEmail(resourceManagerProfile.getEmail());
        user.setRole(roleMasterMapper.getRoleByRoleId(resourceManagerProfile.getRoleId()).getRole());
        user.setPassword(resourceManagerProfile.getPassword());
        return user;

    }
}
