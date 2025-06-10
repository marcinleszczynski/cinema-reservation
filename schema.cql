CREATE KEYSPACE reservations WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 2};
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

INSERT INTO movie (id, name, date_of_movie, creation_timestamp, modification_timestamp)
VALUES (d3279b38-194c-4ef3-8969-35bfda9adc32, 'minecraft', null, null, null);
INSERT INTO movie (id, name, date_of_movie, creation_timestamp, modification_timestamp)
VALUES (5bbe194d-a929-4b4b-a48d-222ac0d81b07, 'godfather', null, null, null);
INSERT INTO movie (id, name, date_of_movie, creation_timestamp, modification_timestamp)
VALUES (2307e68d-d264-4072-a58e-8a691d595d6b, 'pulp fiction', null, null, null);
INSERT INTO movie (id, name, date_of_movie, creation_timestamp, modification_timestamp)
VALUES (c0c4808a-da6f-4f34-a9e0-6d90b757023f, 'bee movie', null, null, null);