package com.mashreq.meetingroom.booking.service;

import com.mashreq.meetingroom.booking.entity.Reservations;
import com.mashreq.meetingroom.booking.repository.ReservationRepository;
import com.mashreq.meetingroom.booking.service.converter.ReservationToDtoMapper;
import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.ReservationResponse;
import com.mashreq.meetingroom.booking.service.dto.Room;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    public static final String DEFAULT_PURPOSE = "Meeting";
    private final RoomService roomService;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse makeReservation(TimeSlot timeSlot, Capacity capacity) {
       Room room = roomService.getAvailableRoom(timeSlot,capacity);
        Reservations reservationToSave = new Reservations();
        reservationToSave.setRoomId(room.roomId());
        reservationToSave.setBookingDate(LocalDate.now());
        reservationToSave.setBeginTime(timeSlot.beginTime().toLocalTime());
        reservationToSave.setEndTime(timeSlot.endTime().toLocalTime());
        reservationToSave.setPurpose(DEFAULT_PURPOSE);
        Reservations reserved = reservationRepository.save(reservationToSave);

        log.info("Meeting room for given request {} has been booked successfully {}", timeSlot, reserved);
        return ReservationToDtoMapper.convert(reserved);

    }
}
