package com.cinema.controller.dto;

import com.cinema.service.seat.dto.SeatDto;
import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
public record ReservationDetailsDto(
        UUID reservationId,
        UUID clientId,
        UUID movieId,
        String movieName,
        Date movieTime,
        List<SeatDto> seats
) {}
