package com.mashreq.meetingroom.booking.controller;

import com.mashreq.meetingroom.booking.controller.dto.ReservationRequest;
import com.mashreq.meetingroom.booking.service.dto.ReservationResponse;
import com.mashreq.meetingroom.booking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/bookings")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        log.info("Meeting room reservation request received {}", reservationRequest);
        ReservationResponse reservationResponse = reservationService
                .makeReservation(reservationRequest.timeSlot(),reservationRequest.capacity());
        log.info("Meeting room Successfully booked {}", reservationResponse);
        return ResponseEntity.ok(reservationResponse);
    }
}
