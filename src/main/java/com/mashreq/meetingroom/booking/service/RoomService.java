package com.mashreq.meetingroom.booking.service;

import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.Room;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;

import java.util.List;

public interface RoomService {
    List<Room> getAvailableRooms(TimeSlot timeSlot);

    Room getAvailableRoom(TimeSlot timeSlot, Capacity capacity);
}
