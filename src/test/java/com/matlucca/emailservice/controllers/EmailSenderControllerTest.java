package com.matlucca.emailservice.controllers;

import com.matlucca.emailservice.application.EmailSenderService;
import com.matlucca.emailservice.core.EmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

class EmailSenderControllerTest {

    public static final String MAIL = "mat.lualves@gmail.com";
    public static final String TESTE = "Teste";
    private EmailRequest emailRequest = new EmailRequest(MAIL, TESTE, TESTE);
    @InjectMocks
    private EmailSenderController emailSenderController;

    @Mock
    private EmailSenderService emailSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSendEmailThenEmailSentSucessfully() {
        doNothing().when(emailSenderService).sendEmail(anyString(), anyString(), anyString());

        ResponseEntity<String> response = emailSenderController.sendEmail(emailRequest);
        Mockito.verify(emailSenderService, Mockito.times(1))
                .sendEmail(anyString(), anyString(), anyString());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("email sent sucessfully", response.getBody());

    }
}