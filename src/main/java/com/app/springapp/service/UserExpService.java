package com.app.springapp.service;

import com.app.springapp.domain.dto.response.MyPageProfileResponseDTO;

public interface UserExpService {

    //    게시글 작성 경험치 지급
    void addPostExp(Long userId, Long postId);

    //    댓글 작성 경험치 지급
    void addCommentExp(Long userId, Long commentId);

    //    학습 완료 경험치 지급
    void addStudyExp(Long userId, Long eduId);

    //    출석 완료 레벨 경험치 지급
    void addAttendanceExp(Long userId);

    //    마이페이지 프로필 레벨 정보 계산
    void setLevelInfo(MyPageProfileResponseDTO profileResponseDTO);

    //    회원탈퇴 시 경험치 이력 삭제
    void deleteUserExpHistoryByUserId(Long userId);
}
