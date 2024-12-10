package com.example.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebClientConfig implements WebMvcConfigurer {


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/java_service") // 적용할 path 패턴을 입력
//                .allowedOrigins("http://localhost:8088") // 허가할 출처들을 기입
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*") // 허용할 헤더 (필요에 따라 수정 가능)
//                .allowCredentials(true); // 인증 관련 쿠키 등을 포함할 경우 true 설정
//    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(-1))
                        .build())
                .baseUrl("http://localhost:8088")
                .build();
    }



}
