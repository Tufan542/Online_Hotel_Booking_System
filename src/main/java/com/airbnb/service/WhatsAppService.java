//package com.airbnb.service;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class WhatsAppService {
//
//    @Value("${twilio.account.sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth.token}")
//    private String authToken;
//
//    @Value("${twilio.whatsapp.from}")
//    private String fromWhatsAppNumber;
//
//    // Initialize Twilio only when sending a message
//    public String sendWhatsAppMessage(String toWhatsAppNumber, String body) {
//        // Initialize Twilio with account credentials
//        Twilio.init(accountSid, authToken);
//
//        // Send WhatsApp message
//        Message message = Message.creator(
//                new com.twilio.type.PhoneNumber("whatsapp:" + toWhatsAppNumber), // recipient WhatsApp number
//                new com.twilio.type.PhoneNumber(fromWhatsAppNumber),             // sender WhatsApp number
//                body                                                                // message body
//        ).create();
//
//        return message.getSid(); // returns message SID if successful
//    }
//}
