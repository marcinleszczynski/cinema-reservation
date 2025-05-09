package com.cinema.stress;

import com.cinema.dao.model.Movie;

import java.util.Date;
import java.util.UUID;

public class Mocks {

    public static final UUID MINECRAFT_MOVIE_ID = UUID.fromString("37e17758-a93f-4d07-8ea0-6cdb6874f01e");
    public static final UUID CLIENT_1_ID = UUID.fromString("172c7033-1781-40ec-af15-7fac12158257");
    public static final UUID CLIENT_2_ID = UUID.fromString("ebf53962-e151-4cac-8263-55cbcaa49529");

    public static Movie movie() {
        var result = new Movie();
        result.setId(MINECRAFT_MOVIE_ID);
        result.setName("minecraft");
        result.setDateOfMovie(new Date());
        return result;
    }
}
