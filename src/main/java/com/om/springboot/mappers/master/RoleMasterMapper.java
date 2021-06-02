package com.om.springboot.mappers.master;

import com.om.springboot.model.master.RoleMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMasterMapper {

    RoleMaster getRole(String role);

    Boolean findByRole(String role);

    RoleMaster getRoleByRoleId(Long roleId);

    Boolean findByRoleId(Long roleId);
}
