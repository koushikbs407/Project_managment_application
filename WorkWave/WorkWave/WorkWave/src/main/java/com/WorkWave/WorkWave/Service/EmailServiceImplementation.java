package com.WorkWave.WorkWave.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmailwithToken(String userEmail, String Link) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,"utf-8");

        String subject = "join project Team";
        String text = "click the link to join the meeting" + Link;

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text,true);
        mimeMessageHelper.setTo(userEmail);

        try {
            javaMailSender.send(message);
        }catch (MailSendException e){
            throw  new MailSendException("failed to send the mail");
        }

    }
}
