package com.example.movie_comment.controller;

import com.example.movie_comment.dto.CommentDto;
import com.example.movie_comment.entity.Comment;
import com.example.movie_comment.kafka.KafkaProducer;
import com.example.movie_comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private KafkaProducer kafkaProducer;

    public CommentController(KafkaProducer kafkaProducer,CommentService commentService){
        this.kafkaProducer=kafkaProducer;
        this.commentService=commentService;
    }


    @PostMapping("/comment")
    public ResponseEntity< ? > createComment(@ModelAttribute CommentDto commentDto){
        Comment comment = Comment.covertCommentDto(commentDto);
        kafkaProducer.createSend("create", comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @GetMapping("/Moviedetail/{movieid}")
    public ResponseEntity<?> getComment(@PathVariable Long movieid){
        return ResponseEntity.ok().body(commentService.getComment(movieid));

    }
}
