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
@Table("reserved_spot")
public class ReservedSpot extends AbstractEntity {

    @PrimaryKeyColumn(name = "movie_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID movieId;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id;

    @PrimaryKeyColumn(name = "reservation_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private UUID reservationId;

    @Column("row")
    private Integer row;

    @Column("column")
    private Integer column;
}
