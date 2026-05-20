package com.app.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

// 외부 API 호출에 사용할 RestTemplate 설정
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // 외부 API 서버와 연결을 시도하는 시간
        factory.setConnectTimeout(5000);

        // 연결 후 응답을 기다리는 시간
        factory.setReadTimeout(30000);

        return new RestTemplate(factory);
    }
}
