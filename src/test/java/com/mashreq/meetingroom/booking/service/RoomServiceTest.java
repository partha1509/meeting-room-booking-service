package com.mashreq.meetingroom.booking.service;

import com.mashreq.meetingroom.booking.entity.RoomEntity;
import com.mashreq.meetingroom.booking.exception.RoomBookingException;
import com.mashreq.meetingroom.booking.repository.MeetingRoomRepository;
import com.mashreq.meetingroom.booking.repository.ReservationRepository;
import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.Room;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @Mock
    private MeetingRoomRepository meetingRoomRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private RoomServiceImpl roomServiceImpl;

    @Test
    public void testShouldGetExceptionWhenRequestedCapacityIsWrong() {
        IllegalArgumentException capacityException = assertThrows(IllegalArgumentException.class,
                () -> new Capacity(0));
        assertEquals("No of person at least 1", capacityException.getMessage());
    }

    @Test
    public void testShouldGetExceptionWhenGivenStartTimesInvalid() {
        IllegalArgumentException beginTimeTimesInvalid = assertThrows(IllegalArgumentException.class,
                () -> new TimeSlot(null, LocalDateTime.now()));
        assertEquals("Begin time is required", beginTimeTimesInvalid.getMessage());
    }

    @Test
    public void testShouldGetExceptionWhenGivenEndTimeTimesInvalid() {
        IllegalArgumentException endTimeTimesInvalid = assertThrows(IllegalArgumentException.class,
                () -> new TimeSlot(LocalDateTime.now(), null));
        assertEquals("End time is required", endTimeTimesInvalid.getMessage());
    }

    @Test
    public void testShouldGetExceptionWhenGivenDateIsPreOrPostDate() {
        RoomBookingException dateException = assertThrows(RoomBookingException.class,
                () -> new TimeSlot(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(5).plusHours(1)));
        assertEquals("E_INVALID_BOOKING_DATE", dateException.getErrorCode());
    }

    @Test
    public void testShouldGetExceptionWhenGivenStartTimeGreaterThanEndTime() {
        RoomBookingException timeException = assertThrows(RoomBookingException.class,
                () -> new TimeSlot(LocalDateTime.now().withHour(8).withMinute(30),
                        LocalDateTime.now().withHour(8).withMinute(0)));
        assertEquals("E_INVALID_TIME_WINDOW", timeException.getErrorCode());
    }

    @Test
    public void testShouldGetExceptionWhenGivenTimeIntervalIsInvalid() {
        RoomBookingException timeException = assertThrows(RoomBookingException.class,
                () -> new TimeSlot(LocalDateTime.now().withHour(8).withMinute(0),
                        LocalDateTime.now().withHour(8).withMinute(20)));
        assertEquals("E_INVALID_TIME_FORMAT", timeException.getErrorCode());
    }

    @Test
    public void testShouldGetExceptionWhenGivenStartTimeAndEndTimeIsSame() {
        RoomBookingException timeException = assertThrows(RoomBookingException.class,
                () -> new TimeSlot(LocalDateTime.now().withHour(8).withMinute(0),
                        LocalDateTime.now().withHour(8).withMinute(0)));
        assertEquals("E_INVALID_TIME_DURATION_INPUT", timeException.getErrorCode());
    }

    @Test
    public void testShouldGetExceptionWhenAllRoomsInMaintenance() {
        when(meetingRoomRepository.getAvailableRooms(any(), any())).thenReturn(Arrays.asList());
        RoomBookingException dateException = assertThrows(RoomBookingException.class,
                () -> roomServiceImpl.getAvailableRooms(new TimeSlot(LocalDateTime.now().withHour(9).withMinute(0),
                        LocalDateTime.now().withHour(9).withMinute(15))));
        assertEquals("E_ROOMS_UNDER_MAINTENANCE", dateException.getErrorCode());

    }

    @Test
    public void testShouldGetAllAvailableRoomOnGivenTime() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setCapacity(10);
        roomEntity.setName("Amaze");
        roomEntity.setCommissioned(true);
        roomEntity.setLocation("27floor");
        roomEntity.setId(2);

        RoomEntity roomEntity2 = new RoomEntity();
        roomEntity2.setCapacity(20);
        roomEntity2.setName("beauty");
        roomEntity2.setCommissioned(true);
        roomEntity2.setLocation("26floor");
        roomEntity2.setId(3);
        when(meetingRoomRepository.getAvailableRooms(any(), any())).thenReturn(Arrays.asList(roomEntity, roomEntity2));
        when(reservationRepository.getAlreadyReservedRooms(any(), any())).thenReturn(Arrays.asList(3));
        List<Room> rooms = roomServiceImpl.getAvailableRooms(new TimeSlot(LocalDateTime.now().withHour(10).withMinute(0),
                LocalDateTime.now().withHour(10).withMinute(15)));
        assertEquals(1, rooms.size());

    }

    @Test
    public void testShouldExceptionWhenAvailableRoomOnGivenTimeToBook() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setCapacity(10);
        roomEntity.setName("Amaze");
        roomEntity.setCommissioned(true);
        roomEntity.setLocation("27floor");
        roomEntity.setId(2);

        RoomEntity roomEntity2 = new RoomEntity();
        roomEntity2.setCapacity(20);
        roomEntity2.setName("beauty");
        roomEntity2.setCommissioned(true);
        roomEntity2.setLocation("26floor");
        roomEntity2.setId(3);
        when(meetingRoomRepository.getAvailableRooms(any(), any())).thenReturn(Arrays.asList(roomEntity, roomEntity2));
        when(reservationRepository.getAlreadyReservedRooms(any(), any())).thenReturn(Arrays.asList(3));
        RoomBookingException roomException = assertThrows(RoomBookingException.class,
                () -> roomServiceImpl.getAvailableRoom(new TimeSlot(LocalDateTime.now().withHour(10).withMinute(0),
                        LocalDateTime.now().withHour(10).withMinute(15)), new Capacity(12)));
        assertEquals("E_ROOMS_NOT_AVAILABLE", roomException.getErrorCode());
    }

    @Test
    public void testShouldGetOptimalAvailableRoomOnGivenTimeToBook() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setCapacity(12);
        roomEntity.setName("Amaze");
        roomEntity.setCommissioned(true);
        roomEntity.setLocation("27floor");
        roomEntity.setId(2);

        RoomEntity roomEntity2 = new RoomEntity();
        roomEntity2.setCapacity(20);
        roomEntity2.setName("beauty");
        roomEntity2.setCommissioned(true);
        roomEntity2.setLocation("26floor");
        roomEntity2.setId(3);
        when(meetingRoomRepository.getAvailableRooms(any(), any())).thenReturn(Arrays.asList(roomEntity, roomEntity2));
        when(reservationRepository.getAlreadyReservedRooms(any(), any())).thenReturn(Arrays.asList(4));
        Room room = roomServiceImpl.getAvailableRoom(new TimeSlot(LocalDateTime.now().withHour(10).withMinute(0),
                LocalDateTime.now().withHour(10).withMinute(15)), new Capacity(12));
        assertNotNull(room);
        assertEquals(Integer.valueOf(2), room.roomId());
    }
}
