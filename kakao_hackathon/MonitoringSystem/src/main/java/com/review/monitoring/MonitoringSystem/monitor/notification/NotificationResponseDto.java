package com.review.monitoring.MonitoringSystem.monitor.notification;

import com.review.monitoring.MonitoringSystem.review.Review;
import lombok.Data;

@Data
public class NotificationResponseDto {
    private NotificationContent content;
    private RelatedUrl url;

    public static NotificationResponseDto create(Notification notification) {
        NotificationResponseDto dto = new NotificationResponseDto();
        dto.setContent(notification.getContent());
        dto.setUrl(notification.getUrl());
        return dto;
    }
}
