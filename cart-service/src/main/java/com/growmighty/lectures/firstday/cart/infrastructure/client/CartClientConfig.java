package com.growmighty.lectures.firstday.cart.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class CartClientConfig {

    @Bean
    @Primary
    RestClient.Builder plainRestClientBuilder() {
        return RestClient.builder();
    }

    // [LB 빌더]
    // 요청 시점에 Eureka 명부에서 실제 인스턴스를 골라(라운드로빈) 주소를 치환한다.
    @Bean
    @LoadBalanced
    RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    RestClient productRestClient(
        @LoadBalanced RestClient.Builder builder,
        @Value("${cart.client.product-base-url:http://product-service}") String baseUrl) {
        return builder.clone().baseUrl(baseUrl).build();
    }
}
