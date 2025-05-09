package com.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackageClasses = CinemaReservationApplication.class)
public class CinemaReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaReservationApplication.class, args);
    }

}
