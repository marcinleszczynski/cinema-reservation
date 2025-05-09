package com.cinema.controller;

import com.cinema.controller.dto.MakeReservationRequestDto;
import com.cinema.controller.dto.ReservationDetailsDto;
import com.cinema.controller.dto.UpdateReservationRequestDto;
import com.cinema.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> makeReservation(@RequestBody MakeReservationRequestDto request) {
        log.info("Making reservation for movie with id: {}", request.movieId());
        reservationService.makeReservation(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReservation(@PathVariable UUID id, @RequestBody UpdateReservationRequestDto request) {
        log.info("Updating reservation with id {}", id);
        reservationService.updateReservation(id, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDetailsDto> getReservationDetails(@PathVariable UUID id) {
        log.info("Getting reservation details for reservation with id: {}", id);
        return ResponseEntity.ok().body(reservationService.getReservationDetails(id));
    }
}
