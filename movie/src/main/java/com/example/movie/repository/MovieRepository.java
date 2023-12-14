package com.example.movie.repository;

import com.example.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findAll();
    Movie findByMid(Long id);

    List<Movie> findByMidIn(List<Long> mid);
    List<Movie> findByMidNotIn(List<Long> mid);

}

