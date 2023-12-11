package com.example.movie_comment.entity;

import com.example.movie_comment.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;;

@Table(name = "movie_comment")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long umid;
    private Boolean umlike;
    private String umliketime;
    private Integer umscore;
    private String umcomment;
    private String umcommenttime;

    private Long mid;
    private Long uid;

    public static Comment covertCommentDto(CommentDto commentDto){
        return new Comment(
                commentDto.getUmid(), commentDto.getUmlike(), commentDto.getUmliketime(),commentDto.getUmscore(),
                commentDto.getUmcomment(), commentDto.getUmcommenttime(), commentDto.getMid(), commentDto.getUid()
        );
    }
}
