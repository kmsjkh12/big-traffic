package com.example.cinema.service;

import com.example.cinema.entity.CinemaEntity;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;


    public CinemaService(CinemaRepository cinemaRepository){
        this.cinemaRepository=cinemaRepository;

    }
    public List<CinemaEntity> getCinema() {
        return cinemaRepository.findAll();
    }
}
