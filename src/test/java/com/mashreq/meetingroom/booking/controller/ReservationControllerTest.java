package com.mashreq.meetingroom.booking.controller;

import com.mashreq.meetingroom.booking.repository.MeetingRoomRepository;
import com.mashreq.meetingroom.booking.repository.ReservationRepository;
import com.mashreq.meetingroom.booking.service.ReservationService;
import com.mashreq.meetingroom.booking.service.RoomService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationControllerTest {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private MockMvc mockMvc;

    ReservationController reservationController;
    @Before
    public void setUp(){
        this.mockMvc= MockMvcBuilders.standaloneSetup(reservationController).build();
    }
    @Test
    public void testShouldGetExceptionWhenRoomIsNotAvailable() throws Exception {

        String reservationRequest = """
                {
                  "timeSlot": {
                    "beginTime": "presentDayT08:00:00",
                    "endTime": "presentDayT08:30:00"
                  },
                  "capacity": {
                    "persons": 17
                  }
                }
                """.replace("presentDay",LocalDate.now().toString());

        mockMvc.perform(post("/api/v1/bookings")
                        .content(reservationRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value( "BAD_REQUEST"))
                .andExpect(jsonPath("$.errorCode").value("E_ROOMS_NOT_AVAILABLE"));
    }
    @Test
    public void testShouldCreateReservation() throws Exception {

        String reservationRequest = """
                {
                  "timeSlot": {
                    "beginTime": "presentDayT16:00:00",
                    "endTime": "presentDayT16:30:00"
                  },
                  "capacity": {
                    "persons": 3
                  }
                }
                """.replace("presentDay",LocalDate.now().toString());

        mockMvc.perform(post("/api/v1/bookings")
                .content(reservationRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value( 1))
                .andExpect(jsonPath("$.bookingId").isString());
    }
}
