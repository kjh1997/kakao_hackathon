package com.review.monitoring.MonitoringSystem.monitor.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public enum Keyword {
    // cs 부서 키워드
    전화, 주문, 교환, 고객, 품절, 사람, 리뷰, 연락, 친절, 서비스, 판매자,
    // delivery 부서 키워드
    배송, 포장, 택배, 발송, 도착, 배달, 주일, 평일, 주말,
    // quality 부서 키워드
    사용, 제품, 가격, 저렴, 깔끔, 디자인, 상태, 추천, 색상, 재질, 고급, 퀄리티, 성능;


}
