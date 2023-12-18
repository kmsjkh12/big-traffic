package com.example.movie_info.dto;

import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientDto {
    private String mid;
    private String tid;
    private Date miday;


    public boolean checkTheater(){
        if(this.tid == null){
            return true;
        }
        return false;
    }
    public ClientDto(String mid, String tid, String miday) throws ParseException {
        this.mid=null;
        this.tid=null;
        this.miday=null;

        if(mid != null){
            this.mid= mid;
        }

        if(tid != null){
            this.tid= tid;
        }
        if (miday != "") {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(miday);
            this.miday =date;
        }
    }
}
