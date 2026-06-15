package com.app.springapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final RestTemplate restTemplate;
    private static final String FASTAPI_URL = "http://localhost:8000/sign/translate";

    @Override
    public Map<String, Object> translate(String text) {
        String url = FASTAPI_URL + "?text=" + text;
        return restTemplate.getForObject(url, Map.class);
    }
}