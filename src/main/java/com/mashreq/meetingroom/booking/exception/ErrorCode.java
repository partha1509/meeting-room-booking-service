package com.mashreq.meetingroom.booking.exception;

public interface ErrorCode {
    String name();

    default String customErrorCode() {
        return this.name();
    }
}
