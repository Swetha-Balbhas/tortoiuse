package com.om.springboot.dto.mapper.master;

import com.om.springboot.dto.model.master.StatusMasterDto;
import com.om.springboot.model.master.StatusMaster;

public class StatusMasterDtoMapper {
    public static StatusMasterDto toStatusMasterDto(StatusMaster statusMaster) {
        return new StatusMasterDto()
                .setStatusId(statusMaster.getStatusId())
                .setStatus(statusMaster.getStatus());
    }
}