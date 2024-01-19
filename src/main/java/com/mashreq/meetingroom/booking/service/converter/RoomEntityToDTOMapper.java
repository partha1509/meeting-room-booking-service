package com.mashreq.meetingroom.booking.service.converter;

import com.mashreq.meetingroom.booking.entity.RoomEntity;
import com.mashreq.meetingroom.booking.service.dto.Room;


public class RoomEntityToDTOMapper{

    public static Room convert(RoomEntity source) {
       return new Room(source.getId(),source.getName(),source.getCapacity(),source.getLocation());
    }
}
