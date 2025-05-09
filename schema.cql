CREATE KEYSPACE reservations WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE reservations;

CREATE TABLE movie (
    id uuid,
    name varchar,
    date_of_movie timestamp,
    creation_timestamp timestamp,
    modification_timestamp timestamp,
    PRIMARY KEY ( name, id )
);

CREATE TABLE reservation (
    id uuid,
    movie_id uuid,
    client_id uuid,
    creation_timestamp timestamp,
    modification_timestamp timestamp,
    PRIMARY KEY ( movie_id, id )
);

CREATE TABLE reserved_spot (
    id uuid,
    reservation_id uuid,
    movie_id uuid,
    row int,
    column int,
    creation_timestamp timestamp,
    modification_timestamp timestamp,
    PRIMARY KEY ( movie_id, id, reservation_id )
);

CREATE INDEX ON reservation(id);
CREATE INDEX ON reserved_spot(reservation_id);
CREATE INDEX ON movie(id);