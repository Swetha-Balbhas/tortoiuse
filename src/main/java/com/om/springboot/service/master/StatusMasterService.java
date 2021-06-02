package com.om.springboot.service.master;

import com.om.springboot.dto.model.master.StatusMasterDto;

public interface StatusMasterService {
    public StatusMasterDto getByStatus(String status);
    public StatusMasterDto getByStatusId(Long statusId);
    }
