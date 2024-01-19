package com.mashreq.meetingroom.booking.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Integer roomId, LocalTime beginTime, LocalTime endTime,
                                  LocalDate bookingDate,String bookingId) {
}
