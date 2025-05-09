package com.cinema.dao.repository;

import com.cinema.dao.model.Movie;
import lombok.NonNull;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends AbstractRepository<Movie> {
    @NonNull
    @Query("SELECT * FROM movie WHERE id = :id")
    Optional<Movie> findById(@NonNull UUID id);
}
