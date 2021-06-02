package com.om.springboot.service.resourcemanager;


import com.om.springboot.dto.mapper.resourcemanager.OtpDtoMapper;
import com.om.springboot.dto.model.resourcemanager.OtpDto;
import com.om.springboot.mappers.resourcemanager.OtpMapper;
import com.om.springboot.model.otpIntegration.TimeBasedOneTimePasswordGenerator;
import com.om.springboot.model.resourcemanager.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Component
public class OtpServiceImpl implements OtpService {

    @Autowired
    @Qualifier("otpMapper")
    private OtpMapper otpMapper;



    @Override
    public Boolean existsByEmail(String email) {
        return otpMapper.findByEmail(email);
    }

    @Override
    public Boolean existsByEmailAndOtp(String email, String otp) {
        return otpMapper.findByEmailAndOtp(email, otp);
    }


    @Override
    public Boolean checkTimeout(String email, String otp) {
        return otpMapper.findByEmailAndOtpTimeout(email, otp);
    }


    @Override
    public OtpDto getOtpByEmail(String email) {
        return OtpDtoMapper
                .toOtpDto(otpMapper.getOtpByEmail(email));
    }

    @Override
    public String updateOtp(String email) {
        Otp model = otpMapper.getOtpByEmail(email);
        if (null == model) return null;
        Otp otp = new Otp();
        otp.setId(model.getId());
        otp.setEmail(email);
        otp.setOtp(this.generateSixDigitInteger());
        otp.setCreatedAt(Instant.now());
        otp.setResentAt(model.getResentAt());
        otp.setResentCount(0);

        Boolean isOtpRegistered = otpMapper.updateOtp(otp);
        if (null != isOtpRegistered && isOtpRegistered) {
            System.out.println(".......otp is updated to email....>" +isOtpRegistered);
          //  sendSMS(guestLoginDto.getMobile(), otp.getOtp());
            //sendOtpEmail(guestLoginDto.getEmail(), otp.getOtp());
            //appMailerService.sendOtpEmail(guestLoginDto.getMobile(), otp.getOtp());
            return (otp.getOtp());
        } else {
            return null;
        }
    }

    @Override
    public String updateResendSixDigitOtp(String email) {
        Otp model = otpMapper.getOtpByEmail(email);
        if (null == model) return null;
        Otp otp = new Otp();
        // Long resentCount = 0L;
        Integer resentCount = model.getResentCount();
        System.out.println("............resent count of otp" + resentCount);
        if (2 == resentCount) {
            return null;
        }
        otp.setId(model.getId());
        otp.setEmail(email);
        otp.setOtp(this.generateSixDigitInteger());
        otp.setCreatedAt(Instant.now());
        otp.setResentAt(Instant.now());
        otp.setResentCount(resentCount + 1);


        Boolean isOtpRegistered = otpMapper.updateOtp(otp);
        System.out.println(".........> resend Otp" + isOtpRegistered);
        if (null != isOtpRegistered && isOtpRegistered) {
            return (otp.getOtp());
        } else {
            return null;
        }
    }




    @Override
    public String insertOtp(String email) {
        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setOtp(generateSixDigitInteger());
        otp.setCreatedAt(Instant.now());
        otp.setResentCount(0);

        System.out.println("------> Now the mapper is called to insert the otp..........");
        Boolean isOtpRegistered = otpMapper.insertOtp(otp);
        if (null != isOtpRegistered && isOtpRegistered) {
            return (otp.getOtp());
        } else {
            return null;
        }
    }




    @Override
    public String generateSixDigitInteger() {
        try {
            final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
            final Key key;
            {
                final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
                // SHA-1 and SHA-256 prefer 64-byte (512-bit) keys; SHA512 prefers 128-byte (1024-bit) keys
                keyGenerator.init(512);
                key = keyGenerator.generateKey();
            }

            final Instant now = Instant.now();
            final Instant later = now.plus(totp.getTimeStep());
            System.out.format("Current password: %06d\n", totp.generateOneTimePassword(key, now));
            System.out.format("Future password:  %06d\n", totp.generateOneTimePassword(key, later));

            return String.format("%06d", totp.generateOneTimePassword(key, now));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("...Invalid Algorithm..");
        } catch (InvalidKeyException e) {
            System.err.println("...Invalid KeyException..");
        }
        return "Out of try - catch Block";
    }


}