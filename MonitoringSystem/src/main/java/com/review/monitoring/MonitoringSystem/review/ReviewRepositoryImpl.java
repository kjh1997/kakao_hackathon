package com.review.monitoring.MonitoringSystem.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository{
    private final EntityManager em;

    public List<Review> getReviewData(int start, int end){
        return em.createQuery("select r from Review r where r.feedback !=-1 and r.department != 'UCF'", Review.class).setFirstResult(start)
                .setMaxResults(end).getResultList();
    }
//
    public Long writeReview(Review review) {
        return review.getId();
    }
}
