package com.mashreq.meetingroom.booking.controller;

import com.jayway.jsonpath.JsonPath;
import com.mashreq.meetingroom.booking.repository.MeetingRoomRepository;
import com.mashreq.meetingroom.booking.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomControllerTest {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetAllAvailableRoomsWithEqualOrHigherCapacity() throws Exception {
        String capacity = "3";
        String beginTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 0, 0).toString();
        String endTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 30, 0).toString();

        MvcResult result =mockMvc.perform(get("/api/v1/rooms")
                        .queryParam("capacity", capacity)
                        .queryParam("beginTime", beginTime)
                        .queryParam("endTime", endTime))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        List<Map<String, Object>> rooms = JsonPath.read(responseJson, "$");
        //2 rooms will be available as another room is booked already(sql script)
        assertTrue(rooms.size()==3);
    }

    @Test
    public void testShouldGetExceptionWhenRoomsUnderMaintenance() throws Exception {
        // Arrange
        String capacity = "3";
        String beginTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 0, 0).toString();
        String endTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 9, 30, 0).toString();

        mockMvc.perform(get("/api/v1/rooms")
                        .queryParam("capacity", capacity)
                        .queryParam("beginTime", beginTime)
                        .queryParam("endTime", endTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorCode").value("E_ROOMS_UNDER_MAINTENANCE"));
    }

    @Test
    public void testShouldGetExceptionWhenGivenTimeIsInvalid() throws Exception {
        // Arrange
        String capacity = "3";
        String beginTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 30, 0).toString();
        String endTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 0, 0).toString();

        mockMvc.perform(get("/api/v1/rooms")
                        .queryParam("capacity", capacity)
                        .queryParam("beginTime", beginTime)
                        .queryParam("endTime", endTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorCode").value("E_INVALID_TIME_WINDOW"));
    }
    @Test
    public void testShouldGetExceptionWhenGivenTimeIntervalIsInvalid() throws Exception {
        // Arrange
        String capacity = "3";
        String beginTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 0, 0).toString();
        String endTime = LocalDateTime.of(LocalDate.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), 8, 20, 0).toString();

        mockMvc.perform(get("/api/v1/rooms")
                        .queryParam("capacity", capacity)
                        .queryParam("beginTime", beginTime)
                        .queryParam("endTime", endTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorCode").value("E_INVALID_TIME_FORMAT"));
    }

    @Test
    public void testShouldGetExceptionWhenGivenBookingDateIsPreOrPostDate() throws Exception {
        // Arrange
        String capacity = "3";
        String beginTime = LocalDateTime.now().withHour(8).withMinute(0).toString();
        String endTime = LocalDateTime.now().plusDays(5).withHour(8).withMinute(30).toString();

        mockMvc.perform(get("/api/v1/rooms")
                        .queryParam("capacity", capacity)
                        .queryParam("beginTime", beginTime)
                        .queryParam("endTime", endTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorCode").value("E_INVALID_BOOKING_DATE"));
    }

    @Test
    public void testShouldGetExceptionWhenGivenStartAndEndTimeIsSame() throws Exception {
        String capacity = "3";
        String beginTime = LocalDateTime.now().withHour(8).withMinute(0).toString();
        String endTime = LocalDateTime.now().withHour(8).withMinute(0).toString();

        mockMvc.perform(get("/api/v1/rooms")
                        .queryParam("capacity", capacity)
                        .queryParam("beginTime", beginTime)
                        .queryParam("endTime", endTime))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorCode").value("E_INVALID_TIME_DURATION_INPUT"));
    }
}
