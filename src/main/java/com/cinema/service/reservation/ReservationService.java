package com.cinema.service.reservation;

import com.cinema.controller.dto.MakeReservationRequestDto;
import com.cinema.controller.dto.ReservationDetailsDto;
import com.cinema.controller.dto.UpdateReservationRequestDto;
import com.cinema.service.movie.MovieService;
import com.cinema.service.seat.ReservedSpotService;
import com.cinema.service.utils.LockUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.cinema.service.seat.mapper.SeatMapper.map;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationCRUDService crud;
    private final ReservedSpotService reservedSpotService;
    private final MovieService movieService;

    public UUID makeReservation(MakeReservationRequestDto request) {
        synchronized (LockUtils.lockFor(request.movieId())) {
            reservedSpotService.ensureSpotsAreFreeOrTakenBy(request.movieId(), request.seats(), null);
            var reservation = crud.createReservation(request);
            reservedSpotService.createReservedSpots(request, reservation.getId());
            return reservation.getId();
        }
    }

    public UUID updateReservation(UUID reservationId, UpdateReservationRequestDto request) {
        var reservation = crud.findById(reservationId);
        reservedSpotService.ensureSpotsAreFreeOrTakenBy(reservation.getMovieId(), request.seats(), reservationId);
        reservedSpotService.removeReservedSpotsByReservationId(reservationId);
        reservedSpotService.updateReservedSpots(request, reservation);
        return reservation.getId();
    }

    public ReservationDetailsDto getReservationDetails(UUID reservationId) {
        var reservation = crud.findById(reservationId);
        var seats = reservedSpotService.findReservedSpotsByReservationId(reservation.getId());
        var movie = movieService.findMovieById(reservation.getMovieId());
        return ReservationDetailsDto.builder()
                .reservationId(reservation.getId())
                .seats(map(seats))
                .reservationId(reservation.getId())
                .clientId(reservation.getClientId())
                .movieId(movie.getId())
                .movieTime(movie.getDateOfMovie())
                .movieName(movie.getName())
                .build();
    }
}
