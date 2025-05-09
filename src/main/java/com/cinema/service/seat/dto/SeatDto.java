package com.cinema.service.seat.dto;

import lombok.Builder;

@Builder
public record SeatDto(
        int row,
        int column
) {}
