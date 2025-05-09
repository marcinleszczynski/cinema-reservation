package com.cinema.dao.repository;

import com.cinema.dao.model.ReservedSpot;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservedSpotRepository extends AbstractRepository<ReservedSpot> {

    @Query("SELECT * FROM reserved_spot WHERE reservation_id = :seatId")
    List<ReservedSpot> findByReservationId(UUID seatId);

    List<ReservedSpot> findByMovieId(UUID movieId);
}
