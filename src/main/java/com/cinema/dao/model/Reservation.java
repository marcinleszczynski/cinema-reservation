package com.cinema.dao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class Reservation extends AbstractEntity {

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id;

    @PrimaryKeyColumn(name = "movie_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("movie_id")
    private UUID movieId;

    @Column("client_id")
    private UUID clientId;
}
