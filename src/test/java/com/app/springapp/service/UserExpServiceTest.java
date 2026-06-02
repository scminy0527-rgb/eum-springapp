package com.app.springapp.service;

import com.app.springapp.domain.dto.response.MyPageProfileResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class UserExpServiceTest {

    @Autowired
    private UserExpService userExpService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Long userId = 1L;
    private Long originalUserExp;

    @BeforeEach
    void setUp() {
        //    테스트 전 1번 유저의 기존 경험치를 저장
        originalUserExp = jdbcTemplate.queryForObject(
                "SELECT USER_EXP FROM TBL_USER WHERE ID = ?",
                Long.class,
                userId
        );

        //    테스트 이력 중복을 막기 위해 경험치 이력만 정리
        jdbcTemplate.update(
                "DELETE FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ?",
                userId
        );

        //    테스트 기준 경험치를 0으로 초기화
        jdbcTemplate.update(
                "UPDATE TBL_USER SET USER_EXP = 0 WHERE ID = ?",
                userId
        );
    }

    @AfterEach
    void tearDown() {
        //    테스트 중 생성된 경험치 이력 삭제
        jdbcTemplate.update(
                "DELETE FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ?",
                userId
        );

        //    테스트 전 경험치로 복구
        jdbcTemplate.update(
                "UPDATE TBL_USER SET USER_EXP = ? WHERE ID = ?",
                originalUserExp,
                userId
        );
    }

    @Test
    void addPostExpTest() {
        //    게시글 작성 경험치 지급
        userExpService.addPostExp(userId, 1001L);

        Long userExp = jdbcTemplate.queryForObject(
                "SELECT USER_EXP FROM TBL_USER WHERE ID = ?",
                Long.class,
                userId
        );

        Integer historyCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ? AND USER_EXP_HISTORY_TYPE = 'POST'",
                Integer.class,
                userId
        );

        log.info("게시글 작성 후 경험치: {}", userExp);
        log.info("게시글 경험치 이력 개수: {}", historyCount);

        assertThat(userExp).isEqualTo(5L);
        assertThat(historyCount).isEqualTo(1);
    }

    @Test
    void addCommunityExpDailyLimitTest() {
        //    게시글/댓글 경험치는 하루 합산 3회까지만 지급
        userExpService.addPostExp(userId, 1001L);
        userExpService.addCommentExp(userId, 2001L);
        userExpService.addPostExp(userId, 1002L);
        userExpService.addCommentExp(userId, 2002L);

        Long userExp = jdbcTemplate.queryForObject(
                "SELECT USER_EXP FROM TBL_USER WHERE ID = ?",
                Long.class,
                userId
        );

        Integer historyCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ? AND USER_EXP_HISTORY_TYPE IN ('POST', 'COMMENT')",
                Integer.class,
                userId
        );

        log.info("게시글/댓글 제한 테스트 경험치: {}", userExp);
        log.info("게시글/댓글 경험치 이력 개수: {}", historyCount);

        assertThat(userExp).isEqualTo(15L);
        assertThat(historyCount).isEqualTo(3);
    }

    @Test
    void addStudyExpDuplicateTest() {
        //    같은 학습은 여러 번 완료해도 경험치가 한 번만 지급
        userExpService.addStudyExp(userId, 1L);
        userExpService.addStudyExp(userId, 1L);

        Long userExp = jdbcTemplate.queryForObject(
                "SELECT USER_EXP FROM TBL_USER WHERE ID = ?",
                Long.class,
                userId
        );

        Integer historyCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ? AND USER_EXP_HISTORY_TYPE = 'STUDY'",
                Integer.class,
                userId
        );

        log.info("같은 학습 중복 테스트 경험치: {}", userExp);
        log.info("학습 경험치 이력 개수: {}", historyCount);

        assertThat(userExp).isEqualTo(20L);
        assertThat(historyCount).isEqualTo(1);
    }

    @Test
    void addStudyExpNewStudyTest() {
        //    서로 다른 학습은 각각 경험치 지급
        userExpService.addStudyExp(userId, 1L);
        userExpService.addStudyExp(userId, 2L);

        Long userExp = jdbcTemplate.queryForObject(
                "SELECT USER_EXP FROM TBL_USER WHERE ID = ?",
                Long.class,
                userId
        );

        Integer historyCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ? AND USER_EXP_HISTORY_TYPE = 'STUDY'",
                Integer.class,
                userId
        );

        log.info("새로운 학습 테스트 경험치: {}", userExp);
        log.info("학습 경험치 이력 개수: {}", historyCount);

        assertThat(userExp).isEqualTo(40L);
        assertThat(historyCount).isEqualTo(2);
    }

    @Test
    void addAttendanceExpDuplicateTest() {
        //    출석 레벨 경험치는 하루 한 번만 40 지급
        userExpService.addAttendanceExp(userId);
        userExpService.addAttendanceExp(userId);

        Long userExp = jdbcTemplate.queryForObject(
                "SELECT USER_EXP FROM TBL_USER WHERE ID = ?",
                Long.class,
                userId
        );

        Integer historyCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM TBL_USER_EXP_HISTORY WHERE USER_ID = ? AND USER_EXP_HISTORY_TYPE = 'ATTENDANCE'",
                Integer.class,
                userId
        );

        log.info("출석 레벨 경험치 테스트 경험치: {}", userExp);
        log.info("출석 레벨 경험치 이력 개수: {}", historyCount);

        assertThat(userExp).isEqualTo(40L);
        assertThat(historyCount).isEqualTo(1);
    }

    @Test
    void setLevelInfoTest() {
        //    총 경험치 120 기준 레벨 정보 계산
        MyPageProfileResponseDTO profile = new MyPageProfileResponseDTO();
        profile.setUserExp(120L);

        userExpService.setLevelInfo(profile);

        log.info("계산된 레벨: Lv.{} {}", profile.getUserLevel(), profile.getUserLevelName());
        log.info("현재 레벨 경험치: {} / {}", profile.getCurrentLevelExp(), profile.getNextLevelExp());

        assertThat(profile.getUserLevel()).isEqualTo(2);
        assertThat(profile.getUserLevelName()).isEqualTo("입문자");
        assertThat(profile.getCurrentLevelExp()).isEqualTo(20L);
        assertThat(profile.getNextLevelExp()).isEqualTo(120L);
        assertThat(profile.getRemainingExp()).isEqualTo(100L);
        assertThat(profile.getExpPercent()).isEqualTo(16);
    }

    @Test
    void setMaxLevelInfoTest() {
        //    Lv.100 이상 경험치는 만렙으로 고정
        MyPageProfileResponseDTO profile = new MyPageProfileResponseDTO();
        profile.setUserExp(120000L);

        userExpService.setLevelInfo(profile);

        log.info("만렙 계산 결과: Lv.{} {}", profile.getUserLevel(), profile.getUserLevelName());
        log.info("만렙 경험치 표시: {} / {}", profile.getCurrentLevelExp(), profile.getNextLevelExp());

        assertThat(profile.getUserLevel()).isEqualTo(100);
        assertThat(profile.getUserLevelName()).isEqualTo("이음");
        assertThat(profile.getCurrentLevelExp()).isEqualTo(106920L);
        assertThat(profile.getNextLevelExp()).isEqualTo(106920L);
        assertThat(profile.getRemainingExp()).isEqualTo(0L);
        assertThat(profile.getExpPercent()).isEqualTo(100);
    }
}
