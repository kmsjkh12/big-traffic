package com.example.movie_info.service;

import com.example.movie_info.dto.ApiResponse;
import com.example.movie_info.dto.ClientDto;
import com.example.movie_info.dto.MovieDto;
import com.example.movie_info.dto.TheaterDto;
import com.example.movie_info.entity.MovieInfoEntity;
import com.example.movie_info.repository.MovieInfoRepository;
import com.example.movie_info.util.Parse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieInfoServiceImpl implements MovieInfoService{
    @Autowired
    private MovieInfoRepository movieInfoRepository;

    private RestTemplate restTemplate;
    public MovieInfoServiceImpl(MovieInfoRepository movieInfoRepository, RestTemplate restTemplate){
        this.movieInfoRepository=movieInfoRepository;
        this.restTemplate=restTemplate;
    }
    @Override
    public List<MovieInfoEntity> getMovieInfo() {
        List<MovieInfoEntity> list = movieInfoRepository.getInfo();

        return list;
    }

    @Override
    public ApiResponse selectMovieInfo(ClientDto clientDto) {
        List<MovieInfoEntity> infos = movieInfoRepository.getInfo();

        List<Long> cid = movieInfoRepository.findDistinctCids();

        List<MovieInfoEntity> uniqueInfos = infos.stream()

                .collect(Collectors.toMap(
                        MovieInfoEntity::getMiday, // 중복 키 설정
                        movieInfoEntity -> movieInfoEntity,
                        (existing, replacement) -> existing // 중복 시 기존 값 유지
                ))

                .values()
                .stream()
                .sorted(Comparator.comparing(MovieInfoEntity::getMiday)) // miday를 기준으로 정렬
                .collect(Collectors.toList());

        if(clientDto.getTid() == null && clientDto.getMiday() == null && clientDto.getMid() ==null){
            List<MovieDto> movie= (List<MovieDto>) getInfo(infos,"/movie-server/playmovies", "movie");
            List<TheaterDto> theater= (List<TheaterDto>) getInfo(infos,"/theater-server/theater", "theater");

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMovies(movie);
            apiResponse.setTheaters(theater);
            apiResponse.setInfos(uniqueInfos);

            return apiResponse;
        }

        ResponseEntity<?> movie;
        ResponseEntity<?> theater;
        ResponseEntity<?> day;

        return null;
    }

    @Override
    public List<?> getInfo(List<MovieInfoEntity> infos, String url , String type ) {
        ResponseEntity<String> data = restTemplate.getForEntity(
                Parse.finalListUrl(url,Parse.extractUniqueMids(infos)),
                String.class);

        String stringDto = data.getBody();
        ObjectMapper objectMapper= new ObjectMapper();

        try{
            if(type == "movie") {
                List<MovieDto> movieDtos = objectMapper.readValue(stringDto, new TypeReference<List<MovieDto>>() {
                });
                return movieDtos;
            }
            if( type == "theater"){
                List<TheaterDto> theaterDtos = objectMapper.readValue(stringDto, new TypeReference<List<TheaterDto>>() {
                });
                return theaterDtos;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
