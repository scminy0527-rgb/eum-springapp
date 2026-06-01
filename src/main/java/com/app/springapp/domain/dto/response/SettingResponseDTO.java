package com.app.springapp.domain.dto.response;

import com.app.springapp.domain.dto.SettingDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "유저 설정 응답 DTO")
public class SettingResponseDTO {

    @Schema(description = "설정 번호", example = "1")
    private Long id;

    @Schema(description = "댓글 알림 여부", example = "0")
    private int settingReply;

    @Schema(description = "좋아요 알림 여부", example = "0")
    private int settingGood;

    @Schema(description = "공지사항 알림 여부", example = "0")
    private int settingBulletin;

    @Schema(description = "학습 리마인더 여부", example = "0")
    private int settingStudy;

    @Schema(description = "이메일 수신 여부", example = "0")
    private int settingEmailPush;

    @Schema(description = "게시글 공개 여부", example = "0")
    private int settingPostOpen;

    @Schema(description = "수어 채팅 기본 사용 여부", example = "0")
    private int settingSignDefault;

    @Schema(description = "점자 번역 기능 여부", example = "0")
    private int settingBrailleTranslate;

    @Schema(description = "새 메시지 자동 스크롤 여부", example = "0")
    private int settingAutoScroll;

    @Schema(description = "실시간 알림 여부", example = "0")
    private int settingPushNotify;

    @Schema(description = "멘션 알림 여부", example = "0")
    private int settingMentionNotify;

    @Schema(description = "사용자 번호", example = "1")
    private Long userId;

    //    설정 DTO를 응답 DTO로 변환
    public static SettingResponseDTO from(SettingDTO settingDTO) {
        SettingResponseDTO responseDTO = new SettingResponseDTO();

        responseDTO.setId(settingDTO.getId());
        responseDTO.setSettingReply(settingDTO.getSettingReply());
        responseDTO.setSettingGood(settingDTO.getSettingGood());
        responseDTO.setSettingBulletin(settingDTO.getSettingBulletin());
        responseDTO.setSettingStudy(settingDTO.getSettingStudy());
        responseDTO.setSettingEmailPush(settingDTO.getSettingEmailPush());
        responseDTO.setSettingPostOpen(settingDTO.getSettingPostOpen());
        responseDTO.setSettingSignDefault(settingDTO.getSettingSignDefault());
        responseDTO.setSettingBrailleTranslate(settingDTO.getSettingBrailleTranslate());
        responseDTO.setSettingAutoScroll(settingDTO.getSettingAutoScroll());
        responseDTO.setSettingPushNotify(settingDTO.getSettingPushNotify());
        responseDTO.setSettingMentionNotify(settingDTO.getSettingMentionNotify());
        responseDTO.setUserId(settingDTO.getUserId());

        return responseDTO;
    }
}