package com.om.springboot.mappers.master;

import com.om.springboot.model.master.StatusMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatusMasterMapper {
 StatusMaster getByStatus(String status);
 Boolean findByStatus(String status);
 Boolean findByStatusId(Long statusId);
StatusMaster getStatusByStatusId(Long statusId);
}
