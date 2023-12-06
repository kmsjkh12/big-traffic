package com.example.AuthServer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nickname;
    @Column
    private String password;

}
