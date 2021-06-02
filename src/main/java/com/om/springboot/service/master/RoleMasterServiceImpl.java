package com.om.springboot.service.master;

import com.om.springboot.dto.mapper.master.RoleMasterDtoMapper;
import com.om.springboot.dto.model.master.RoleMasterDto;
import com.om.springboot.mappers.master.RoleMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class RoleMasterServiceImpl implements RoleMasterService {
    @Autowired
    @Qualifier("roleMasterMapper")
    RoleMasterMapper roleMasterMapper;

    @Override
    public Boolean existsByRoleId(Long roleId) {
        Boolean isExist = roleMasterMapper.findByRoleId(roleId);
        System.out.println("............roleId is exists..................." + isExist);
        if (isExist != null && isExist) {
            return isExist;
        } else {
            return false;
        }
    }


    @Override
    public RoleMasterDto getRoleId(Long roleId) {
        Boolean isExists = roleMasterMapper.findByRoleId(roleId);
        if (null != isExists && isExists ) {

            return RoleMasterDtoMapper
                    .toRoleMasterDto(roleMasterMapper.getRoleByRoleId(roleId));

        }

        return null;
    }


    @Override
    public RoleMasterDto getRole(String role) {
        Boolean isExists = roleMasterMapper.findByRole(role);
        if (null != isExists && isExists ) {

            return RoleMasterDtoMapper
                    .toRoleMasterDto(roleMasterMapper.getRole(role));

        }

        return null;
    }
}