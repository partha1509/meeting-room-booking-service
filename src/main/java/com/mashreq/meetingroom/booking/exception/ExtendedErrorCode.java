package com.mashreq.meetingroom.booking.exception;

import lombok.Getter;

@Getter
public enum ExtendedErrorCode implements ErrorCode{
    E_ROOMS_UNDER_MAINTENANCE("E_ROOMS_UNDER_MAINTENANCE",
            "Meeting Rooms under Maintenance for requested time."),
    E_ROOMS_NOT_AVAILABLE("E_ROOMS_NOT_AVAILABLE","Meeting Rooms not available for requested time."),
    E_INVALID_TIME_WINDOW("E_INVALID_TIME_WINDOW","Provided Time window is not valid."),
    E_INVALID_BOOKING_DATE("E_INVALID_BOOKING_DATE","Booking is not allowed on Pre or post date."),
    E_INVALID_TIME_FORMAT("E_INVALID_TIME_FORMAT",
            "Please provide booking time window in intervals of 15 minutes."),
    E_INVALID_TIME_DURATION_INPUT("E_INVALID_TIME_DURATION_INPUT",
            "Start Time and End Time Can not be same."),
    E_DATE_NOT_ALLOWED("E_DATE_NOT_ALLOWED","Requested Date is not allowed for booking");
    String errorCode;
    String message;
    ExtendedErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
