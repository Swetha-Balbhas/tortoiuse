package com.om.springboot.dto.mapper.master;

import com.om.springboot.dto.model.master.RoleMasterDto;
import com.om.springboot.model.master.RoleMaster;

public class RoleMasterDtoMapper {
    public static RoleMasterDto toRoleMasterDto(RoleMaster roleMaster) {
        return new RoleMasterDto()
                .setRoleId(roleMaster.getRoleId())
                .setRole(roleMaster.getRole());
    }
}
