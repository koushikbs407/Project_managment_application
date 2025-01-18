package com.WorkWave.WorkWave.Service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailwithToken(String userEmail,String Link) throws MessagingException;
}
