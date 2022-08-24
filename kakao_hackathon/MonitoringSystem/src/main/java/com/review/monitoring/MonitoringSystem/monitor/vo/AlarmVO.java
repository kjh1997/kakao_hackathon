package com.review.monitoring.MonitoringSystem.monitor.vo;

import lombok.Data;

@Data
public class AlarmVO {
    private Long id;

    private Long feedback;

    private Long score;

    private String keyword;
}
