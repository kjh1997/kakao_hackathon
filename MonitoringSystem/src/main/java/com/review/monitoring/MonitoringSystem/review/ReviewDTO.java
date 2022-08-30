package com.review.monitoring.MonitoringSystem.review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private String comment;
    private int star;
    private LocalDateTime localDateTime;
    public ReviewDTO() {
        this.localDateTime = LocalDateTime.now();
    }
}
