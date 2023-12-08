package com.example.movie.service;


import com.example.movie.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovie();


    Movie getDetails(String mid);
}
