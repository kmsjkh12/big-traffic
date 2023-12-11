package com.example.cinema.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie_cinema")
public class CinemaEntity { // 소문자 수정본

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Column(nullable = false)
    private String cname;

    @Column(nullable = false)
    private String ctype;

    @Column(nullable = false)
    private Integer cseat;

    @Column(nullable = false)
    private Long tid;



}

