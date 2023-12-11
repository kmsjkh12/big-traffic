package com.example.theater.service;

import com.example.theater.entity.Theater;
import com.example.theater.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;

    }

    public List<Theater> requestTheater() {
        return theaterRepository.findAll();
    }

}
