package com.mashreq.meetingroom.booking.exception;

import com.mashreq.meetingroom.booking.exception.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoomBookingAdvice {

    @ExceptionHandler(RoomBookingException.class)
    public ResponseEntity<Object> handleException(RoomBookingException exception) {
        return new ResponseEntity<>(Response.builder().errorCode(exception.getErrorCode())
                .errorDetails(exception.getMessage()).status(HttpStatus.BAD_REQUEST).build(), HttpStatus.OK);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = "Invalid input: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
