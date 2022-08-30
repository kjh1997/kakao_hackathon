package com.review.monitoring.MonitoringSystem.monitor.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member {
    @Id
    @Column(name = "member_id", nullable = false)
    @GeneratedValue
    private Long id;

    private String nickname;
    @Column(name ="password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    public Member() {}

    public Member(String Id, String password, String email, Department department) {
        this.nickname = Id;
        this.password = password;
        this.email = email;
        this.department = department;
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    public void deleteAlarm(Long alarmId) {
        alarms.removeIf(alarm -> alarm.getId().equals(alarmId));
    }
}
