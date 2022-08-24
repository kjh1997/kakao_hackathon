package com.review.monitoring.MonitoringSystem.review;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;

public interface MemberRepository {
    void insert(Member member);
    int existById(String id);
    Member selectOne(String memberId);
    Member update(Member member);
    void delete(String userId);


}
