package com.review.monitoring.MonitoringSystem.review;

import com.review.monitoring.MonitoringSystem.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional
    public void writeReview(Review review) {
        review.showData();
        kafkaProducer.send("etl", review);
        System.out.println("flush");

    }
}
