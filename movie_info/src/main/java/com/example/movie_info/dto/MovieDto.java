package com.example.movie_info.dto;

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
    private String reserve;

}
