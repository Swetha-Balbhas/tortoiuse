package com.om.springboot.service.application;

import com.om.springboot.dto.model.resourcemanager.OpportunityDto;
import com.om.springboot.service.resourcemanager.OpportunityService;
import com.om.springboot.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AppMailerService {
    private static final Logger logger = LoggerFactory.getLogger(AppMailerService.class);

    private static final String UPLOADED_FOLDER = "C:\\Swetha\\UPLOAD_FILE";

    @Autowired
    JavaMailSender javaMailService;

    @Autowired
    OpportunityService opportunityService;


    @Async
    public void sendOtpEmail(String receiver, String otp) {
        logger.debug("------------>Sending OTP email to " + receiver + " with otp as " + otp);
        MimeMessage mimeMessage = javaMailService.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(new InternetAddress(receiver));
            helper.setCc(new InternetAddress(AppConstants.EMAIL_ADMIN));
            helper.setFrom(AppConstants.EMAIL_SENDER);
            helper.setText("\n Dear ,"
                    + "\n Please use your One Time Password "
                    + otp + " to proceed further."
                    + "\n\n Regards, "
                    + "\n Balbhas Team."
            );
            helper.setSubject("Your OTP from Balbhas Team ");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            javaMailService.send(mimeMessage);
        } catch (MailException e) {
            System.err.println("---->MAIL SENDING EXCEPTION<-------------Could not send email to " + receiver + " because of " + e);
            e.printStackTrace();
        }
    }

    @Async
    public String sendResumeToResourceManager(byte[] byteArrayInputStream, String name, String email, String mobile,
                                              String experience, String gender, String location, Long token, String fileName) throws IOException {
        final InputStreamSource fileStreamSource = new ByteArrayResource(byteArrayInputStream);
        MimeMessage message = javaMailService.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            final String emailTo = AppConstants.EMAIL_RECEIVER;
            final String subject = "Balbhas Careers alerts";
            OpportunityDto opportunityDto = opportunityService.getJobOpeningByToken(token);
            if (null != opportunityDto) {
                String role = opportunityDto.getRole();
                StringBuilder content = new StringBuilder()
                        .append("\n\n Hi ").append(",")
                        .append("\n\n ").append(name).append(" has shown interest in ").append(role).append(" !")
                        .append("\n\n More details about the Candidate. ")
                        .append("\n Name ").append(" : ").append(name)
                        .append("\n Email ").append(" : ").append(email)
                        .append("\n Mobile ").append(" : ").append(mobile);
                if(null != experience){
                    content.append("\n Experience ").append(" : ").append(experience);
                }
                content.append("\n Gender ").append(" : ").append(gender)
                        .append("\n Location ").append(" : ").append(location)
                        .append("\n Note - Attached Resume")
                        .append("\n\n Regards,").append("\n Balbhas Team");
                helper.setFrom(AppConstants.EMAIL_SENDER);
                helper.setTo(emailTo);
                helper.setCc(AppConstants.EMAIL_ADMIN);
                helper.setSubject(subject);
                helper.setText(content.toString());
                helper.addAttachment(fileName, fileStreamSource, "application/pdf");
            }
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        System.out.println("resume is attached.........> ");
        javaMailService.send(message);
        return "output";
    }


    /*
    @Async
    public void sendResumeToResourceManager(MultipartFile file, String email) throws IOException{
        StringBuilder builder= new StringBuilder();
        builder.append("Dear ");
        String subject = " Sent file";
        String successMsg = "Email sent to ";
        String errorMsg = "Error in sending Email to ";
        sendMail(email, file,  subject, builder.toString(), successMsg,
                errorMsg);
    }

    @Async
    public String sendEmail(MultipartFile attachFile) {// reads form input
        final String emailTo = "swethaece2000@gmail.com";
        final String subject = "file is attached";
        final String message = " lets check file is attached";

        // for logging
        System.out.println("emailTo: " + emailTo);
        System.out.println("subject: " + subject);
        System.out.println("message: " + message);
        System.out.println("attachFile: " + attachFile.getOriginalFilename());

        javaMailService.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(
                        mimeMessage, true, "UTF-8");
                messageHelper.setTo(emailTo);
                messageHelper.setSubject(subject);
                messageHelper.setText(message);

                // determines if there is an upload file, attach it to the e-mail
                String attachName = attachFile.getOriginalFilename();
                if (!attachFile.equals("")) {

                    messageHelper.addAttachment(attachName, new InputStreamSource() {

                        @Override
                        public InputStream getInputStream() throws IOException {
                            return attachFile.getInputStream();
                        }
                    });
                }
            }
        });

        return "Result";
    }
*/
    public void sendMail(String receiver, MultipartFile file, String subject, String content, String successMsg, String
            errorMsg) throws IOException {

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        Files.write(path, bytes);

        MimeMessage mimeMessage = javaMailService.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(new InternetAddress(receiver));
            // helper.setCc(new InternetAddress(cc));
            helper.setFrom(AppConstants.EMAIL_SENDER);
            helper.setText(content);
            helper.setSubject(subject);
            // helper.addAttachment(file.getOriginalFilename(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            javaMailService.send(mimeMessage);
            System.out.println(successMsg);
        } catch (MailException e) {
            System.err.println(errorMsg + e);
        }
    }


}
