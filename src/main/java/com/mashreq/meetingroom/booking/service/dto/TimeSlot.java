package com.mashreq.meetingroom.booking.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mashreq.meetingroom.booking.exception.RoomBookingException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.mashreq.meetingroom.booking.exception.ExtendedErrorCode.*;

public record TimeSlot(LocalDateTime beginTime, LocalDateTime endTime) {

    public static final int MINIMUM_TIME_INTERVAL = 15;

    public TimeSlot {
        if (Objects.isNull(beginTime))
            throw new IllegalArgumentException("Begin time is required");
        if (Objects.isNull(endTime))
            throw new IllegalArgumentException("End time is required");

        //Booking Date Validation
        if (!(beginTime.toLocalDate().isEqual(LocalDate.now())
                && endTime.toLocalDate().isEqual(LocalDate.now()))) {
            throw new RoomBookingException(E_INVALID_BOOKING_DATE.getErrorCode(), E_INVALID_BOOKING_DATE.getMessage());
        }
        // Time and Booking time interval validation
        if (beginTime.isAfter(endTime)) {
            throw new RoomBookingException(E_INVALID_TIME_WINDOW.getErrorCode(), E_INVALID_TIME_WINDOW.getMessage());
        } else {
            long minutes = Duration.between(beginTime, endTime).toMinutes();
            if (minutes % MINIMUM_TIME_INTERVAL != 0) {
                throw new RoomBookingException(E_INVALID_TIME_FORMAT.getErrorCode(), E_INVALID_TIME_FORMAT.getMessage());
            }
        }
        //When Start Time and End Time is Same
        if (Duration.between(beginTime, endTime).toMinutes() == 0) {
            throw new RoomBookingException(E_INVALID_TIME_DURATION_INPUT.getErrorCode(),
                    E_INVALID_TIME_DURATION_INPUT.getMessage());
        }
    }

}
