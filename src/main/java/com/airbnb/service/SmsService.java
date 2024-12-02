//package com.airbnb.service;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//
//@Service
//public class SmsService {
//    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
//
//    @Value("${twilio.phone.number}")
//    private String twilioPhoneNumber;
//
//    public void sendSms(String toPhoneNumber, String messageContent) {
//        try {
//            logger.info("Starting to Send SMS : " + new DateTime());
//            Message message = Message.creator(
//                            new PhoneNumber(toPhoneNumber), // To number
//                            new PhoneNumber(twilioPhoneNumber), // From Twilio number
//                            messageContent) // Message body
//                    .create();
//            logger.info("Sent SMS" + new  DateTime());
//            System.out.println("Sent message SID: " + message.getSid());
//        }catch (Exception e)
//        {
//            logger.error(e.getMessage());
//        }
//    }
//}