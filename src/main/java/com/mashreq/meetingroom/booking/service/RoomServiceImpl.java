package com.mashreq.meetingroom.booking.service;

import com.mashreq.meetingroom.booking.service.converter.RoomEntityToDTOMapper;
import com.mashreq.meetingroom.booking.service.dto.Capacity;
import com.mashreq.meetingroom.booking.service.dto.Room;
import com.mashreq.meetingroom.booking.service.dto.TimeSlot;
import com.mashreq.meetingroom.booking.entity.RoomEntity;
import com.mashreq.meetingroom.booking.exception.RoomBookingException;
import com.mashreq.meetingroom.booking.repository.MeetingRoomRepository;
import com.mashreq.meetingroom.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.mashreq.meetingroom.booking.exception.ExtendedErrorCode.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Room> getAvailableRooms(TimeSlot timeSlot) {
        return allAvailableRooms(timeSlot).stream()
                .map(RoomEntityToDTOMapper::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Room getAvailableRoom(TimeSlot timeSlot, Capacity capacity) {
        RoomEntity roomEntity = allAvailableRooms(timeSlot).stream()
                .sorted(Comparator.comparing(RoomEntity::getCapacity))
                .filter(s -> s.getCapacity() >= capacity.persons())
                .findFirst()
                .orElseThrow(() -> new RoomBookingException(E_ROOMS_NOT_AVAILABLE.getErrorCode(),
                        E_ROOMS_NOT_AVAILABLE.getMessage()));
        return RoomEntityToDTOMapper.convert(roomEntity);
    }


    private List<RoomEntity> allAvailableRooms(TimeSlot timeSlot) {
        List<RoomEntity> availableMeetingRoomEntities = meetingRoomRepository
                .getAvailableRooms(timeSlot.beginTime().toLocalTime(), timeSlot.endTime().toLocalTime());
        if (CollectionUtils.isEmpty(availableMeetingRoomEntities)) {
            throw new RoomBookingException(E_ROOMS_UNDER_MAINTENANCE.getErrorCode(),
                    E_ROOMS_UNDER_MAINTENANCE.getMessage());
        }
        log.info("Total available meeting rooms : {}", availableMeetingRoomEntities.size());

        List<Integer> alreadyReservedRooms = reservationRepository
                .getAlreadyReservedRooms(timeSlot.beginTime().toLocalTime(), timeSlot.endTime().toLocalTime());
        log.info("Total booked meeting rooms for given time window: {}", alreadyReservedRooms.size());
        return availableMeetingRoomEntities.stream()
                .filter(r -> !alreadyReservedRooms.contains(r.getId()))
                .toList();
    }
}
