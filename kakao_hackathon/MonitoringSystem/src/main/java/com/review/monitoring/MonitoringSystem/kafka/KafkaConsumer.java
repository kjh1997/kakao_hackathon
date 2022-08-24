package com.review.monitoring.MonitoringSystem.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.review.monitoring.MonitoringSystem.elk.ElasticDocument;
import com.review.monitoring.MonitoringSystem.elk.TestElasticsearchRepository;
import com.review.monitoring.MonitoringSystem.monitor.domain.Department;
import com.review.monitoring.MonitoringSystem.monitor.domain.Member;
import com.review.monitoring.MonitoringSystem.monitor.notification.NotificationService;
import com.review.monitoring.MonitoringSystem.monitor.user.alarm.AlarmService;
import com.review.monitoring.MonitoringSystem.review.Review;
import com.review.monitoring.MonitoringSystem.review.ReviewServiceImpl;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ReviewServiceImpl reviewService;

    private final AlarmService alarmService;

    private final NotificationService notificationService;
    private final TestElasticsearchRepository testElasticsearchRepository;
    @KafkaListener(topics = "complete")
    public void processMessage(String kafkaMessage) {
        log.info("Kafka Message ====> " + kafkaMessage);
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ElasticDocument docs= ElasticDocument.builder()
                .comment((String) map.get("comment"))
                .date(LocalDateTime.now())
                .feedback((int) map.get("feedback"))
                .score((int) map.get("score"))
                .department((String) map.get("department"))
                .star((int) map.get("star"))
                .mu_keyword((String) map.get("review_word"))
                .build();
        testElasticsearchRepository.save(docs);
        System.out.println((String) map.get("comment"));

//        Review review = mapReview(map);
//        sendNotification(review);

    }
    private Review mapReview(Map<Object,Object> map) {
        Review review = new Review();
        review.setId((Long)map.get("id"));
        review.setStar((int)map.get("star"));
        review.setDate(LocalDateTime.now());
        review.setDepartment(Department.valueOf((String)map.get("department")));
        review.setFeedback((int) map.get("feedback"));
        review.setScore((int) map.get("score"));
        review.setComment((String) map.get("comment"));
        review.setMu_keyword((String) map.get("mu_keyword"));
        return review;
    }

    private void sendNotification(Review review) {
        List<Member> members = alarmService.getAlarmedMembers(review);
        String content = "키워드 : " + review.getMu_keyword()
                +" 호감도 : " + (review.getFeedback() == 1? "긍정" :  "부정")
                + " 점수 : " + review.getScore();
        members.stream().forEach(member -> {
            notificationService.send(member,content);
            System.out.println("성공적으로 보내짐 서버에서는");
        });
    }
}