package com.review.monitoring.MonitoringSystem.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReviewRepository {
    public Long writeReview(Review review);
}
