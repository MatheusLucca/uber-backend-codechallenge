package com.matlucca.emailservice.application;

import com.matlucca.emailservice.adapters.EmailSenderGateway;
import com.matlucca.emailservice.core.EmailSenderUseCase;
import com.matlucca.emailservice.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService implements EmailSenderUseCase {

    private final EmailSenderGateway mainEmailSenderGateway;

    private final EmailSenderGateway optEmailSenderGateway;

    @Autowired
    public EmailSenderService(@Qualifier("ses") EmailSenderGateway mainEmailSenderGateway, @Qualifier("jvm") EmailSenderGateway optEmailSenderGateway) {
        this.mainEmailSenderGateway = mainEmailSenderGateway;
        this.optEmailSenderGateway = optEmailSenderGateway;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try{
            mainEmailSenderGateway.sendEmail(toEmail, subject, body);
        } catch (EmailServiceException ex){
            optEmailSenderGateway.sendEmail(toEmail, subject, body);
        }
    }
}