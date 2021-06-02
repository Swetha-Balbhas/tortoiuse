package com.om.springboot.mappers.resourcemanager;


import com.om.springboot.model.resourcemanager.Otp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OtpMapper {

    Boolean insertOtp(Otp otp);


    Otp getOtpByMobile(String mobile);

    Otp getOtpByEmail(String email);


    Boolean findByEmail(String email);


    Boolean updateOtp(Otp otp);


    Boolean findByUserId(Long userId);

    Boolean findByEmailAndOtpTimeout(String email, String otp);

    Boolean findByEmailAndOtp(String email, String otp);

}
