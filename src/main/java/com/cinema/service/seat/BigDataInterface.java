package com.cinema.service.seat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.IntStream;

public class BigDataInterface {

    private final UUID CLIENT_ID = UUID.fromString("9f5e9576-6a53-44f8-ae25-f326e132c6c6");
    private final List<UUID> movies = List.of(
            UUID.fromString("5bbe194d-a929-4b4b-a48d-222ac0d81b07"),
            UUID.fromString("2307e68d-d264-4072-a58e-8a691d595d6b"),
            UUID.fromString("c0c4808a-da6f-4f34-a9e0-6d90b757023f")
    );
    private final List<UUID> reservations = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        var cliInterface = new BigDataInterface();
        cliInterface.run();
    }

    private void run() throws IOException, InterruptedException {

        var client = HttpClient.newHttpClient();
        var isRunning = true;

        while (isRunning) {
            System.out.println("Choose what to do:");
            System.out.println("    1. Make reservation");
            System.out.println("    2. Update reservation");
            System.out.println("    3. See reservation");
            System.out.println("    4. Exit\n");

            var validInput = chooseValidOption(4);

            switch (validInput) {
                case 1 -> {
                    System.out.println("Choose movie:");
                    movies.forEach(id -> {
                        var index = movies.indexOf(id);
                        System.out.printf("    %s. %s\n", index+1, id);
                    });
                    var chosenMovie = chooseValidOption(movies.size());
                    var chosenSeat = getSeatInput();
                    var requestBody = String.format("{\"movieId\": \"%s\", \"clientId\": \"%s\", \"seats\": [{\"row\": %s, \"column\": %s}]}", movies.get(chosenMovie - 1), CLIENT_ID, chosenSeat.row(), chosenSeat.column());
                    var request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/reservation"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    reservations.add(UUID.fromString(response.body().substring(1, response.body().length() - 1)));
                }
                case 2 -> {
                    System.out.println("Choose reservation:");
                    reservations.forEach(id -> {
                        var index = reservations.indexOf(id);
                        System.out.printf("    %s. %s\n", index+1, id);
                    });
                    var chosenReservation = chooseValidOption(reservations.size());
                    var chosenSeat = getSeatInput();
                    var requestBody = String.format("{\"seats\": [{\"row\": %s, \"column\": %s}]}", chosenSeat.row(), chosenSeat.column());
                    var request = HttpRequest.newBuilder()
                            .uri(URI.create(String.format("http://localhost:8080/reservation/%s", chosenReservation)))
                            .header("Content-Type", "application/json")
                            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    client.send(request, HttpResponse.BodyHandlers.ofString());
                }
                case 3 -> {
                    System.out.println("Choose reservation:");
                    reservations.forEach(id -> {
                        var index = reservations.indexOf(id);
                        System.out.printf("    %s. %s\n", index+1, id);
                    });
                    var chosenReservation = chooseValidOption(reservations.size());
                    var request = HttpRequest.newBuilder()
                            .uri(URI.create(String.format("http://localhost:8080/reservation/%s", reservations.get(chosenReservation - 1))))
                            .header("Content-Type", "application/json")
                            .GET()
                            .build();
                    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());
                }
                case 4 -> {
                    isRunning = false;
                }
            }
        }
    }

    private Integer chooseValidOption(int numberOfOptions) {
        while (true) {
            System.out.printf("Enter option %s: ", IntStream.range(1, numberOfOptions+1).boxed().toList());
            var input = new Scanner(System.in);
            var result = input.nextLine();
            try {
                var parsedInput = Integer.parseInt(result);
                if (parsedInput <= numberOfOptions && parsedInput >= 1) {
                    return Integer.parseInt(result);
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    private Seat getSeatInput() {
        System.out.print("Enter seat: ");
        var input = new Scanner(System.in);
        var row = input.nextInt();
        var column = input.nextInt();
        return new Seat(row, column);
    }

    private record Seat(Integer row, Integer column) {};
}
