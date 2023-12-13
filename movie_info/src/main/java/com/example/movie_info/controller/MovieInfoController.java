package com.example.movie_info.controller;

import com.example.movie_info.dto.ClientDto;
import com.example.movie_info.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    public MovieInfoController(MovieInfoService movieInfoService){
        this.movieInfoService=movieInfoService;
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(){
        return ResponseEntity.ok().body(movieInfoService.getMovieInfo());
    }

    @GetMapping("/information")
    public ResponseEntity<?> getMovieInfo(@ModelAttribute ClientDto clientDto){
        return ResponseEntity.ok().body(movieInfoService.selectMovieInfo(clientDto));

    }

}
