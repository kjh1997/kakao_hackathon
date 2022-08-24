package com.review.monitoring.MonitoringSystem.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.monitoring.MonitoringSystem.review.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Review send(String kafkaTopic, Review review) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(review);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        System.out.println("kafka send");
        System.out.println(review.getComment());
        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("Kafka Producer send data from the Order microservice : " + review);
        System.out.println("kafka send complete");
        return review;
    }

}