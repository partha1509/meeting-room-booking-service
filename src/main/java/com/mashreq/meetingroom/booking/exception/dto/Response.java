package com.mashreq.meetingroom.booking.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Response<T> {
    private HttpStatusCode status;
    private T data;
    private String errorCode;
    private String errorDetails;
}

