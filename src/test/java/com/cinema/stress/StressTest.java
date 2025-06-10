package com.cinema.stress;

import com.cinema.controller.dto.MakeReservationRequestDto;
import com.cinema.dao.repository.MovieRepository;
import com.cinema.dao.repository.ReservationRepository;
import com.cinema.dao.repository.ReservedSpotRepository;
import com.cinema.service.reservation.ReservationService;
import com.cinema.service.seat.dto.SeatDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static com.cinema.stress.Mocks.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StressTest {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservedSpotRepository reservedSpotRepository;
    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
        reservationRepository.deleteAll();
        reservedSpotRepository.deleteAll();
        movieRepository.save(movie());
    }

    @AfterEach
    public void tearDown() {
        movieRepository.deleteAll();
        reservationRepository.deleteAll();
        reservedSpotRepository.deleteAll();
    }

    @Test
    public void stressTest1() {
        var seats = List.of(new SeatDto(4, 1), new SeatDto(5, 1), new SeatDto(6, 1));
        var reservationRequest = new MakeReservationRequestDto(CLIENT_1_ID, MINECRAFT_MOVIE_ID, seats);
        List<Runnable> requests = List.of(
                () -> reservationService.makeReservation(reservationRequest),
                () -> reservationService.makeReservation(reservationRequest),
                () -> reservationService.makeReservation(reservationRequest)
        );
        try (var executor = Executors.newCachedThreadPool()) {
            requests.forEach(executor::execute);
        }
        var finalSeats = reservedSpotRepository.findAll()
                .stream()
                .map(reservedSeat -> new SeatDto(reservedSeat.getRow(), reservedSeat.getColumn()))
                .toList();
        assertEquals(3, finalSeats.size());
        assertTrue(finalSeats.contains(seats.getFirst()));
        assertTrue(finalSeats.contains(seats.get(1)));
        assertTrue(finalSeats.contains(seats.getLast()));
    }

    @Test
    public void stressTest2() {
        int rows = 5;
        int columns = 15;
        var random = new Random();
        var seatList = new ArrayList<SeatDto>();
        IntStream.range(0, rows)
            .forEach(i -> {
                IntStream.range(0, columns)
                    .forEach(j -> {
                        seatList.add(new SeatDto(i, j));
                    });
            });
        Collections.shuffle(seatList);
        int split1 = random.nextInt(seatList.size());
        int split2 = random.nextInt(seatList.size());
        var requests = new ArrayList<Runnable>();
        seatList.subList(0, split1)
                .forEach(seat -> requests.add(() -> {
                    var request = new MakeReservationRequestDto(CLIENT_1_ID, MINECRAFT_MOVIE_ID, List.of(seat));
                    reservationService.makeReservation(request);
                }));
        seatList.subList(split2, seatList.size())
                .forEach(seat -> requests.add(() -> {
                    var request = new MakeReservationRequestDto(CLIENT_2_ID, MINECRAFT_MOVIE_ID, List.of(seat));
                    reservationService.makeReservation(request);
                }));
        try (var executor = Executors.newCachedThreadPool()) {
            requests.forEach(executor::execute);
        }

        var finalSeats = reservedSpotRepository.findAll();
        if (split1 > split2) {
            assertEquals(75, finalSeats.size());
        } else {
            var expectedSeats = split1 + (seatList.size() - split2);
            assertEquals(expectedSeats, finalSeats.size());
        }
    }

}
