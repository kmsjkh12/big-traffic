package com.example.AuthServer.dto;

import com.example.AuthServer.entity.Auth;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    private String uid;
    private String upw;
    private String unickname;
    private String uemail;
    private String utel;
    private String uaddr;
    private String uaddrsecond;
    private Date ubirth;
    private Date ujoindate;

    public AuthDto(Auth auth) {
        this.uid=auth.getUid();
        this.upw=auth.getUpw();
        this.unickname=auth.getUnickname();
        this.uemail= auth.getUemail();
        this.utel= auth.getUtel();
        this.uaddr=auth.getUaddr();
        this.uaddrsecond=auth.getUaddrsecond();
        this.ubirth=auth.getUbirth();
        this.ujoindate=auth.getUjoindate();
    }
}
