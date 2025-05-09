package com.cinema.service.seat.mapper;

import com.cinema.dao.model.ReservedSpot;
import com.cinema.service.seat.dto.SeatDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class SeatMapper {

    public static SeatDto map(ReservedSpot reservedSpot) {
        return SeatDto.builder()
                .row(reservedSpot.getRow())
                .column(reservedSpot.getColumn())
                .build();
    }

    public static List<SeatDto> map(List<ReservedSpot> reservedSpots) {
        return reservedSpots.stream()
                .map(SeatMapper::map)
                .toList();
    }
}
