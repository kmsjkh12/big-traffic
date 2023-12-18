package com.example.movie_info.dto;

import com.example.movie_info.entity.MovieInfoEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private List<MovieDto> movies;
    private List<TheaterDto> theaters;
    private List<MovieInfoDto> infos;
    private List<ChoiceMovieDto> choice;
}
