package com.review.monitoring.MonitoringSystem.scheduler;
import com.review.monitoring.MonitoringSystem.elk.ElasticDocument;
import com.review.monitoring.MonitoringSystem.elk.TestElasticsearchRepository;
import com.review.monitoring.MonitoringSystem.monitor.domain.Department;
import com.review.monitoring.MonitoringSystem.review.Review;
import com.review.monitoring.MonitoringSystem.review.ReviewRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TestScheduler {
//    @Value("${jwt.secret}") String secret;
    private final TestElasticsearchRepository testElasticsearchRepository;
    private final ReviewRepositoryImpl reviewRepository;
//    @Scheduled(fixedRate=3000000)
//    public void testScheduler(){
//        System.out.println("start : get data");
//
//        for (int i = 70000; i < 71000; i += 100) {
//            List<Review> reviewList = reviewRepository.getReviewData(i,100);
//            List<ElasticDocument> elasticDocuments = new ArrayList<>();
//            System.out.println("start : put data"+ reviewList.size());
//            System.out.println("start : " + i );
//            for(Review r: reviewList ){
//                ElasticDocument elasticDocument = ElasticDocument.builder()
//                        .comment(r.getComment())
//                        .date(r.getDate())
//                        .feedback(r.getFeedback())
//                        .score(r.getScore())
//                        .department(r.getDepartment().toString())
//                        .star(r.getStar())
//                        .mu_keyword(r.getMu_keyword())
//                        .build();
//                elasticDocuments.add(elasticDocument);
//            }
//            System.out.println("????" + elasticDocuments.size());
//            testElasticsearchRepository.saveAll(elasticDocuments);
//
//        }
//
//        }

}
