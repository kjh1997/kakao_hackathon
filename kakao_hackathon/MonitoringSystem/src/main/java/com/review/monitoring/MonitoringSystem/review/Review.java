package com.review.monitoring.MonitoringSystem.review;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.review.monitoring.MonitoringSystem.monitor.domain.Department;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int star;

    private LocalDateTime date;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Enumerated(EnumType.STRING)
    private Department department;

    private int feedback;

    private int score;

    private String comment;

    private String mu_keyword;

    private String correct;

    public void showData() {
        System.out.println(this.getComment() + " | " + this.date + " | " + this.star);
    }
}
