package com.example.movie_info.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientDto {
    private String mid;
    private String tid;
    private Date miday;

    public ClientDto(String mid, String tid, String miday){
        this.mid =mid;
        this.tid=tid;

        if (miday != "") {
            this.miday = Date.valueOf(miday);
        }
    }
}
