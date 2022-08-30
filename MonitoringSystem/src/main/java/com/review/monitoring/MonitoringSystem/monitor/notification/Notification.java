package com.review.monitoring.MonitoringSystem.monitor.notification;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EqualsAndHashCode(of = "id")
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Embedded
    private NotificationContent content;

    @Getter
    @Embedded
    private RelatedUrl url;
    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver;

    @Builder
    public Notification(Member receiver, String content, String url, Boolean isRead) {
        this.receiver = receiver;
        this.content = new NotificationContent(content);
        this.url = new RelatedUrl(url);
        this.isRead = isRead;
    }
}
