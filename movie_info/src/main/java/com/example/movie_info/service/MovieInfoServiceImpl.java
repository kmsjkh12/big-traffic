package com.example.movie_info.service;

import com.example.movie_info.dto.*;
import com.example.movie_info.entity.MovieInfoEntity;
import com.example.movie_info.repository.MovieInfoRepository;
import com.example.movie_info.util.InfoDtoParse;
import com.example.movie_info.util.Parse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieInfoServiceImpl implements MovieInfoService{
    @Autowired
    private MovieInfoRepository movieInfoRepository;
    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;
    public MovieInfoServiceImpl(MovieInfoRepository movieInfoRepository, RestTemplate restTemplate,ObjectMapper objectMapper){
        this.movieInfoRepository=movieInfoRepository;
        this.restTemplate=restTemplate;
        this.objectMapper=objectMapper;
    }

    @Override
    public ApiResponse selectMovieInfo(ClientDto clientDto) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse();

        List<Long> cinemaIds = null;
        Long mid = null;

        if(clientDto.getMid().length() != 0){
            mid = Long.valueOf(clientDto.getMid());
        }

        if(clientDto.getTid().length() != 0){
            cinemaIds = requestCinema(clientDto.getTid());
        }

        List<Long> midList = movieInfoRepository.findMovieInfoMovie(mid, cinemaIds, clientDto.getMiday());
        List<Long> cidList =  movieInfoRepository.findMovieInfoCinema(mid, cinemaIds, clientDto.getMiday());
        List<Date> midayList =  movieInfoRepository.findMovieInfoMiday(mid, cinemaIds, clientDto.getMiday());
        List<Date> notMidayList =  movieInfoRepository.findMovieInfoMiday(null, null, null);
        List<ChoiceMovieDto> choice = null;
        List<MovieDto> movies = requestMovie(midList);
        List<TheaterDto> theaters = requestTheater(cidList);
        List<MovieInfoDto> infos = InfoDtoParse.mergeRemoveDuplicates(midayList,notMidayList);

        if(clientDto.getMid().length() != 0 && clientDto.getTid().length() != 0 && clientDto.getMiday() != null){

            List<MovieInfoEntity> movieInfo = movieInfoRepository.findMovieInfo(mid, cinemaIds, clientDto.getMiday());
            choice = movieInfo.stream().map(
                    (v)-> new ChoiceMovieDto(v)
            ).collect(Collectors.toList());

        }

        apiResponse.setMovies(movies);
        apiResponse.setTheaters(theaters);
        apiResponse.setInfos(infos);
        apiResponse.setChoice(choice);
        return apiResponse;
    }

    //영화 id를 검색하면 영화를 반환해줌
    public List<MovieDto> requestMovie(List<Long> movie) throws JsonProcessingException {
        String urlMovie = Parse.finalListUrl("/movie-server/playmovies", movie);
        ResponseEntity<String> requestMovie = restTemplate.getForEntity(urlMovie,String.class);
       return objectMapper.readValue(requestMovie.getBody(), new TypeReference<List<MovieDto>>() {});
    }


    //극장 반환
    public List<TheaterDto> requestTheater(List<Long> cinema) throws JsonProcessingException {

        //cinema로부터 tid를 추출함
        String requestTheaterIdsUrl = Parse.finalListUrl("/cinema-server/cinematotid", cinema);

        ResponseEntity<String> requestTheaterIds = restTemplate.getForEntity(requestTheaterIdsUrl,String.class);
        List<Long> theaterIds = objectMapper.readValue(requestTheaterIds.getBody(), new TypeReference<List<Long>>() {});

        String requestTheater = Parse.finalListUrl("/theater-server/gettheater", theaterIds);

        ResponseEntity<String> theater = restTemplate.getForEntity(requestTheater,String.class);
        return objectMapper.readValue(theater.getBody(), new TypeReference<List<TheaterDto>>() {});

    }

    public List<Long> requestCinema(String tid) throws JsonProcessingException {
        String requestCinemaIdsUrl = Parse.finalStringUrl("/cinema-server/tidtocinema", tid);

        ResponseEntity<String> requestCinemaIds = restTemplate.getForEntity(requestCinemaIdsUrl,String.class);
        List<Long> theaterIds = objectMapper.readValue(requestCinemaIds.getBody(), new TypeReference<List<Long>>() {});

        return theaterIds;

    }

}
