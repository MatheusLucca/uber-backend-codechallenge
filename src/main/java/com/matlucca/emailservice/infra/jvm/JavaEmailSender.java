package com.matlucca.emailservice.infra.jvm;

import com.matlucca.emailservice.adapters.EmailSenderGateway;
import com.matlucca.emailservice.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("jvm")
public class JavaEmailSender implements EmailSenderGateway {
    private JavaMailSender javaMailSender;

    @Autowired
    public JavaEmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("mathluckgo@gmail.com");
            javaMailSender.send(message);
        } catch (Exception ex){
            throw new EmailServiceException("Email sending failed", ex);
        }

    }
}
