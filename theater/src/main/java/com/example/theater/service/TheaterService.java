package com.example.theater.service;

import com.example.theater.Parse.Parse;
import com.example.theater.dto.TheaterDto;
import com.example.theater.entity.Theater;
import com.example.theater.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    public List<TheaterDto> responseTheater (String list) {
        List<String> TheaterIds = Arrays.asList(list.split(","));

        List<Long> tid= Parse.convertStringListToLongList(TheaterIds);

        List<Theater> theaters = theaterRepository.findByTidIn(tid);
        List<Theater> notTheaters = theaterRepository.findByTidNotIn(tid);

        Set<Long> tidSet = theaters.stream().map(Theater::getTid).collect(Collectors.toSet());

        List<Theater> combinedList = notTheaters.stream()
                .filter(theater -> !tidSet.contains(theater.getTid()))
                .collect(Collectors.toList());

        List<TheaterDto> theaterDtos = theaters.stream().map(
                (v)->
                        new TheaterDto(v.getTid(),v.getTname(),v.getTaddr(),v.getTarea(), true)
        ).collect(Collectors.toList());


        List<TheaterDto> notTheaterDtos = combinedList.stream().map(
                (v)->
                        new TheaterDto(v.getTid(),v.getTname(),v.getTaddr(),v.getTarea(), false)
        ).collect(Collectors.toList());


        List<TheaterDto> result = new ArrayList<>();

        result.addAll(theaterDtos);
        result.addAll(notTheaterDtos);

        return result;

    }

}
