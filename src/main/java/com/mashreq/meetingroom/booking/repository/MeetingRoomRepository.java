package com.mashreq.meetingroom.booking.repository;

import com.mashreq.meetingroom.booking.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<RoomEntity, Integer> {
    @Query("""
            select r from RoomEntity r 
            where NOT EXISTS (
            select 1 from r.roomMaintenances m 
            where :beginTime < m.endTime and :endTime > m.beginTime )
           """)
    List<RoomEntity> getAvailableRooms(@Param("beginTime")LocalTime beginTime, @Param("endTime")LocalTime endTime);

}
