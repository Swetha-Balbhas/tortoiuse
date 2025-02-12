package com.om.springboot.controller.ui.repository;

import com.om.springboot.model.user.DatabaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {

}
