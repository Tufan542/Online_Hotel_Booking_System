//package com.airbnb.service;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendEmailWithAttachment(String to, String subject, String text, File pathToAttachment) throws MessagingException, MessagingException {
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//
//        // Use MimeMessageHelper to handle the attachment
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(text);
//
//        // Attach the file to the email
//        FileSystemResource file = new FileSystemResource(pathToAttachment);
//        helper.addAttachment(file.getFilename(), file);
//
//        // Send the email
//        javaMailSender.send(message);
//    }
//}
