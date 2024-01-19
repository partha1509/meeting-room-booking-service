package com.mashreq.meetingroom.booking.service.dto;

public record Capacity(int persons) {

    public Capacity(int persons) {
        if (persons < 1)
            throw new IllegalArgumentException("No of person at least 1");
        this.persons = persons;
    }
}
