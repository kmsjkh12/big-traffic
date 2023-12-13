package com.example.movie.dto;

import com.example.movie.entity.Movie;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MovieDto {
    private Long mid;

    private String mtitle;
    private String mrating;

    private boolean reserve;

    public static MovieDto mapDto(Movie movie,boolean flag){
        return new MovieDto(movie.getMid(), movie.getMtitle(),movie.getMrating(),flag);
    }
}