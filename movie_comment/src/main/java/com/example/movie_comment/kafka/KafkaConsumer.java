package com.example.movie_comment.kafka;

import com.example.movie_comment.dto.CommentDto;
import com.example.movie_comment.entity.Comment;
import com.example.movie_comment.repository.CommentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private CommentRepository commentRepository;

    public KafkaConsumer(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @KafkaListener(topics = "comment", groupId = "group_1")
    public void createComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .umid(commentDto.getUmid()).umlike(commentDto.getUmlike()).umcomment(commentDto.getUmcomment())
                .umcommenttime(commentDto.getUmcommenttime()).uid(commentDto.getUid()).mid(commentDto.getMid())
                .umscore(commentDto.getUmscore()).umliketime(commentDto.getUmliketime()).build();

        commentRepository.save(comment);
    }

}