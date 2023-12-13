package com.example.movie.controller;

import com.example.movie.service.MovieService;
import com.example.movie.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    // 현재상영작 영화 가져오는 메소드
    @GetMapping("/screenmovie")
    public ResponseEntity<?> ScreenMovie(@RequestParam Map<String, String> requestMap) {
        return ResponseEntity.ok().body(movieService.getScreenMovie(requestMap));
    }

    // 상영예정작 영화 가져오는 메소드
    @GetMapping("/comingmovie")
    public ResponseEntity<?> ComingMovie(@RequestParam Map<String, String> requestMap) {
        return ResponseEntity.ok().body(movieService.getComingMovie(requestMap));
    }

    @GetMapping("/playmovies")
    public ResponseEntity<?> handleRequest(@RequestParam("list") String list) {
        return ResponseEntity.ok().body(movieService.getPlayMovie(list));

    }

}
