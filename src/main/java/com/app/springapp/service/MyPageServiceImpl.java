package com.app.springapp.service;

import com.app.springapp.domain.dto.request.MyPageEditRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.MyPageActivityResponseDTO;
import com.app.springapp.domain.dto.response.MyPageAttendanceResponseDTO;
import com.app.springapp.domain.dto.response.MyPageFollowResponseDTO;
import com.app.springapp.domain.dto.response.MyPageMainResponseDTO;
import com.app.springapp.domain.dto.response.MyPagePostResponseDTO;
import com.app.springapp.domain.dto.response.MyPageProfileResponseDTO;
import com.app.springapp.domain.dto.response.MyPageStudyStatusResponseDTO;
import com.app.springapp.exception.MyPageException;
import com.app.springapp.exception.PostException;
import com.app.springapp.repository.MyPageDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, PostException.class})
@Slf4j
public class MyPageServiceImpl implements MyPageService {
    private final MyPageDAO myPageDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final FileService fileService;

    // 마이페이지 메인

    //    마이페이지 메인 조회
    @Override
    public MyPageMainResponseDTO getMyPageMain(Long userId) {
        MyPageMainResponseDTO myPageMainResponseDTO = new MyPageMainResponseDTO();

        myPageMainResponseDTO.setProfile(getProfile(userId));
        myPageMainResponseDTO.setActivity(getActivity(userId));
        myPageMainResponseDTO.setMyPostList(getMyPostList(userId));
        myPageMainResponseDTO.setBookmarkList(getBookmarkList(userId));
        myPageMainResponseDTO.setFollowingList(getFollowingList(userId));
        myPageMainResponseDTO.setFollowerList(getFollowerList(userId));
        myPageMainResponseDTO.setAttendance(getAttendance(userId));
        myPageMainResponseDTO.setStudyStatusList(getStudyStatusList(userId));

        return myPageMainResponseDTO;
    }

    //    프로필 카드 조회
    @Override
    public MyPageProfileResponseDTO getProfile(Long userId) {
        MyPageProfileResponseDTO profile = myPageDAO.findProfileByUserId(userId);

        if (profile == null) {
            throw new MyPageException("회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        return profile;
    }

    //    내 활동 개수 조회
    @Override
    public MyPageActivityResponseDTO getActivity(Long userId) {
        return myPageDAO.findActivityByUserId(userId);
    }

    //    내가 작성한 게시글 목록 조회
    @Override
    public List<MyPagePostResponseDTO> getMyPostList(Long userId) {
        return myPageDAO.findMyPostListByUserId(userId);
    }

    //    좋아요한 게시글 목록 조회
    @Override
    public List<MyPagePostResponseDTO> getBookmarkList(Long userId) {
        return myPageDAO.findBookmarkListByUserId(userId);
    }

    //    팔로잉 목록 조회
    @Override
    public List<MyPageFollowResponseDTO> getFollowingList(Long userId) {
        return myPageDAO.findFollowingListByUserId(userId);
    }

    //    팔로워 목록 조회
    @Override
    public List<MyPageFollowResponseDTO> getFollowerList(Long userId) {
        return myPageDAO.findFollowerListByUserId(userId);
    }

    //    출석 정보 조회
    @Override
    public MyPageAttendanceResponseDTO getAttendance(Long userId) {
        List<LocalDate> attendanceDateList = myPageDAO.findAttendanceDateListByUserId(userId);

        return calculateAttendance(attendanceDateList);
    }

    //    학습 현황 조회
    @Override
    public List<MyPageStudyStatusResponseDTO> getStudyStatusList(Long userId) {
        return myPageDAO.findStudyStatusListByUserId(userId);
    }

    //    출석 날짜 목록으로 현재 연속 출석일과 최고 연속 출석일 계산
    private MyPageAttendanceResponseDTO calculateAttendance(List<LocalDate> attendanceDateList) {
        MyPageAttendanceResponseDTO attendance = new MyPageAttendanceResponseDTO();

        if (attendanceDateList == null || attendanceDateList.isEmpty()) {
            attendance.setAttendanceCount(0L);
            attendance.setAttendanceStartDate(null);
            attendance.setMaxAttendanceCount(0L);
            return attendance;
        }

        List<LocalDate> dates = new ArrayList<>();

        for (LocalDate date : attendanceDateList) {
            if (!dates.contains(date)) {
                dates.add(date);
            }
        }

        LocalDate today = LocalDate.now();
        LocalDate baseDate = dates.contains(today) ? today : today.minusDays(1);

        long currentCount = 0L;
        LocalDate attendanceStartDate = null;

        for (LocalDate date : dates) {
            if (date.equals(baseDate.minusDays(currentCount))) {
                currentCount++;
                attendanceStartDate = date;
            }
        }

        long maxCount = 1L;
        long tempCount = 1L;

        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate currentDate = dates.get(i);
            LocalDate nextDate = dates.get(i + 1);

            if (currentDate.minusDays(1).equals(nextDate)) {
                tempCount++;
                maxCount = Math.max(maxCount, tempCount);
            } else {
                tempCount = 1L;
            }
        }

        attendance.setAttendanceCount(currentCount);
        attendance.setAttendanceStartDate(attendanceStartDate);
        attendance.setMaxAttendanceCount(maxCount);

        return attendance;
    }

    // 정보수정

    //    정보수정 화면 회원 정보 조회
    @Override
    public MyPageProfileResponseDTO getUserInfo(Long userId) {
        MyPageProfileResponseDTO userInfo = myPageDAO.findUserInfoById(userId);

        if (userInfo == null) {
            throw new MyPageException("회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        return userInfo;
    }

    //    소셜 로그인 회원 여부 조회
    @Override
    public boolean isSocialUser(Long userId) {
        return myPageDAO.findSocialUserCountByUserId(userId) > 0;
    }

    //    닉네임 중복 검사
    @Override
    public boolean isDuplicatedNickname(String userNickname, Long userId) {
        return myPageDAO.findUserCountByUserNickname(userNickname, userId) > 0;
    }

    //    기본 프로필 수정
    @Override
    public void updateUserBasicInfo(MyPageEditRequestDTO requestDTO, Long userId) {
        getUserInfo(userId);

        if (isDuplicatedNickname(requestDTO.getUserNickname(), userId)) {
            throw new MyPageException("이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }

        myPageDAO.updateUserBasicInfo(requestDTO, userId);
    }

    //    계정 정보 수정
    @Override
    public void updateUserAccountInfo(MyPageEditRequestDTO requestDTO, Long userId) {
        MyPageProfileResponseDTO userInfo = getUserInfo(userId);

        if (isSocialUser(userId) && !userInfo.getUserEmail().equals(requestDTO.getUserEmail())) {
            throw new MyPageException("소셜 로그인 회원은 이메일을 변경할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!userInfo.getUserPhoneNum().equals(requestDTO.getUserPhoneNum())) {
            boolean phoneCheck = authService.verifyUserPhoneVerificationCode(requestDTO.getUserPhoneNum(), requestDTO.getPhoneCode());

            if (!phoneCheck) {
                throw new MyPageException("휴대폰 인증이 필요합니다.", HttpStatus.BAD_REQUEST);
            }
        }

        if (!userInfo.getUserEmail().equals(requestDTO.getUserEmail())) {
            boolean emailCheck = authService.verifyUserEmailVerificationCode(requestDTO.getUserEmail(), requestDTO.getEmailCode());

            if (!emailCheck) {
                throw new MyPageException("이메일 인증이 필요합니다.", HttpStatus.BAD_REQUEST);
            }
        }

        myPageDAO.updateUserAccountInfo(requestDTO, userId);
    }

    //    프로필 사진 수정
    @Override
    public void updateUserProfile(MultipartFile uploadFile, Long userId) {
        getUserInfo(userId);

        ApiResponseDTO response = fileService.uploadFile(uploadFile);
        Map<String, Object> data = (Map<String, Object>) response.getData();
        String uploadedUrl = String.valueOf(data.get("uploadedUrl"));

        myPageDAO.updateUserProfile(uploadedUrl, userId);
    }

    //    프로필 사진 기본 이미지로 변경
    @Override
    public void deleteUserProfile(Long userId) {
        getUserInfo(userId);

        myPageDAO.deleteUserProfile(userId);
    }

    //    비밀번호 변경
    @Override
    public void updateUserPassword(String currentPassword, String newPassword, Long userId) {
        getUserInfo(userId);

        if (isSocialUser(userId)) {
            throw new MyPageException("소셜 로그인 회원은 비밀번호를 변경할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        String savedPassword = myPageDAO.findUserPasswordByUserId(userId);

        if (!passwordEncoder.matches(currentPassword, savedPassword)) {
            throw new MyPageException("현재 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        myPageDAO.updateUserPassword(passwordEncoder.encode(newPassword), userId);
    }
}