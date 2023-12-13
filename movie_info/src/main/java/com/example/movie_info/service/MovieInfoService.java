package com.example.movie_info.service;

import com.example.movie_info.dto.ApiResponse;
import com.example.movie_info.dto.ClientDto;
import com.example.movie_info.entity.MovieInfoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MovieInfoService {
     List<MovieInfoEntity> getMovieInfo();


     ApiResponse selectMovieInfo(ClientDto clientDto);


     List<?> getInfo(List<MovieInfoEntity> infos, String url, String type);
}
