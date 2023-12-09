package com.example.movie_comment.service;

import com.example.movie_comment.entity.Comment;
import com.example.movie_comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }
    public List<Comment> getComment(Long mid){
        return commentRepository.findByMid(mid);
    }
}
