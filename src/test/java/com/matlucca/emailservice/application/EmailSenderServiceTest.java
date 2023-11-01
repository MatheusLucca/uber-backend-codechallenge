package com.matlucca.emailservice.application;

import com.matlucca.emailservice.adapters.EmailSenderGateway;
import com.matlucca.emailservice.core.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmailSenderServiceTest {

    public static final String MAIL = "mat.lualves@gmail.com";
    public static final String TESTE = "Teste";
    @InjectMocks
    private EmailSenderService emailSenderService;

    @Mock
    private EmailSenderGateway mainEmailSenderGateway;

    @Mock
    private EmailSenderGateway optEmailSenderGateway;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSendEmailThenEmailSentSucessfully() {
        doNothing().when(mainEmailSenderGateway).sendEmail(anyString(), anyString(), anyString());


        emailSenderService.sendEmail(MAIL, TESTE, TESTE);
        Mockito.verify(mainEmailSenderGateway, times(1))
                .sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void whenSendEmailThenMainFailAndOptIsUsed(){
        doThrow(new EmailServiceException("ERRO")).when(mainEmailSenderGateway).sendEmail(anyString(), anyString(), anyString());
        doNothing().when(optEmailSenderGateway).sendEmail(anyString(), anyString(), anyString());
        emailSenderService.sendEmail(MAIL, TESTE, TESTE);
        Mockito.verify(mainEmailSenderGateway, Mockito.times(1))
                .sendEmail(anyString(), anyString(), anyString());
        Mockito.verify(optEmailSenderGateway, Mockito.times(1))
                .sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void whenSendEmailThenReturnEmailServiceException(){
        doThrow(new EmailServiceException("ERRO")).when(mainEmailSenderGateway).sendEmail(anyString(), anyString(), anyString());
        doThrow(new EmailServiceException("ERRO")).when(optEmailSenderGateway).sendEmail(anyString(), anyString(), anyString());

        assertThrows(EmailServiceException.class, () -> {
            emailSenderService.sendEmail(MAIL, TESTE, TESTE);
        });
    }



}