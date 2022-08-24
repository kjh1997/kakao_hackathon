package com.review.monitoring.MonitoringSystem.monitor.user.service;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class UserAccount extends User {
    private Member member;

    public UserAccount(Member member) {
        super(member.getNickname(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
        this.member = member;
    }
}
