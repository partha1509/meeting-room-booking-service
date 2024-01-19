package com.mashreq.meetingroom.booking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "room_reservations")
@Data
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "begin_time")
    private LocalTime beginTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "booking_date")
    private LocalDate bookingDate;
    @Column(name = "booking_id")
    private String bookingId= UUID.randomUUID().toString();
}
