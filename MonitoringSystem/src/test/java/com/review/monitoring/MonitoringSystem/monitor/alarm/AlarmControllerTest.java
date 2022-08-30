package com.review.monitoring.MonitoringSystem.monitor.alarm;

import com.review.monitoring.MonitoringSystem.monitor.domain.Alarm;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.user.alarm.AlarmService;
import com.review.monitoring.MonitoringSystem.monitor.user.service.MemberService;
import com.review.monitoring.MonitoringSystem.monitor.vo.AlarmVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AlarmControllerTest {
    private MemberService memberService;
    private AlarmService alarmService;

    @Autowired
    public AlarmControllerTest(MemberService memberService, AlarmService alarmService) {
        this.memberService = memberService;
        this.alarmService = alarmService;
    }

    @Test
    @Transactional
    void addAlarmCondition() {
        Member member = memberService.getMember("test");
        AlarmVO alarmVO = new AlarmVO();
        alarmVO.setFeedback(1L);
        alarmVO.setScore(69L);
        alarmVO.setKeyword("배송");
        Alarm alarm = alarmService.addAlarm(alarmVO,member);

        Assertions.assertNotNull(alarm.getId());

        Assertions.assertFalse(member.getAlarms().isEmpty());
    }

    @Test
    @Transactional
    void deleteAlarmCondition() {


    }
}