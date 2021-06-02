package com.om.springboot.dto.mapper.resourcemanager;

import com.om.springboot.dto.model.resourcemanager.OtpDto;
import com.om.springboot.model.resourcemanager.Otp;

public class OtpDtoMapper {

    public static OtpDto toOtpDto(Otp otp) {
        return new OtpDto()
                .setId(otp.getId())
                .setEmail(otp.getEmail())
                .setOtp(otp.getOtp())
                .setCreatedAt(otp.getCreatedAt())
                .setResentAt(otp.getResentAt())
                .setResentCount(otp.getResentCount());


    }
}

