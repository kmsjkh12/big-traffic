package com.example.movie_info.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheaterDto {
    private Long tid;

    private String tname;

    private String taddr;

    private String tarea;
}
