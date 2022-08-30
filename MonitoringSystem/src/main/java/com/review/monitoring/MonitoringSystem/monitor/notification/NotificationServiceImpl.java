package com.review.monitoring.MonitoringSystem.monitor.notification;

import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.review.NotificationRepository;
import com.review.monitoring.MonitoringSystem.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    private String generateEmitterId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }
    @Override
    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String emitterId = generateEmitterId(memberId);

        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = generateEmitterId(memberId);
        sendToClient(emitter,eventId,emitterId,"EventStream Created. [memberId=" + memberId + "]");

        if(!lastEventId.isEmpty()) {
            Map<String,Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter,entry.getKey(),emitterId, entry.getValue()));
        }

        return emitter;
    }

    @Override
    public void send(Member receiver, String content) {
        Notification notification = notificationRepository.save(createNotification(receiver,content));
        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach((key, emitter) -> {
            emitterRepository.saveEventCache(key,notification);
            sendToClient(emitter,eventId,key,NotificationResponseDto.create(notification));
        });
    }

    private Notification createNotification(Member receiver, String content) {
        return Notification.builder()
                .receiver(receiver)
                .content(content)
                .url("_blank")
                .isRead(false)
                .build();
    }


    private void sendToClient(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            throw new RuntimeException("연결 오류!");
        }
    }
}
