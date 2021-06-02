package com.om.springboot.service.master;

import com.om.springboot.dto.mapper.master.StatusMasterDtoMapper;
import com.om.springboot.dto.model.master.StatusMasterDto;
import com.om.springboot.mappers.master.StatusMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class StatusMasterServiceImpl implements StatusMasterService {
    @Autowired
    @Qualifier("statusMasterMapper")
    StatusMasterMapper statusMasterMapper;

    @Override
    public StatusMasterDto getByStatus(String status) {
        Boolean isExists = statusMasterMapper.findByStatus(status);
        if (null != isExists && isExists ) {
            return StatusMasterDtoMapper
                    .toStatusMasterDto(statusMasterMapper.getByStatus(status));
        }
        return null;
    }

    @Override
    public StatusMasterDto getByStatusId(Long statusId) {
        Boolean isExists = statusMasterMapper.findByStatusId(statusId);
        if (null != isExists && isExists ) {
            return StatusMasterDtoMapper
                    .toStatusMasterDto(statusMasterMapper.getStatusByStatusId(statusId));
        }
        return null;
    }

}