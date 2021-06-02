package com.om.springboot.controller.ui.resourcemanager;


import com.om.springboot.controller.request.*;
import com.om.springboot.controller.request.auth.LogoutRequest;
import com.om.springboot.controller.request.auth.RMLoginRequest;
import com.om.springboot.dto.model.master.RoleMasterDto;
import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.dto.model.resourcemanager.ResourceManagerProfileDto;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.OtpResponse;
import com.om.springboot.dto.response.auth.JwtAuthenticationResponse;
import com.om.springboot.dto.response.resourcemanager.OpportunityList;
import com.om.springboot.dto.response.resourcemanager.OpportunityListResponse;
import com.om.springboot.security.JwtTokenProvider;
import com.om.springboot.service.application.AppMailerService;
import com.om.springboot.service.master.RoleMasterService;
import com.om.springboot.service.resourcemanager.OpportunityService;
import com.om.springboot.service.resourcemanager.OtpService;
import com.om.springboot.service.resourcemanager.ResourceManagerService;
import com.om.springboot.util.AppConstants;
import com.om.springboot.util.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/balbhas")
public class ResourceManagerController {


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
    AppMailerService appMailerService;

    @CrossOrigin
    @PostMapping("/postLogin/insertOpportunity")
    public ResponseEntity<?> insertJobOpportunities(@Valid @RequestBody OpportunityRequest opportunityRequest) {

        OpportunityDto opportunityDto = new OpportunityDto()
                .setRole(opportunityRequest.getRole())
                .setDepartment(opportunityRequest.getDepartment())
                .setJobDescription(opportunityRequest.getJobDescription())
                .setLocation(opportunityRequest.getLocation())
                .setExperience(opportunityRequest.getExperience())
                .setSkills(opportunityRequest.getSkills())
                .setPositions(opportunityRequest.getPositions())
                .setStatus(AppConstants.OPEN_STATUS);
        Boolean isInserted = opportunityService.insertJobOpportunity(opportunityDto);
        if (null != isInserted && isInserted) {
            System.out.println(" checks job opportunity details are inserted.......> " + isInserted);
            return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E101.name()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @PostMapping("preLogin/SignIn")
    public ResponseEntity<?> loginAsResourceManager(@Valid @RequestBody RMLoginRequest rmLoginRequest) {
        String email = rmLoginRequest.getEmail();
        String password = rmLoginRequest.getPassword();
        RoleMasterDto roleMasterDto = roleMasterService.getRole(AppConstants.RM_ROLE);
        Boolean doesAdminExist = resourceManagerService.existsByEmailAndRoleId(email, roleMasterDto.getRoleId());
        if (null == doesAdminExist || !doesAdminExist) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()),
                    HttpStatus.OK);
        }
        ResourceManagerProfileDto resourceManagerProfileDto = resourceManagerService.getProfileByEmail(email);
        if (null != resourceManagerProfileDto) {
            String encryptedPassword = resourceManagerProfileDto.getPassword();
            if (new BCryptPasswordEncoder().matches(password, encryptedPassword)) {
                System.out.println("Admin Password Matches ....................");
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                rmLoginRequest.getEmail(),
                                rmLoginRequest.getPassword()
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenProvider.generateToken(authentication);
                return new ResponseEntity(new JwtAuthenticationResponse(jwt, true, null),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E103.name()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()),
                    HttpStatus.OK);
        }
    }


    @CrossOrigin
    @PostMapping("/postLogin/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest tokenRequest) {
        tokenProvider.expireToken(tokenRequest.getAccessToken());
        return new ResponseEntity(new ApiResponse(true, ""), HttpStatus.OK);
    }


    @CrossOrigin
    @PostMapping("/postLogin/verifyEmail")
    public ResponseEntity<?> verifyEmailOfEmployee(@Valid @RequestBody EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        Boolean isEmailPresents = resourceManagerService.existsByEmail(email);
        System.out.println(".....email is checks for send otp in controller........>" + isEmailPresents);
        if (null == isEmailPresents || !isEmailPresents) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
        } else {
            ResourceManagerProfileDto resourceManagerProfileDto = resourceManagerService.getProfileByEmail(email);
            Boolean isRoleExists = resourceManagerService.existsByEmailAndRoleId(email, resourceManagerProfileDto.getRoleId());
            if (null == isRoleExists || !isRoleExists) {
                return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
            }

            String otpNumber = null;
            Boolean emailSuccess = otpService.existsByEmail(email);
            //System.out.println("............emial is exists checks in controller......" + emailSuccess);
            if (null != emailSuccess && emailSuccess) {
                otpNumber = otpService.updateOtp(email);
            } else {
                otpNumber = otpService.insertOtp(email);
            }
            if (null != otpNumber) {
                appMailerService.sendOtpEmail(email,otpNumber);
                OtpResponse otpResponse = new OtpResponse(Boolean.TRUE, "")
                        .setOtp(otpNumber);
                //  appMailerService.sendOtpEmail(email, otpNumber);
                return new ResponseEntity(otpResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()),
                        HttpStatus.OK);
            }
        }
    }

    @CrossOrigin
    @PostMapping("/postLogin/validateOtp")
    public ResponseEntity<?> validateOtpForEmployee(@Valid @RequestBody ValidateOtpRequest validateOtpRequest) {
        String email = validateOtpRequest.getEmail();
        Boolean isEmailPresents = resourceManagerService.existsByEmail(email);
        System.out.println(".....email is checks for send otp in controller........>" + isEmailPresents);
        if (null == isEmailPresents || !isEmailPresents) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
        } else {
            Boolean isOtpValidated = otpService.existsByEmailAndOtp(email, validateOtpRequest.getOtp());
            if (null == isOtpValidated || !isOtpValidated) {
                return new ResponseEntity(new ApiResponse(false, ErrorConstants.E106.name()),
                        HttpStatus.OK);
            }

            Boolean isTimeout = otpService.checkTimeout(email, validateOtpRequest.getOtp());
            if (null != isTimeout && isTimeout) {
                return new ResponseEntity(new ApiResponse(true, ""),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(new ApiResponse(false, ErrorConstants.E104.name()),
                        HttpStatus.OK);
            }
        }
    }

    @CrossOrigin
    @PostMapping("/postLogin/resendOtp")
    public ResponseEntity<?> resendOtpToEmployee(@Valid @RequestBody EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        Boolean isEmailPresents = resourceManagerService.existsByEmail(email);
        System.out.println(".....email exists in userTable........>" + isEmailPresents);
        if (null == isEmailPresents || !isEmailPresents) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
        } else {
            String otpNumber = null;
            if (null != email) {
                Boolean isEmailExistsInOTPTable = otpService.existsByEmail(email);
                if (null != isEmailExistsInOTPTable && isEmailExistsInOTPTable) {

                    otpNumber = otpService.updateResendSixDigitOtp(email);

                    if (null != otpNumber) {
                        appMailerService.sendOtpEmail(email,otpNumber);
                        OtpResponse otpResponse = new OtpResponse(Boolean.TRUE, "")
                                .setOtp(otpNumber);
                        //   appMailerService.sendOtpEmail(email, otpNumber);
                        return new ResponseEntity(otpResponse, HttpStatus.OK);
                    } else {
                        return new ResponseEntity(new ApiResponse(false, ErrorConstants.E105.name()),
                                HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity(new ApiResponse(false, ErrorConstants.E104.name()), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/postLogin/resetPassword")
    public ResponseEntity<?> forgetPasswordForEmployee(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        String email = passwordResetRequest.getEmail();
        String password = passwordResetRequest.getNewPassword();
        Boolean isEmailExists = resourceManagerService.existsByEmail(email);
        if (null != isEmailExists && isEmailExists) {
            System.out.println("........email is present in ......" + email);
            ResourceManagerProfileDto employeeProfileDto = resourceManagerService.getProfileByEmail(email);

            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            // Boolean isReset = userAuthenticationService.resetPassword(email, encodedPassword);
            ResourceManagerProfileDto resourceManagerProfileDto = new ResourceManagerProfileDto()
                    .setEmail(email)
                    .setPassword(encodedPassword);
            Boolean isReset = resourceManagerService.resetPassword(resourceManagerProfileDto);
            if (null != isReset && isReset) {
                System.out.println(".........> reset password is ......>" + password);
                System.out.println("........> encoded reset password..........>" + encodedPassword);
                return new ResponseEntity(new ApiResponse(true, ""),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(new ApiResponse(false, ErrorConstants.E104.name()),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity(new ApiResponse(false, ErrorConstants.E104.name()),
                HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/postLogin/getPostedOpportunities")
    public ResponseEntity<?> getAllPostedOpportunitiesDetails() {
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
    @PostMapping("/postLogin/editJobOpportunities")
    public ResponseEntity<?> updateJob(@Valid @RequestBody OpportunityRequest opportunityRequest) {
        Long token = opportunityRequest.getToken();
        Boolean isExists = opportunityService.existsByToken(token);
        if (null == isExists || !isExists) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }
        OpportunityDto opportunityDto = new OpportunityDto()
                .setToken(token)
                .setRole(opportunityRequest.getRole())
                .setDepartment(opportunityRequest.getDepartment())
                .setJobDescription(opportunityRequest.getJobDescription())
                .setPositions(opportunityRequest.getPositions())
                .setSkills(opportunityRequest.getSkills())
                .setLocation(opportunityRequest.getLocation())
                .setExperience(opportunityRequest.getExperience())
                .setStatus(opportunityRequest.getStatus());
        Boolean isUpdated = opportunityService.updateJobOpportunityByToken(opportunityDto);
        System.out.println("....> checks job opportunity is updated ........>" + isUpdated);
        if (null != isUpdated && isUpdated) {
            return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E109.name()), HttpStatus.OK);
        }
    }


    @CrossOrigin
    @PostMapping("/postLogin/updateCloseStatus")
    public ResponseEntity<?> closeOpenDetails(@Valid @RequestBody TokenRequest tokenRequest) {
        Long token = tokenRequest.getToken();
        Boolean isClosed = opportunityService.closeJobOpportunity(token);
        if (null != isClosed && isClosed) {
            return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E109.name()), HttpStatus.OK);
        }
    }
}
