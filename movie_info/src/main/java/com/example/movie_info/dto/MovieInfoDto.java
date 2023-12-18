package com.example.movie_info.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
public class MovieInfoDto {

    private Date miday;
    private boolean reserve;

    public MovieInfoDto(Date miday, boolean reserve){
        this.miday=miday;
        this.reserve = reserve;
    }
}
