package com.example.cinema.service;

import com.example.cinema.entity.CinemaEntity;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CinemaService {

    private String url ="http://localhost:8500";
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired

    private RestTemplate restTemplate;


    public CinemaService(CinemaRepository cinemaRepository, RestTemplate restTemplate){
        this.cinemaRepository=cinemaRepository;
        this.restTemplate=restTemplate;

    }
    public List<CinemaEntity> getCinema() {
        return cinemaRepository.findAll();
    }


}
