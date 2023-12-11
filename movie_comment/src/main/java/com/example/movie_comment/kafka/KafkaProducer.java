package com.example.movie_comment.kafka;

import com.example.movie_comment.dto.CommentDto;
import com.example.movie_comment.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public Comment createSend(String topic, Comment comment){
        //kafka 메세지 전송
        kafkaTemplate.send(topic, comment);
        return comment;
    }
}
