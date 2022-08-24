package com.review.monitoring.MonitoringSystem.elk;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface TestElasticsearchRepository extends ElasticsearchRepository<ElasticDocument,Long> {
}
