package com.example.theater.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "movie_theater")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @Column(nullable = false, length = 30)
    private String tname;

    @Column(nullable = false, length = 50)
    private String taddr;

    @Column(nullable = false, length = 30)
    private String tarea;
}
