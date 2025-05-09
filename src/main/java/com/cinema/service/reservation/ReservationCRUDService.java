package com.cinema.service.reservation;

import com.cinema.controller.dto.MakeReservationRequestDto;
import com.cinema.dao.model.Reservation;
import com.cinema.dao.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationCRUDService {

    private final ReservationRepository reservationRepository;

    public Reservation findById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Reservation with id " + id + " not found"));
    }

    public Reservation createReservation(MakeReservationRequestDto dto) {
        var reservation = new Reservation();
        reservation.setMovieId(dto.movieId());
        reservation.setClientId(dto.clientId());
        return reservationRepository.save(reservation);
    }
}
