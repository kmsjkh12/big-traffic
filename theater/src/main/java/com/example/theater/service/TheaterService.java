package com.example.theater.service;

import com.example.theater.entity.Theater;
import com.example.theater.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TheaterService {
    private final static String url ="http://localhost:8500";

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private RestTemplate restTemplate;


    public TheaterService(TheaterRepository theaterRepository, RestTemplate restTemplate) {
        this.theaterRepository = theaterRepository;
        this.restTemplate = restTemplate;

    }

    public List<Theater> requestTheater() {
        return theaterRepository.findAll();
    }


    public ResponseEntity<?> getTheater() {
        List<Long> theaterId = new RestTemplate().getForObject(
                url + "/cinema-server/theater",
                List.class
        );

        List<Theater> theater = theaterRepository.findByTidIn(theaterId);

        return ResponseEntity.ok().body(theater);
    }


}
