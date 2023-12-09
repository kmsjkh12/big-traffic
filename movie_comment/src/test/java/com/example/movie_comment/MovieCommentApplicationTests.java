package com.example.movie_comment;

import com.example.movie_comment.kafka.KafkaConsumer;
import com.example.movie_comment.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieCommentApplicationTests {

	@Autowired
	private KafkaProducer kafkaProducer;

	@Test
	void test(){
		kafkaProducer.create();
	}
}
