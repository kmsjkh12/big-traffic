package com.example.movie_comment.kafka;

import com.example.movie_comment.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public CommentDto createSend(String topic, CommentDto orderDto){
        //kafka 메세지 전송
        kafkaTemplate.send(topic, orderDto);
        System.out.println("createsend, kafkaproducer");
        return orderDto;
    }
    public void create(){
        kafkaTemplate.send("comment","say hello");
    }
}
