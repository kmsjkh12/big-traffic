package com.example.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table(name="movie")
@Entity
@Getter
@NoArgsConstructor

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    @Column(nullable = false,length = 30)
    private String mtitle;

    @Column(nullable = false, length = 30)
    private String mdir;

    @Column(nullable = false, length = 30)
    private String mgenre;

    @Column(nullable = false)
    private int mtime;

    @Column(nullable = false, length = 30)
    private Date mdate;

    @Column(nullable = false, length = 30)
    private String mrating;

    @Column(nullable = false, length = 30)
    private String mstory;

    @Column(nullable = false, length = 50)
    private String mimagepath;

}
