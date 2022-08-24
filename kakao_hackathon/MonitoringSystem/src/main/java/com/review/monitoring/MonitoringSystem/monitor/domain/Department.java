package com.review.monitoring.MonitoringSystem.monitor.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Department {
    delivery(Arrays.asList(Keyword.전화, Keyword.주문, Keyword.교환, Keyword.고객, Keyword.품절,
            Keyword.사람, Keyword.리뷰, Keyword.연락, Keyword.친절, Keyword.서비스, Keyword.판매자)),
    customerService(Arrays.asList(Keyword.배송, Keyword.포장, Keyword.택배,
            Keyword.발송, Keyword.도착, Keyword.배달, Keyword.주일, Keyword.평일, Keyword.주말)),
    quality(Arrays.asList(Keyword.사용, Keyword.제품,Keyword.가격, Keyword.저렴, Keyword.깔끔, Keyword.디자인,
            Keyword.상태, Keyword.추천, Keyword.색상, Keyword.재질, Keyword.고급, Keyword.퀄리티, Keyword.성능)),
    UCF(Collections.emptyList());

    @Getter
    private final List<Keyword> keywords;

    Department(List<Keyword> keywords) {
        this.keywords = keywords;
    }

}
