package com.review.monitoring.MonitoringSystem.elk;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Document(indexName = "test14")
@Builder
public class ElasticDocument {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name="index_name")
    private Long id;
    private int star;
    @Field(type = FieldType.Date)
    private LocalDateTime date;
    private String department;
    private int feedback;
    private int score;
    private String comment;
    private String mu_keyword;
}
