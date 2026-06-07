package com.app.springapp.service;

import com.app.springapp.domain.dto.ReviewDTO;
import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;
import com.app.springapp.repository.ReviewDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void writeReview(Long userId, ReviewRequestDTO request) {
        ReviewDTO reviewDTO = new ReviewDTO(
                userId,
                request.getReviewRating(),
                String.join(",", request.getReviewTags()),
                request.getReviewContent()
        );
        reviewDAO.insertReview(reviewDTO);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewDAO.findAllReviews();
    }

    // 오늘의 후기 (날짜 기반 셔플 + 캐시)
    @Cacheable(value = "todayReviews", key = "T(java.time.LocalDate).now().toString()", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<ReviewResponseDTO> getTodayReviews() {
        long seed = LocalDate.now().toEpochDay();
        List<ReviewResponseDTO> all = reviewDAO.findAllReviews(); // 평점+최신순 정렬은 쿼리에서

        // 상위 20개 중에서 날짜 기반 셔플로 7개 추출
        List<ReviewResponseDTO> top = all.stream().limit(20).toList();
        List<ReviewResponseDTO> mutable = new java.util.ArrayList<>(top);
        Collections.shuffle(mutable, new Random(seed));

        return mutable.stream().limit(7).toList();
    }

    // 자정마다 캐시 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledClearCache() {
        redisTemplate.delete("todayReviews::" + LocalDate.now().toString());
        log.info("오늘의 후기 캐시 초기화");
    }

    // 수동 캐시 초기화
    @CacheEvict(value = "todayReviews", allEntries = true)
    @Override
    public void clearTodayReviewsCache() {
        log.info("오늘의 후기 캐시 수동 초기화");
    }
}