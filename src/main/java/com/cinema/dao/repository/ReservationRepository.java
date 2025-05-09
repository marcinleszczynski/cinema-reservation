package com.cinema.dao.repository;

import com.cinema.dao.model.Reservation;
import lombok.NonNull;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends AbstractRepository<Reservation> {
    @NonNull
    @Query("SELECT * FROM reservation WHERE id = :id")
    Optional<Reservation> findById(@NonNull UUID id);
}
