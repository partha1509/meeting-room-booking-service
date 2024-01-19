package com.mashreq.meetingroom.booking.service;

import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.ReservationResponse;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;

public interface ReservationService {
    ReservationResponse makeReservation(TimeSlot timeSlot, Capacity capacity);
}
