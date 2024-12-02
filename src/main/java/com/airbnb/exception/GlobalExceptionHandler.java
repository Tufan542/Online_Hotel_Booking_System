package com.airbnb.exception;

import com.airbnb.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ResourceNotFound.class)

public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
        ResourceNotFound ex //ex object will hold that thing and handle the exception
)
{
    ErrorDetails err= new ErrorDetails(ex.getMessage(),new Date());
    return  new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);//500
}
}
