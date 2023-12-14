package com.example.cinema.service;

import com.example.cinema.entity.CinemaEntity;
import com.example.cinema.parse.Parse;
import com.example.cinema.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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


    public List<Long> responseCinema(String list) {
        List<String> cinemaIds = Arrays.asList(list.split(","));

        List<Long > cids = Parse.convertStringListToLongList(cinemaIds);

        return cinemaRepository.findByCidsIn(cids);
    }

    public List<Long> responseTid(String id){
        return cinemaRepository.findByTids(Long.valueOf(id));
    }
}
