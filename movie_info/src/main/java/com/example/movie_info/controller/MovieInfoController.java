package com.example.movie_info.controller;

import com.example.movie_info.dto.ClientDto;
import com.example.movie_info.service.MovieInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    public MovieInfoController(MovieInfoService movieInfoService){
        this.movieInfoService=movieInfoService;
    }

    @GetMapping("/information")
    public ResponseEntity<?> getMovieInfo(@RequestParam(name = "mid", required = false) String mid,
                                          @RequestParam(name = "tid", required = false) String tid,
                                          @RequestParam(name = "miday", required = false) String miday) throws JsonProcessingException, ParseException {
        ClientDto clientDto = new ClientDto(mid,tid,miday);
        return ResponseEntity.ok().body(movieInfoService.selectMovieInfo(clientDto));

    }

}
