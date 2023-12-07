package com.example.AuthServer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Table(name = "auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auth {

    @Id
    @Column(nullable = false, length = 20)
    private String uid;

    @Column(nullable = false)
    private String upw;

    @Column(nullable = false, length = 20)
    private String unickname;

    @Column(nullable = false, length = 50)
    private String uemail;

    @Column(nullable = false, length = 20)
    private String utel;

    @Column(nullable = false, length = 50)
    private String uaddr;

    @Column(nullable = false, length = 50)
    private String uaddrsecond;

    @Column(nullable = false)
    private Date ubirth;

    @Column(nullable = false)
    private Date ujoindate;
}
