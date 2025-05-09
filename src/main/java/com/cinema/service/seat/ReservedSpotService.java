package com.cinema.service.seat;

import com.cinema.controller.dto.MakeReservationRequestDto;
import com.cinema.controller.dto.UpdateReservationRequestDto;
import com.cinema.dao.model.Reservation;
import com.cinema.dao.model.ReservedSpot;
import com.cinema.dao.repository.ReservedSpotRepository;
import com.cinema.service.seat.dto.SeatDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservedSpotService {

    private final ReservedSpotRepository reservedSpotRepository;

    public List<ReservedSpot> findReservedSpotsByReservationId(UUID reservationId) {
        return reservedSpotRepository.findByReservationId(reservationId);
    }

    public List<ReservedSpot> findByMovieId(UUID movieId) {
        return reservedSpotRepository.findByMovieId(movieId);
    }

    public void createReservedSpots(MakeReservationRequestDto request, UUID reservationId) {
        request.seats().forEach(seat -> {
            var reservedSpot = new ReservedSpot();
            reservedSpot.setReservationId(reservationId);
            reservedSpot.setMovieId(request.movieId());
            reservedSpot.setRow(seat.row());
            reservedSpot.setColumn(seat.column());
            reservedSpotRepository.save(reservedSpot);
        });
    }

    public void updateReservedSpots(UpdateReservationRequestDto request, Reservation reservation) {
        request.seats().forEach(seat -> {
            var reservedSpot = new ReservedSpot();
            reservedSpot.setReservationId(reservation.getId());
            reservedSpot.setMovieId(reservation.getMovieId());
            reservedSpot.setRow(seat.row());
            reservedSpot.setColumn(seat.column());
            reservedSpotRepository.save(reservedSpot);
        });
    }

    public void ensureSpotsAreFreeOrTakenBy(UUID movieId, List<SeatDto> seats, UUID excludedReservationId) {
        var isAtLeastOneSpotTaken = findByMovieId(movieId)
                .stream()
                .filter(seat -> !seat.getReservationId().equals(excludedReservationId))
                .map(reservedSpot -> new SeatDto(reservedSpot.getRow(), reservedSpot.getColumn()))
                .anyMatch(seats::contains);
        if (isAtLeastOneSpotTaken) {
            throw new IllegalStateException("At least one spot is already taken");
        }
    }

    public void removeReservedSpotsByReservationId(UUID reservationId) {
        var seats = reservedSpotRepository.findByReservationId(reservationId);
        log.info("SEATS: {}", seats);
        reservedSpotRepository.deleteAll(seats);
    }
}
