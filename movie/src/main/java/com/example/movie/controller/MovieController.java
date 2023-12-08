package com.example.movie.controller;

import com.example.movie.service.MovieService;
import com.example.movie.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;
    public MovieController(MovieService movieService){
        this.movieService=movieService;

    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getMovies(){
        return ResponseEntity.ok().body(movieService.getAllMovie());
    }


    @GetMapping("/Moviedetail/{movieId}")
    public ResponseEntity<?> getDetails(@PathVariable String movieId){
        return ResponseEntity.ok().body(movieService.getDetails(movieId));
    }
}
