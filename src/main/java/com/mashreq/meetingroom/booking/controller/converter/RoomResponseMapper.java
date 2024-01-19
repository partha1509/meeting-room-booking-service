package com.mashreq.meetingroom.booking.controller.converter;

import com.mashreq.meetingroom.booking.controller.dto.RoomResponse;
import com.mashreq.meetingroom.booking.service.dto.Room;


public class RoomResponseMapper {
    public static RoomResponse convert(Room source) {
       return new RoomResponse(source.name(), source.capacity(), source.name());
    }
}
