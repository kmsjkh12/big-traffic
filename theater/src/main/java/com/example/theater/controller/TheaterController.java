package com.example.theater.controller;

import com.example.theater.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    public TheaterController(TheaterService theaterService){
        this.theaterService=theaterService;

    }

    @GetMapping("/theater")
    public ResponseEntity<?> getTheater(){
        return ResponseEntity.ok().body(theaterService.requestTheater());
    }

    @GetMapping("/gettheater")
    public ResponseEntity<?> responseTheater(@RequestParam("list") String list){
        return ResponseEntity.ok().body(theaterService.responseTheater(list));
    }

}
