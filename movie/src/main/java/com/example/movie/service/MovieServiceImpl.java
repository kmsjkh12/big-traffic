package com.example.movie.service;

import com.example.movie.dto.MovieDto;
import com.example.movie.entity.Movie;
import com.example.movie.parse.Parse;
import com.example.movie.repository.MovieRepository;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<MovieDto> getPlayMovie(String list) {
        List<String> midList = Arrays.asList(list.split(","));

        List<Long> mid=Parse.convertStringListToLongList(midList);

        List<Movie> movie = movieRepository.findByMidIn(mid);
        List<Movie> notMovie = movieRepository.findByMidNotIn(mid);
        List<MovieDto> movieDtos = movie.stream().map(
                (v)->
                    new MovieDto(v.getMid(), v.getMtitle(), v.getMrating(), true)
        ).collect(Collectors.toList());


        List<MovieDto> notMovieDtos = notMovie.stream().map(
                (v)->
                        new MovieDto(v.getMid(), v.getMtitle(), v.getMrating(), false)
        ).collect(Collectors.toList());


        List<MovieDto> result = new ArrayList<>();

        result.addAll(movieDtos);
        result.addAll(notMovieDtos);
        return result;
    }

    @Override
    public List<Movie>  getComingMovie(Map<String, String> requestMap) {
        return null;
    }
}
