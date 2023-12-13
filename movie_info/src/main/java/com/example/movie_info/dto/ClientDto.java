package com.example.movie_info.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long mid;
    private Long tid;
    private String miday;
}
