package com.example.movie.service;

import com.example.movie.entity.Movie;
import com.example.movie.repository.MovieRepository;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository= movieRepository;

    }
    @Override
    public List<Movie> getAllMovie(){
        List<Movie> movies = movieRepository.findAll();

        return movies;
    }

    @Override
    public Movie getDetails(String mid) {
        return movieRepository.findByMid(Long.valueOf(mid));
    }

    @Override
    public List<Movie>  getScreenMovie(Map<String, String> requestMap) {
        return null;
    }

    @Override
    public List<Movie>  getComingMovie(Map<String, String> requestMap) {
        return null;
    }
}
