package com.mashreq.meetingroom.booking.service;

import com.mashreq.meetingroom.booking.entity.Reservations;
import com.mashreq.meetingroom.booking.entity.RoomEntity;
import com.mashreq.meetingroom.booking.repository.MeetingRoomRepository;
import com.mashreq.meetingroom.booking.repository.ReservationRepository;
import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.ReservationResponse;
import com.mashreq.meetingroom.booking.service.dto.Room;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private RoomService roomService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MeetingRoomRepository meetingRoomRepository;
    @InjectMocks
    private ReservationServiceImpl reservationServiceImpl;

    @Test
    public void testShouldCreateReservationSuccessfully() {
        TimeSlot timeSlot = new TimeSlot(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Capacity capacity = new Capacity(10);
        Room room = new Room(1, "Amaze", 10, "27th floor");
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setCapacity(10);
        roomEntity.setName("Amaze");
        roomEntity.setCommissioned(true);
        roomEntity.setLocation("27floor");
        roomEntity.setId(2);

        when(roomService.getAvailableRoom(any(), any())).thenReturn(room);
        when(reservationRepository.save(any())).thenReturn(new Reservations());
        ReservationResponse response = reservationServiceImpl.makeReservation(timeSlot, capacity);
        assertNotNull(response);
    }
}
