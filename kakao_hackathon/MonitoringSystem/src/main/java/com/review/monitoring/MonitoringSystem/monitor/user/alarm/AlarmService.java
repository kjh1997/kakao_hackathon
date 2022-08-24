package com.review.monitoring.MonitoringSystem.monitor.user.alarm;

import com.review.monitoring.MonitoringSystem.monitor.domain.Alarm;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.vo.AlarmVO;
import com.review.monitoring.MonitoringSystem.review.Review;

import java.util.List;

public interface AlarmService {
    Alarm addAlarm(AlarmVO alarmVO, Member member);

    List<Member> getAlarmedMembers(Review review);

    void deleteAlarm(Long alarmId, Member member);
}
