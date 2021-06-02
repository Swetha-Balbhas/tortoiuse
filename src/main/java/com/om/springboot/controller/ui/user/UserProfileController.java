package com.om.springboot.controller.ui.user;


import com.om.springboot.controller.request.*;
import com.om.springboot.controller.request.auth.RMLoginRequest;
import com.om.springboot.dto.model.master.RoleMasterDto;
import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.dto.model.resourcemanager.ResourceManagerProfileDto;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.OtpResponse;
import com.om.springboot.dto.response.auth.JwtAuthenticationResponse;
import com.om.springboot.dto.response.resourcemanager.JobOpeningResponse;
import com.om.springboot.dto.response.resourcemanager.OpportunityList;
import com.om.springboot.dto.response.resourcemanager.OpportunityListResponse;
import com.om.springboot.model.user.DatabaseFile;
import com.om.springboot.security.JwtTokenProvider;
import com.om.springboot.service.application.AppMailerService;
import com.om.springboot.service.master.RoleMasterService;
import com.om.springboot.service.resourcemanager.OpportunityService;
import com.om.springboot.service.resourcemanager.OtpService;
import com.om.springboot.service.resourcemanager.ResourceManagerService;
import com.om.springboot.service.user.DatabaseFileService;
import com.om.springboot.util.AppConstants;
import com.om.springboot.util.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/balbhas")
public class UserProfileController {


    @Autowired
    OpportunityService opportunityService;

    @Autowired
    RoleMasterService roleMasterService;

    @Autowired
    ResourceManagerService resourceManagerService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    OtpService otpService;

    @Autowired
    DatabaseFileService fileStorageService;

    @Autowired
    AppMailerService appMailerService;

    @Autowired
    JavaMailSender javaMailSender;

    
/*
    @CrossOrigin
    @GetMapping("/postLogin/getCurrentJobOpenings")
    public ResponseEntity<?> getAllOpenedOpportunity() {
        List<OpportunityDto> getAllOpenedOpportunities = opportunityService.getAllOpenedOpportunities();
        if (null == getAllOpenedOpportunities || getAllOpenedOpportunities.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }
        OpportunityListResponse opportunityListResponse = new OpportunityListResponse(true, "");
        List<OpportunityList> opportunityLists = new ArrayList<>();
        for (OpportunityDto opportunityDto : getAllOpenedOpportunities) {
            OpportunityList opportunityList = new OpportunityList()
                    .setToken(opportunityDto.getToken())
                    .setRole(opportunityDto.getRole())
                    .setDepartment(opportunityDto.getDepartment())
                    .setLocation(opportunityDto.getLocation())
                    .setPositions(opportunityDto.getPositions())
                    .setStatus(opportunityDto.getStatus());
            opportunityLists.add(opportunityList);
        }
        opportunityListResponse.setOpportunityList(opportunityLists);
        return new ResponseEntity<>(opportunityListResponse, HttpStatus.OK);
    }
 */

    @CrossOrigin
    @GetMapping("/postLogin/getCurrentJobOpenings")
    public ResponseEntity<?> getAllOpenedOpportunity() {
        List<OpportunityDto> opportunityDtoList = opportunityService.getAllOpportunityList();
        if (null == opportunityDtoList || opportunityDtoList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E107.name()), HttpStatus.OK);
        }
        OpportunityListResponse opportunityListResponse = new OpportunityListResponse(true, "");
        List<OpportunityList> opportunityLists = new ArrayList<>();
        for (OpportunityDto opportunityDto : opportunityDtoList) {
            OpportunityList opportunityList = new OpportunityList()
                    .setToken(opportunityDto.getToken())
                    .setRole(opportunityDto.getRole())
                    .setDepartment(opportunityDto.getDepartment())
                    .setJobDescription(opportunityDto.getJobDescription())
                    .setExperience(opportunityDto.getExperience())
                    .setSkills(opportunityDto.getSkills())
                    .setLocation(opportunityDto.getLocation())
                    .setPositions(opportunityDto.getPositions())
                    .setStatus(opportunityDto.getStatus())
                    .setApplicationReceived(opportunityDto.getApplicationCount());

            SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
            Instant createdDate = opportunityDto.getCreatedAt();
            java.util.Date createDate = Date.from(createdDate);
            //  Date createDate =  Date.from(createdDate);
            String createdOn = sd.format(createDate);
            opportunityList.setCreatedOn(createdOn);
            opportunityLists.add(opportunityList);
        }
        opportunityListResponse.setOpportunityList(opportunityLists);
        return new ResponseEntity<>(opportunityListResponse, HttpStatus.OK);
    }


        @CrossOrigin
    @GetMapping("/postLogin/viewParticularJobOpenings/{token}")
    public ResponseEntity<?> viewJobOpenings(@PathVariable Long token) {
        OpportunityDto opportunityDto = opportunityService.getJobOpeningByToken(token);
        if (null != opportunityDto) {
            JobOpeningResponse jobOpeningResponse = new JobOpeningResponse(true, "");

            jobOpeningResponse.setToken(opportunityDto.getToken())
                    .setRole(opportunityDto.getRole())
                    .setDepartment(opportunityDto.getDepartment())
                    .setJobDescription(opportunityDto.getJobDescription())
                    .setExperience(opportunityDto.getExperience())
                    .setSkills(opportunityDto.getSkills())
                    .setLocation(opportunityDto.getLocation())
                    .setPositions(opportunityDto.getPositions())
                    .setStatus(opportunityDto.getStatus());

            SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
            Instant createdDate = opportunityDto.getCreatedAt();
            java.util.Date createDate = Date.from(createdDate);
            //  Date createDate =  Date.from(createdDate);
            String createdOn = sd.format(createDate);
            jobOpeningResponse.setCreatedOn(createdOn);
            return new ResponseEntity<>(jobOpeningResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E104.name()),
                    HttpStatus.OK);
        }
    }


    @CrossOrigin
    @PostMapping("/preLogin/submitJobRequest")
    public ResponseEntity<?> uploadFile(@RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("email") String email,
                                        @RequestParam("mobile") String mobile,
                                        @RequestParam(name = "experience", required = false) String experience,
                                        @RequestParam("gender") String gender,
                                        @RequestParam("location") String location,
                                        @RequestParam("token") Long token
    ) throws IOException {

        DatabaseFile fileName = fileStorageService.storeFile(file, token, firstName, lastName, email, mobile, experience, gender, location);
        System.out.println(" uploaded file size is....>" + file.getSize());
        String id = fileName.getId();
        if(null != id){
          DatabaseFile databaseFile =  fileStorageService.getFile(id);
            appMailerService.sendResumeToResourceManager(databaseFile.getData(), firstName + " " + lastName, email, mobile, experience, gender, location, token, databaseFile.getFileName());
        }
        Long count = opportunityService.getJobOpeningByToken(token).getApplicationCount();
        if (null != count) {
            OpportunityDto opportunityDto = new OpportunityDto();
            opportunityDto.setApplicationCount(count + 1L);
            opportunityDto.setToken(token);
            opportunityService.updateApplicationReceived(opportunityDto);
        } else {
            OpportunityDto opportunityDto = new OpportunityDto();
            opportunityDto.setApplicationCount(1L);
            opportunityDto.setToken(token);
            opportunityService.updateApplicationReceived(opportunityDto);
        }
        return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
    }


}



