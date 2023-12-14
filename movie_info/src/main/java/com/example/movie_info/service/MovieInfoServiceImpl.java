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


import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
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
        // 상영 가능한 모든 영화를 불러온다.
        List<MovieInfoEntity> movieInfos = movieInfoRepository.getInfo();
        System.out.println("영화 아이디" + clientDto.getMid());
        System.out.println("극장 아이디" + clientDto.getTid());
        System.out.println("날짜" + clientDto.getMiday());

        ApiResponse apiResponse = new ApiResponse();

        if ((clientDto.getTid() == null || clientDto.getTid().equals("")) &&
                (clientDto.getMid() == null || clientDto.getMid().equals("")) &&
                clientDto.getMiday() == null) {
            // null 값에 대한 데이터들
            System.out.print("전체가 널값입니다");
            List<MovieDto> movies = requestMovie(movieInfoRepository.findDistinctMidayAndCinIn(null, null));
            List<TheaterDto> theaters = requestTheater(movieInfoRepository.findDistinctMidAndMiday(null, null));
            List<MovieInfoEntity> infos = movieInfoRepository.findReserverMiday();
            List<MovieInfoEntity> uniqueInfos = infos.stream()
                    .collect(Collectors.toMap(MovieInfoEntity::getMiday, Function.identity(), (existing, replacement) -> existing))
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(MovieInfoEntity::getMiday)) // miday로 오름차순 정렬
                    .collect(Collectors.toList());

            apiResponse.setMovies(movies);
            apiResponse.setTheaters(theaters);
            apiResponse.setInfos(uniqueInfos);
            return apiResponse;
        }

        if ((clientDto.getTid() == null || clientDto.getTid().equals("")) &&
                (clientDto.getMid() != null || !clientDto.getMid().equals("")) &&
                clientDto.getMiday() == null) {
            System.out.print("영화가 널값입니다");
            List<MovieDto> movies = requestMovie(movieInfoRepository.findDistinctMidayAndCinIn(null, null));
            List<TheaterDto> requestTheater =requestTheater(movieInfoRepository.findDistinctMidAndMiday(Long.valueOf(clientDto.getMid()), null));
            List<MovieInfoEntity> infos = movieInfoRepository.findMovieInfosMid(Long.valueOf(clientDto.getMid()), null);

            List<MovieInfoEntity> uniqueInfos = infos.stream()
                    .collect(Collectors.toMap(MovieInfoEntity::getMiday, Function.identity(), (existing, replacement) -> existing))
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(MovieInfoEntity::getMiday)) // miday로 오름차순 정렬
                    .collect(Collectors.toList());

            apiResponse.setMovies(movies);
            apiResponse.setTheaters(requestTheater);
            apiResponse.setInfos(uniqueInfos);

            return apiResponse;

        }

        if ((clientDto.getTid() != null || !clientDto.getTid().equals("")) &&
                (clientDto.getMid() == null || clientDto.getMid().equals("")) &&
                clientDto.getMiday() == null){
            System.out.print("극장이 널값입니다");

            List<Long> cinemaIds = requestCinema(clientDto.getTid());
            List<MovieDto> requestMovie= requestMovie(movieInfoRepository.findDistinctMidayAndCinIn(
                    cinemaIds,null));
            List<TheaterDto> requestTheater = requestTheater(cinemaIds);
            List<MovieInfoEntity> infos = movieInfoRepository.findMovieInfosMid(null, cinemaIds);

            List<MovieInfoEntity> uniqueInfos = infos.stream()
                    .collect(Collectors.toMap(MovieInfoEntity::getMiday, Function.identity(), (existing, replacement) -> existing))
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(MovieInfoEntity::getMiday)) // miday로 오름차순 정렬
                    .collect(Collectors.toList());

            apiResponse.setMovies(requestMovie);
            apiResponse.setTheaters(requestTheater);
            apiResponse.setInfos(uniqueInfos);

            return apiResponse;
        }


        if ((clientDto.getTid() == null || clientDto.getTid().equals("")) &&
                (clientDto.getMid() == null || clientDto.getMid().equals("")) &&
                clientDto.getMiday() != null) {
            System.out.print("날짜가 널값입니다");

            List<Long> mid = movieInfoRepository.findDistinctMidayAndCinIn(null,clientDto.getMiday());
            List<MovieDto> requestMovie = requestMovie(mid);

            List<TheaterDto> requestTheater = requestTheater(movieInfoRepository.findDistinctMidAndMiday(null, clientDto.getMiday()));
            List<MovieInfoEntity> infos = movieInfoRepository.findReserverMiday();
            List<MovieInfoEntity> uniqueInfos = infos.stream()
                    .collect(Collectors.toMap(MovieInfoEntity::getMiday, Function.identity(), (existing, replacement) -> existing))
                    .values()
                    .stream()
                    .sorted(Comparator.comparing(MovieInfoEntity::getMiday)) // miday로 오름차순 정렬
                    .collect(Collectors.toList());

            apiResponse.setMovies(requestMovie);
            apiResponse.setTheaters(requestTheater);
            apiResponse.setInfos(uniqueInfos);

            return apiResponse;
        }


        return null;
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
