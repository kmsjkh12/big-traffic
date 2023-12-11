package com.example.cinema.controller;

import com.example.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService){
        this.cinemaService=cinemaService;
    }
    @GetMapping("/cinema")
    public ResponseEntity<?> getCinema(){
        return ResponseEntity.ok().body(cinemaService.getCinema());
    }
}
