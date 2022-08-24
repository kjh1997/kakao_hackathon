package com.review.monitoring.MonitoringSystem.monitor.notification;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class NotificationContent {
    private String content;

    public NotificationContent(String content) {
        this.content = content;
    }

}
