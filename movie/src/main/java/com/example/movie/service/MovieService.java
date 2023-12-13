package com.example.movie.service;


import com.example.movie.dto.MovieDto;
import com.example.movie.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getAllMovie();


    Movie getDetails(String mid);

    List<Movie>  getScreenMovie(Map<String, String> requestMap);
    List<MovieDto>  getPlayMovie(String mid);


    List<Movie>  getComingMovie(Map<String, String> requestMap);
}
