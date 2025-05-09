package com.cinema.controller.dto;

import com.cinema.service.seat.dto.SeatDto;

import java.util.List;
import java.util.UUID;

public record MakeReservationRequestDto(
        UUID clientId,
        UUID movieId,
        List<SeatDto> seats
) {}
