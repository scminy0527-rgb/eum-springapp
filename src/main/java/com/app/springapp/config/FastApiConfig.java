package com.app.springapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "fastapi.learning-analysis")
public class FastApiConfig {
    // FastAPI 서버 주소
    private String baseUrl;

    // 학습 분석 리포트 경로
    private String reportPath;

    // 학습 분석 전체 요청 주소
    public String getReportUrl() {
        return baseUrl + reportPath;
    }
}