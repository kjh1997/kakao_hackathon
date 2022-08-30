package com.review.monitoring.MonitoringSystem.monitor.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberVO {
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String department;

    public MemberVO() {

    }

    public MemberVO(String id, String email, String department) {
        this.nickname = nickname;
        this.email = email;
        this.department = department;
    }
}
