package com.app.springapp.service;

import com.app.springapp.domain.dto.request.MyPageEditRequestDTO;
import com.app.springapp.domain.dto.request.MyPageWithdrawRequestDTO;
import com.app.springapp.domain.dto.response.MyPageFollowResponseDTO;
import com.app.springapp.domain.dto.response.MyPagePostResponseDTO;
import com.app.springapp.domain.dto.response.MyPageStudyStatusResponseDTO;
import com.app.springapp.exception.MyPageException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import com.app.springapp.exception.MyPageException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class MyPageServiceTest {
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // 마이페이지 메인

    //    마이페이지 메인 조회 테스트
    @Test
    public void getMyPageMainTest() {
        Long userId = 1L;

        log.info(myPageService.getMyPageMain(userId).toString());
    }

    //    프로필 카드 조회 테스트
    @Test
    public void getProfileTest() {
        Long userId = 1L;

        log.info(myPageService.getProfile(userId).toString());
    }

    //    내 활동 개수 조회 테스트
    @Test
    public void getActivityTest() {
        Long userId = 1L;

        log.info(myPageService.getActivity(userId).toString());
    }

    //    내가 작성한 게시글 목록 조회 테스트
    @Test
    public void getMyPostListTest() {
        Long userId = 1L;

        myPageService.getMyPostList(userId)
                .stream()
                .forEach((post) -> log.info(post.toString()));
    }

    //    좋아요한 게시글 목록 조회 테스트
    @Test
    public void getBookmarkListTest() {
        Long userId = 1L;

        myPageService.getBookmarkList(userId)
                .stream()
                .forEach((post) -> log.info(post.toString()));
    }

    //    팔로잉 목록 조회 테스트
    @Test
    public void getFollowingListTest() {
        Long userId = 1L;

        myPageService.getFollowingList(userId)
                .stream()
                .forEach((follow) -> log.info(follow.toString()));
    }

    //    팔로워 목록 조회 테스트
    @Test
    public void getFollowerListTest() {
        Long userId = 1L;

        myPageService.getFollowerList(userId)
                .stream()
                .forEach((follow) -> log.info(follow.toString()));
    }

    //    출석 정보 조회 테스트
    @Test
    public void getAttendanceTest() {
        Long userId = 1L;

        log.info(myPageService.getAttendance(userId).toString());
    }

    //    학습 현황 조회 테스트
    @Test
    public void getStudyStatusListTest() {
        Long userId = 1L;

        myPageService.getStudyStatusList(userId)
                .stream()
                .forEach((studyStatus) -> log.info(studyStatus.toString()));
    }

    // 정보수정

    //    정보수정 화면 회원 정보 조회 테스트
    @Test
    public void getUserInfoTest() {
        Long userId = 1L;

        log.info(myPageService.getUserInfo(userId).toString());
    }

    //    소셜 로그인 회원 여부 조회 테스트
    @Test
    public void isSocialUserTest() {
        Long userId = 1L;

        log.info("소셜 로그인 회원 여부: {}", myPageService.isSocialUser(userId));
    }

    //    닉네임 중복 검사 테스트
    @Test
    public void isDuplicatedNicknameTest() {
        Long userId = 1L;
        String userNickname = "minjun_k";

        log.info("닉네임 중복 여부: {}", myPageService.isDuplicatedNickname(userNickname, userId));
    }

    //    기본 프로필 수정 테스트
    @Test
    public void updateUserBasicInfoTest() {
        Long userId = 1L;

        MyPageEditRequestDTO requestDTO = new MyPageEditRequestDTO();
        requestDTO.setUserName("김민준");
        requestDTO.setUserNickname("minjun_update");
        requestDTO.setUserIntro("정보수정 테스트입니다.");
        requestDTO.setUserJob("소프트웨어 개발자");
        requestDTO.setUserAddress("서울특별시 강남구");

        myPageService.updateUserBasicInfo(requestDTO, userId);

        log.info("기본 프로필 수정 성공");
    }

    //    계정 정보 수정 테스트
    @Test
    public void updateUserAccountInfoTest() {
        Long userId = 1L;

        MyPageEditRequestDTO requestDTO = new MyPageEditRequestDTO();
        requestDTO.setUserEmail("minjun.kim@email.com");
        requestDTO.setUserPhoneNum("010-1234-5678");

        myPageService.updateUserAccountInfo(requestDTO, userId);

        log.info("계정 정보 수정 성공");
    }

    //    프로필 사진 기본 이미지 변경 테스트
    @Test
    public void deleteUserProfileTest() {
        Long userId = 1L;

        myPageService.deleteUserProfile(userId);

        log.info("프로필 사진 기본 이미지 변경 성공");
    }

    //    프로필 사진 수정 테스트
//    @Test
//    public void updateUserProfileTest() {
//        Long userId = 1L;
//
//        MockMultipartFile uploadFile = new MockMultipartFile(
//                "uploadFile",
//                "profile-test.png",
//                "image/png",
//                "profile image test".getBytes()
//        );
//
//        myPageService.updateUserProfile(uploadFile, userId);
//
//        log.info("프로필 사진 수정 성공");
//    }

    //    비밀번호 변경 테스트
//    @Test
//    public void updateUserPasswordTest() {
//        Long userId = 1L;
//        String currentPassword = "1234";
//        String newPassword = "12345";
//
//        myPageService.updateUserPassword(currentPassword, newPassword, userId);
//
//        log.info("비밀번호 변경 성공");
//    }

    // 회원탈퇴

    //    일반 회원탈퇴 테스트용 회원 생성
    @Autowired
    private RedisTemplate redisTemplate;

    // 회원탈퇴

    //    일반 회원탈퇴 테스트용 회원 생성
    private Long createWithdrawLocalTestUser() {
        Long userId = jdbcTemplate.queryForObject("SELECT SEQ_USER.NEXTVAL FROM DUAL", Long.class);
        String suffix = String.valueOf(userId);

        jdbcTemplate.update(
                "INSERT INTO TBL_USER (ID, USER_NAME, USER_NICKNAME, USER_EMAIL, USER_PHONE_NUM, USER_PASSWORD, USER_EXP, USER_PROFILE, USER_ROLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                userId,
                "탈퇴테스트회원",
                "withdraw_local_" + suffix,
                "withdraw_local_" + suffix + "@email.com",
                "010-9000-" + String.format("%04d", userId % 10000),
                "test123!@#",
                0,
                "default.jpg",
                "USER"
        );

        jdbcTemplate.update(
                "INSERT INTO TBL_SOCIAL_USER (ID, SOCIAL_USER_PROVIDER_ID, SOCIAL_USER_PROVIDER, USER_ID) VALUES (SEQ_SOCIAL_USER.NEXTVAL, ?, ?, ?)",
                "withdraw_local_" + suffix,
                "local",
                userId
        );

        return userId;
    }

    //    소셜 회원탈퇴 테스트용 회원 생성
    private Long createWithdrawSocialTestUser() {
        Long userId = jdbcTemplate.queryForObject("SELECT SEQ_USER.NEXTVAL FROM DUAL", Long.class);
        String suffix = String.valueOf(userId);

        jdbcTemplate.update(
                "INSERT INTO TBL_USER (ID, USER_NAME, USER_NICKNAME, USER_EMAIL, USER_PHONE_NUM, USER_PASSWORD, USER_EXP, USER_PROFILE, USER_ROLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                userId,
                "소셜탈퇴테스트회원",
                "withdraw_social_" + suffix,
                "withdraw_social_" + suffix + "@email.com",
                "010-9100-" + String.format("%04d", userId % 10000),
                null,
                0,
                "default.jpg",
                "USER"
        );

        jdbcTemplate.update(
                "INSERT INTO TBL_SOCIAL_USER (ID, SOCIAL_USER_PROVIDER_ID, SOCIAL_USER_PROVIDER, USER_ID) VALUES (SEQ_SOCIAL_USER.NEXTVAL, ?, ?, ?)",
                "google_withdraw_" + suffix,
                "google",
                userId
        );

        return userId;
    }

    //    일반 회원탈퇴 테스트
    @Test
    public void withdrawLocalUserTest() {
        Long userId = createWithdrawLocalTestUser();

        MyPageWithdrawRequestDTO requestDTO = new MyPageWithdrawRequestDTO();
        requestDTO.setWithdrawReason("자주 사용하지 않아요");
        requestDTO.setWithdrawDetail(null);
        requestDTO.setUserPassword("test123!@#");
        requestDTO.setWithdrawAgree(true);

        myPageService.withdrawUser(requestDTO, userId);

        Long userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TBL_USER WHERE ID = ?", Long.class, userId);
        Long withdrawCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TBL_USER_WITHDRAW WHERE USER_ID = ?", Long.class, userId);

        log.info("일반 회원 삭제 여부: {}", userCount == 0);
        log.info("일반 회원 탈퇴 사유 저장 여부: {}", withdrawCount > 0);
    }

    //    소셜 회원탈퇴 테스트
    @Test
    public void withdrawSocialUserTest() {
        Long userId = createWithdrawSocialTestUser();

        String userEmail = jdbcTemplate.queryForObject(
                "SELECT USER_EMAIL FROM TBL_USER WHERE ID = ?",
                String.class,
                userId
        );

        String emailCode = "123456";
        String redisKey = "email:" + userEmail + ":" + emailCode;

        redisTemplate.opsForValue().set(redisKey, emailCode, 3, TimeUnit.MINUTES);

        MyPageWithdrawRequestDTO requestDTO = new MyPageWithdrawRequestDTO();
        requestDTO.setWithdrawReason("다른 계정을 사용할게요");
        requestDTO.setWithdrawDetail(null);
        requestDTO.setEmailCode(emailCode);
        requestDTO.setWithdrawAgree(true);

        myPageService.withdrawUser(requestDTO, userId);

        Long userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TBL_USER WHERE ID = ?", Long.class, userId);
        Long withdrawCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TBL_USER_WITHDRAW WHERE USER_ID = ?", Long.class, userId);

        log.info("소셜 회원 삭제 여부: {}", userCount == 0);
        log.info("소셜 회원 탈퇴 사유 저장 여부: {}", withdrawCount > 0);
    }

    // 정보수정 테스트용 일반 회원 생성
    private Long createMyPageEditTestUser() {
        Long userId = jdbcTemplate.queryForObject("SELECT SEQ_USER.NEXTVAL FROM DUAL", Long.class);
        String suffix = String.valueOf(userId);

        jdbcTemplate.update(
                "INSERT INTO TBL_USER " +
                        "(ID, USER_NAME, USER_NICKNAME, USER_EMAIL, USER_PHONE_NUM, USER_PASSWORD, USER_EXP, USER_PROFILE, USER_ROLE, USER_EMAIL_CHANGED_AT) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                userId,
                "수정전이름",
                "edit_test_" + suffix,
                "edit_test_" + suffix + "@email.com",
                "01012345678",
                "test1234",
                0,
                "default.jpg",
                "USER",
                null
        );

        return userId;
    }

    // 기본 프로필 수정 시 이름까지 변경되는지 테스트
    @Test
    public void updateUserBasicInfoNameTest() {
        Long userId = createMyPageEditTestUser();

        MyPageEditRequestDTO requestDTO = new MyPageEditRequestDTO();
        requestDTO.setUserName("수정후이름");
        requestDTO.setUserNickname("edit_name_update_" + userId);
        requestDTO.setUserIntro("이름 변경 테스트입니다.");
        requestDTO.setUserJob("직장인");
        requestDTO.setUserAddress("수도권");

        myPageService.updateUserBasicInfo(requestDTO, userId);

        String changedName = jdbcTemplate.queryForObject(
                "SELECT USER_NAME FROM TBL_USER WHERE ID = ?",
                String.class,
                userId
        );

        assertEquals("수정후이름", changedName);

        jdbcTemplate.update("DELETE FROM TBL_USER WHERE ID = ?", userId);
    }

    // 이메일 변경 성공 테스트
    @Test
    public void updateUserAccountInfoEmailSuccessTest() {
        Long userId = createMyPageEditTestUser();

        String newEmail = "edit_email_success_" + userId + "@email.com";
        String emailCode = "123456";
        String redisKey = "email:" + newEmail + ":" + emailCode;

        redisTemplate.opsForValue().set(redisKey, emailCode, 3, TimeUnit.MINUTES);

        MyPageEditRequestDTO requestDTO = new MyPageEditRequestDTO();
        requestDTO.setUserEmail(newEmail);
        requestDTO.setUserPhoneNum("01012345678");
        requestDTO.setEmailCode(emailCode);

        myPageService.updateUserAccountInfo(requestDTO, userId);

        String changedEmail = jdbcTemplate.queryForObject(
                "SELECT USER_EMAIL FROM TBL_USER WHERE ID = ?",
                String.class,
                userId
        );

        Object changedAt = jdbcTemplate.queryForObject(
                "SELECT USER_EMAIL_CHANGED_AT FROM TBL_USER WHERE ID = ?",
                Object.class,
                userId
        );

        assertEquals(newEmail, changedEmail);
        assertNotNull(changedAt);

        redisTemplate.delete(redisKey);
        jdbcTemplate.update("DELETE FROM TBL_USER WHERE ID = ?", userId);
    }

    // 이메일 변경 1개월 제한 테스트
    @Test
    public void updateUserAccountInfoEmailLimitTest() {
        Long userId = createMyPageEditTestUser();

        jdbcTemplate.update(
                "UPDATE TBL_USER SET USER_EMAIL_CHANGED_AT = SYSDATE WHERE ID = ?",
                userId
        );

        MyPageEditRequestDTO requestDTO = new MyPageEditRequestDTO();
        requestDTO.setUserEmail("edit_email_limit_" + userId + "@email.com");
        requestDTO.setUserPhoneNum("01012345678");
        requestDTO.setEmailCode("123456");

        MyPageException exception = assertThrows(
                MyPageException.class,
                () -> myPageService.updateUserAccountInfo(requestDTO, userId)
        );

        assertEquals("이메일은 1개월에 1번만 변경할 수 있습니다.", exception.getMessage());

        jdbcTemplate.update("DELETE FROM TBL_USER WHERE ID = ?", userId);
    }
}