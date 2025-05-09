package com.cinema.controller.dto;

import com.cinema.service.seat.dto.SeatDto;

import java.util.List;

public record UpdateReservationRequestDto(
        List<SeatDto> seats
) {}
