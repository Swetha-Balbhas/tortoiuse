package com.om.springboot.service.master;

import com.om.springboot.dto.model.master.RoleMasterDto;

public interface RoleMasterService {
    public RoleMasterDto getRole(String role);
    RoleMasterDto getRoleId(Long roleId);
    public Boolean existsByRoleId(Long roleId);
}
