package com.review.monitoring.MonitoringSystem.monitor.user.controller;

import lombok.Data;

@Data
public class MemberForm {
    private String id;

    private String password;
    private String email;
    private String department;

}
