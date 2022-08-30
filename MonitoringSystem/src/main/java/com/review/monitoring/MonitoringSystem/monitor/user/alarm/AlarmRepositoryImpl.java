package com.review.monitoring.MonitoringSystem.monitor.user.alarm;

import com.review.monitoring.MonitoringSystem.monitor.domain.Alarm;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository{
    private final EntityManager em;

    @Override
    public Alarm insert(Alarm alarm) {
        em.persist(alarm);
        return alarm;
    }

    @Override
    public List<Member> selectMembersByAlarm(Review review) {
        return em.createQuery("SELECT m.member_id, m.department, m.email FROM member m join alarm a on m.member_id = a.member_id " +
                        "where a.feedback = :feedback and a.keyword = :keyword and a.score >= :score " +
                        "group by m.member_id", Member.class)
                .setParameter("feedback", review.getFeedback())
                .setParameter("keyword", review.getMu_keyword())
                .setParameter("score", review.getScore())
                .getResultList();
    }

    @Override
    public void delete(Long alarmId) {
        Alarm alarm = em.find(Alarm.class, alarmId);
        em.remove(alarm);
    }
}
