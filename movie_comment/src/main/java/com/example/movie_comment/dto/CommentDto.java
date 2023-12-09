package com.example.movie_comment.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long umid;

    private Boolean umlike;

    private String umliketime;

    private Integer umscore;

    private String umcomment;

    private String umcommenttime;

    private Long mid;
    private Long uid;



}
