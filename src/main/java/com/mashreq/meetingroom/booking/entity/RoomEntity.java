package com.mashreq.meetingroom.booking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="meeting_room")
@Data
public class RoomEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="capacity")
    private Integer capacity;

    @Column(name="location")
    private String location;
    @Column(name = "commissioned")
    private Boolean commissioned;
    @OneToMany(targetEntity = RoomMaintenance.class, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private List<RoomMaintenance> roomMaintenances;

}
