package com.mashreq.meetingroom.booking.controller.dto;

import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;


public record ReservationRequest(TimeSlot timeSlot, Capacity capacity) {
}
