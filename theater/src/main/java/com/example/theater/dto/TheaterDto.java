package com.example.theater.dto;

import jakarta.persistence.Column;
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
    private boolean reserve;
}
