package com.review.monitoring.MonitoringSystem.config;


import com.review.monitoring.MonitoringSystem.elk.TestElasticsearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@Slf4j
public class ELKConfig {
    @Value("${elasticsearch.host}")
    private String hostname; //localhost

    @Value("${elasticsearch.port}")
    private Integer port; // 9200

    @Bean
    public RestHighLevelClient client(){
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostname+":"+port)
                .withConnectTimeout(500000)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {

        return new ElasticsearchRestTemplate(client());
    }
}
