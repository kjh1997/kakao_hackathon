package com.review.monitoring.MonitoringSystem.review;

import com.review.monitoring.MonitoringSystem.monitor.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
