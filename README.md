# Meeting-room-booking-service
## Project & API Documentation Meeting Room Booking Service 

### Project Stack
` Java 17`
`Spring Boot 3`
` Maven`
`Junit 5`
` H2 Inmemory Database`

### Steps to Set Up the Project

1. Clone and Import the Project as a Maven project in your System
2. Execute `mvn clean install` which will help to download all dependency

### Steps to Run Test Cases
1. Execute ` mvn test` which will run Unit and Integration tests
2. Execute `mvn -Dtest= {AnyTest} test ` to run any particular test class.

### Steps to Run the Project Locally
1. Execute `mvn spring-boot:run` to run the application locally.


### In-Built SQL Script
The Project contains an in-built `SQL` script `data.sql` which gets executed when the application starts.
The Script helps to pre-populate the maintenance time and a few already booking entries which helps to test the 
Application effortlessly and effectively.

If you wish to reset the booking data, Just restarts the application.

## API Documentation: Meeting Room Reservations
## 1. Create Reservation
###This Endpoint is responsible for booking the reservation.

### Endpoint: 
``` 
POST /api/v1/bookings
```
Description: Create a reservation for a meeting room.

### Request:
```
Method: POST
URL: /api/v1/bookings
Headers:
Content-Type: application/json
```

### Body:
```
{
  "timeSlot": {
    "beginTime": "HH:mm:ss",
    "endTime": "HH:mm:ss"
  },
  "capacity": {
    "persons": 3
  }
```

### Response:
```
Status Code: 200 OK
{
  "roomId": 123,
  "beginTime": "HH:mm:ss",
  "endTime": "HH:mm:ss",
  "bookingDate": "yyyy-MM-dd",
  "bookingId": "abc123"
}
```
### Error Responses:
```
Status Code: 400 Bad Request

{
    "status": "BAD_REQUEST",
    "errorCode": "E_ROOMS_UNDER_MAINTENANCE",
    "errorDetails": "Meeting Rooms under Maintenance for the requested time."
}
Status Code: 500 Internal Server Error

{
  "error": "An unexpected error occurred while processing the request."
}
```
### Data Definitions
```
timeSlot Object:

beginTime (LocalTime): The beginning time of the reservation slot.
endTime (LocalTime): The ending time of the reservation slot.
capacity Object:
persons (int): The desired no of persons for booking.
```
```
### ReservationResponse Object:

roomId (Integer): The unique identifier of the reserved meeting room.
beginTime (LocalTime): The beginning time of the reservation.
endTime (LocalTime): The ending time of the reservation.
bookingDate (LocalDate): The date when the reservation was made.
bookingId (String): The unique identifier for the reservation.
```

## 2. Get Available Rooms
## Retrieve a list of available rooms within the specified time range.

### Endpoint
```
GET /api/v1/rooms
```
### Parameters
```
beginTime(LocalDateTime):	The start time of the desired time range.
endTime(LocalDateTime):	The end time of the desired time range.
```
### Request
```
Method: GET
URL: /api/v1/rooms
Example Request:

GET /api/v1/rooms?beginTime=2022-01-01T08:00:00&endTime=2022-01-01T12:00:00
```
### Response
```
HTTP Status Code: 200 OK
Example Response Body:

[
  {
    "name": "Meeting Room A",
    "capacity": 10,
    "location": "Floor 1"
  },
  {
    "name": "Conference Room B",
    "capacity": 20,
    "location": "Floor 2"
  }
  // ... other available rooms
]
```
### Error Responses
```
HTTP Status Code: 400 Bad Request

Response Body:

{
    "status": "BAD_REQUEST",
    "errorCode": "E_ROOMS_UNDER_MAINTENANCE",
    "errorDetails": "Meeting Rooms under Maintenance for the requested time."
}
HTTP Status Code: 500 Internal Server Error

Response Body:

{
  "error": "An internal server error occurred. Please try again later."
}
```
