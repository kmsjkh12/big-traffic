package com.example.movie_info.dto;

import com.example.movie_info.entity.MovieInfoEntity;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceMovieDto {

    private Long miid;

    private Date miday;

    private String mistarttime;

    private String miendtime;

    private Long mid; //주인 N

    private Long cid; //주인 N

    public ChoiceMovieDto(MovieInfoEntity e){
        this.miid= e.getMiid();
        this.miday=e.getMiday();
        this.mistarttime=e.getMistarttime();
        this.miendtime=e.getMiendtime();
        this.mid=e.getMid();
        this.cid=e.getCid();

    }
}
