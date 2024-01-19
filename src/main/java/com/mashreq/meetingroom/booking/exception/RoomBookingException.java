package com.mashreq.meetingroom.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomBookingException extends RuntimeException{
    private String errorCode;
    private String message;
}
