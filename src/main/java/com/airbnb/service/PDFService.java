//package com.airbnb.service;
//
//import com.airbnb.entity.Booking;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BarcodeQRCode;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.stream.Stream;
//
//@Service
//@AllArgsConstructor
//public class PDFService {
//    private EmailService emailService;
//    public void generatePDF(Booking booking)
//    {
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("C://booking_pdf//" + booking.getId() + "_booking_confirmation.pdf"));
//
//            document.open();
//
//            // Add booking confirmation header
//            document.add(new Paragraph("Booking Confirmation"));
//
//            // Add guest information
//            PdfPTable guestTable = new PdfPTable(2);
//            guestTable.addCell("Name:");
//            guestTable.addCell(booking.getGuestName());
//            guestTable.addCell("Email:");
//            guestTable.addCell(booking.getEmail());
//            guestTable.addCell("Mobile:");
//            guestTable.addCell(booking.getMobile());
//            document.add(guestTable);
//
//            // Add reservation details
//            PdfPTable reservationTable = new PdfPTable(2);
//            reservationTable.addCell("Listing:");
//            reservationTable.addCell(booking.getProperty().getName());
//            reservationTable.addCell("Location:");
//            reservationTable.addCell(booking.getProperty().getCity().getName() + ", " + booking.getProperty().getCountry().getName());
//            reservationTable.addCell("Check-in:");
//            reservationTable.addCell(booking.getCheckInDate().toString());
//            reservationTable.addCell("Check-out:");
//            reservationTable.addCell(booking.getCheckOutDate().toString());
//            reservationTable.addCell("Number of Guests:");
//            reservationTable.addCell(String.valueOf(booking.getNoOfGuests()));
//            reservationTable.addCell("Total Price:");
//            reservationTable.addCell(String.valueOf(booking.getTotal_price()));
//            document.add(reservationTable);
//
//            // Generate and add QR Code
//            String qrCodeText = "Booking ID: " + booking.getId() + "\nGuest: " + booking.getGuestName();
//            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(qrCodeText, 1000, 1000, null);
//            Image qrCodeImage = barcodeQRCode.getImage();
//            qrCodeImage.scaleAbsolute(100, 100);  // Adjust the size as needed
//            document.add(qrCodeImage);
//            // Add payment information
//            PdfPTable paymentTable = new PdfPTable(2);
//            paymentTable.addCell("Payment Method:");
//            paymentTable.addCell("Credit Card"); // Hardcoded for now, update with actual payment method
//            paymentTable.addCell("Amount Paid:");
//            paymentTable.addCell(String.valueOf(booking.getTotal_price()));
//            paymentTable.addCell("Payment Date:");
//            paymentTable.addCell(booking.getCheckInDate().toString()); // Assuming payment date is the same as check-in date
//            document.add(paymentTable);
//
//            // Add cancellation policy
//            document.add(new Paragraph("Cancellation Policy:"));
//            document.add(new Paragraph("Guests can cancel their reservation up to 7 days before check-in for a full refund."));
//
//            document.close();
//
//            //another method to call email
//            emailService.sendEmailWithAttachment(
//                    booking.getEmail(),
//                    "Booking Confirmation.Your booking id is"+booking.getId(),
//                    "test",
//                            new File("C://booking_pdf//" + booking.getId() + "_booking_confirmation.pdf"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    {
////        try {
////
////        Document document = new Document();
////        PdfWriter.getInstance(document, new FileOutputStream("C://booking_pdf//"+booking.getId()+"_booking_confirmation.pdf"));
////
////        document.open();
////
////        PdfPTable table = new PdfPTable(3);
////        addTableHeader(table);
////        addRows(table,booking);
////       // addCustomRows(table);
////
////        document.add(table);
////        document.close();
////    }catch (Exception e)
////    {
////        e.printStackTrace();
////    }
////    }
////    private void addTableHeader(PdfPTable table) {
////        Stream.of("Guest_Name", "Hotel Name", "City")
////                .forEach(columnTitle -> {
////                    PdfPCell header = new PdfPCell();
////                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
////                    header.setBorderWidth(2);
////                    header.setPhrase(new Phrase(columnTitle));
////                    table.addCell(header);
////                });
////    }
////    private void addRows(PdfPTable table,Booking booking) {
////        table.addCell(booking.getGuestName());
////        table.addCell(booking.getProperty().getName());
////        table.addCell(booking.getProperty().getCity().getName());
////    }
//}
