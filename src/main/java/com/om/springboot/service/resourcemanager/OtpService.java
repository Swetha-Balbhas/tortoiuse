package com.om.springboot.service.resourcemanager;


import com.om.springboot.dto.model.resourcemanager.OtpDto;

public interface OtpService {
    public String generateSixDigitInteger();
    String insertOtp(String email);
    String updateOtp(String email);

    public Boolean existsByEmail(String email);

    public Boolean existsByEmailAndOtp(String email, String otp);

    public OtpDto getOtpByEmail(String email);

    String updateResendSixDigitOtp(String email);

    Boolean checkTimeout(String email, String otp);
}