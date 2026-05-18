package com.app.springapp.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtTokenDTO {
    private String accessToken;
    private String refreshToken;
    private String role;
}
