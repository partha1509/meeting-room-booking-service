package com.mashreq.meetingroom.booking.repository;

import com.mashreq.meetingroom.booking.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {
    @Query("select r.roomId from Reservations r where :beginTime < r.endTime and :endTime > r.beginTime ")
    List<Integer> getAlreadyReservedRooms(@Param("beginTime") LocalTime beginTime, @Param("endTime") LocalTime endTime);
}

