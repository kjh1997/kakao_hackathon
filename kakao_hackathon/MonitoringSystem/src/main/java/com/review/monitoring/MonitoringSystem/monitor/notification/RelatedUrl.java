package com.review.monitoring.MonitoringSystem.monitor.notification;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class RelatedUrl {
    private String url;

    public RelatedUrl(String url) {
        this.url = url;
    }
}
