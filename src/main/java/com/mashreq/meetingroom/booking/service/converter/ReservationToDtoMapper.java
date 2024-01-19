package com.mashreq.meetingroom.booking.service.converter;

import com.mashreq.meetingroom.booking.entity.Reservations;
import com.mashreq.meetingroom.booking.service.dto.ReservationResponse;

public class ReservationToDtoMapper {
    public static ReservationResponse convert(Reservations source) {
        return new ReservationResponse(source.getRoomId(),source.getBeginTime(),source.getEndTime(),
                source.getBookingDate(), source.getBookingId());
    }
}
