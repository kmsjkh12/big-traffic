package com.example.movie_comment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "movie_comment")
@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long umid;
    @Column
    private Boolean umlike;
    @Column
    private String umliketime;
    @Column
    private Integer umscore;
    @Column
    private String umcomment;
    @Column
    private String umcommenttime;
    @Column
    private Long mid;
    @Column
    private Long uid;

    @Builder
    public Comment(Long umid,
                   Boolean umlike,
                   String umliketime,
                   Integer umscore,
                   String umcomment,
                   String umcommenttime,
                   Long mid,
                   Long uid) {
        this.umid=umid;
        this.umlike=umlike;
        this.umliketime=umliketime;
        this.umscore=umscore;
        this.umcomment=umcomment;
        this.umcommenttime=umcommenttime;
        this.mid=mid;
        this.uid=uid;
    }
}
