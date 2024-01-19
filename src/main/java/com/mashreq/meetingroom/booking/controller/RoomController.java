package com.mashreq.meetingroom.booking.controller;

import com.mashreq.meetingroom.booking.controller.converter.RoomResponseMapper;
import com.mashreq.meetingroom.booking.controller.dto.RoomResponse;
import com.mashreq.meetingroom.booking.service.RoomService;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam LocalDateTime beginTime,
            @RequestParam LocalDateTime endTime) {
        List<RoomResponse> availableRooms = roomService.getAvailableRooms(new TimeSlot(beginTime, endTime))
                .stream()
                .map(RoomResponseMapper::convert)
                .toList();
        return ResponseEntity.ok(availableRooms);
    }

}
