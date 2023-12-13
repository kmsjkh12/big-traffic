package com.example.movie_info.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_information")
public class MovieInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long miid;

    @Column(nullable = false,length = 30)
    private Date miday;

    @Column(nullable = false,length = 30)
    private String mistarttime;

    @Column(nullable = false, length = 30)
    private String miendtime;

    @Column(nullable = false, length = 30)
    private Long mid; //주인 N

    @Column(nullable = false, length = 30)
    private Long cid; //주인 N
}
