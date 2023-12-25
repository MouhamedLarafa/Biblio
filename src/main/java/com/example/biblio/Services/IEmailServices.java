package com.example.biblio.Services;

import com.example.biblio.Configurations.Mail;

import javax.mail.MessagingException;

public interface IEmailServices {
    public void sendEmail(Mail mail, String templateName) throws MessagingException, jakarta.mail.MessagingException;

}
