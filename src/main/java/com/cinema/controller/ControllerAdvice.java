package com.cinema.controller;

import com.cinema.service.exception.SpotTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    public ResponseEntity<String> handleSpotTaken(SpotTakenException e) {
        log.warn("Encountered spot taken exception: {}", Arrays.toString(e.getStackTrace()));
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
