package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Schema(description = "마이페이지 메인 응답 DTO")
public class MyPageMainResponseDTO {
    @Schema(description = "프로필 정보", required = true)
    private MyPageProfileResponseDTO profile;
    @Schema(description = "내 활동 정보", required = true)
    private MyPageActivityResponseDTO activity;
    @Schema(description = "내 게시글 목록", required = true)
    private List<MyPagePostResponseDTO> myPostList;
    @Schema(description = "좋아요한 게시글 목록", required = true)
    private List<MyPagePostResponseDTO> bookmarkList;
    @Schema(description = "팔로잉 목록", required = true)
    private List<MyPageFollowResponseDTO> followingList;
    @Schema(description = "팔로워 목록", required = true)
    private List<MyPageFollowResponseDTO> followerList;
    @Schema(description = "출석 정보", required = true)
    private MyPageAttendanceResponseDTO attendance;
    @Schema(description = "학습 현황 목록", required = true)
    private List<MyPageStudyStatusResponseDTO> studyStatusList;
}