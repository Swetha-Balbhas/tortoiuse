package com.om.springboot.service.user;

import com.om.springboot.controller.ui.repository.DatabaseFileRepository;
import com.om.springboot.exception.FileStorageException;
import com.om.springboot.model.user.DatabaseFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class DatabaseFileService {

    @Autowired
     DatabaseFileRepository dbFileRepository;

    public DatabaseFile storeFile(MultipartFile file, Long token, String firstName, String lastName, String email, String mobile,
                                  String experience, String gender, String location) {

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(token,firstName,lastName,email,mobile,experience,gender,location,fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DatabaseFile getFile(String fileId) throws FileNotFoundException {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

   }

