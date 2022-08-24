package com.review.monitoring.MonitoringSystem.monitor.user.alarm;

import com.review.monitoring.MonitoringSystem.monitor.domain.Alarm;
import com.review.monitoring.MonitoringSystem.monitor.domain.Keyword;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.vo.AlarmVO;
import com.review.monitoring.MonitoringSystem.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;

    @Override
    @Transactional
    public Alarm addAlarm(AlarmVO alarmVO, Member member) {
        if(member.getAlarms().stream().anyMatch(alarm -> alarm.getFeedback().equals(alarmVO.getFeedback())
                && alarm.getScore().equals(alarmVO.getScore())
                && alarm.getKeyword().equals(Keyword.valueOf(alarmVO.getKeyword())))) {
            return null;
        }
        Alarm alarm = new Alarm(alarmVO.getFeedback(), alarmVO.getScore(),
                Keyword.valueOf(alarmVO.getKeyword()), member);
        member.addAlarm(alarm);
        return alarmRepository.insert(alarm);
    }

    @Override
    public List<Member> getAlarmedMembers(Review review) {
        return alarmRepository.selectMembersByAlarm(review);
    }
    @Override
    @Transactional
    public void deleteAlarm(Long alarmId, Member member) {
        alarmRepository.delete(alarmId);
        member.deleteAlarm(alarmId);

    }

}
