package com.review.monitoring.MonitoringSystem.elk;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(indexName = "testIndex")
@Getter
@Setter
public class testDocument {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "test_id")
    private Long id;
    private String name;
}
