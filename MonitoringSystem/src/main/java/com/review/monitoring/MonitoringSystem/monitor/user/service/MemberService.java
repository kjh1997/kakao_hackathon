package com.review.monitoring.MonitoringSystem.monitor.user.service;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.vo.MemberVO;

public interface MemberService {
    int checkDuplicateMember(String id);
    void register(Member member);

    Member getMember(String nickname);
    Member logIn(String id, String password);
    Member updateMemberInfo(MemberVO memberVO, Member member);

    void delete(String memberId);
}
