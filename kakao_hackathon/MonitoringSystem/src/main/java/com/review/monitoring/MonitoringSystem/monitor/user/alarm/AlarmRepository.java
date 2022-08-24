package com.review.monitoring.MonitoringSystem.monitor.user.alarm;

import com.review.monitoring.MonitoringSystem.monitor.domain.Alarm;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.review.Review;

import java.util.List;

public interface AlarmRepository {
    Alarm insert(Alarm alarm);

    List<Member> selectMembersByAlarm(Review review);
    void delete(Long alarmId);
}
