package com.app.springapp.api;

import com.app.springapp.domain.dto.request.VerificationRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verifications")
@RequiredArgsConstructor
@Slf4j
public class SmsApi {

    private final AuthService authService;

    // 휴대폰 인증코드 발송
    @PostMapping("/phone/verification-code")
    @Operation(summary = "휴대폰 인증코드 발송", description = "입력한 휴대폰 번호로 6자리 인증코드 SMS 발송")
    @ApiResponse(responseCode = "200", description = "SMS 발송 성공")
    @ApiResponse(responseCode = "400", description = "SMS 발송 실패 (유효하지 않은 번호)")
    public ResponseEntity<ApiResponseDTO> sendMemberPhoneVerificationCode(
            @RequestBody VerificationRequestDTO verificationRequestDTO) {

        String memberPhone = verificationRequestDTO.getMemberPhone();
        log.info(">>> SMS 인증코드 발송 요청 - phone: {}", memberPhone);
        boolean result = authService.sendUserPhoneVerificationCode(memberPhone);
        log.info(">>> SMS 인증코드 발송 결과 - result: {}", result);

        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.of(false, "휴대폰 번호를 확인해주세요."));
        }
        return ResponseEntity.ok(ApiResponseDTO.of(true, "메시지가 발송되었습니다."));
    }

    // 휴대폰 인증코드 검증
    @PutMapping("/phone/verification-code")
    @Operation(summary = "휴대폰 인증코드 검증", description = "발송된 SMS 인증코드 일치 여부 확인")
    @ApiResponse(responseCode = "200", description = "인증 성공")
    @ApiResponse(responseCode = "400", description = "인증 실패 (코드 불일치)")
    public ResponseEntity<ApiResponseDTO> verifyMemberPhoneVerificationCode(
            @RequestBody VerificationRequestDTO verificationRequestDTO) {

        String memberPhone = verificationRequestDTO.getMemberPhone();
        String code = verificationRequestDTO.getCode();
        boolean result = authService.verifyUserPhoneVerificationCode(memberPhone, code);

        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.of(false, "인증번호를 확인해주세요."));
        }
        return ResponseEntity.ok(ApiResponseDTO.of(true, "인증이 완료되었습니다."));
    }

    // 이메일 인증코드 발송
    @PostMapping("/email/verification-code")
    @Operation(summary = "이메일 인증코드 발송", description = "입력한 이메일로 6자리 인증코드 발송")
    @ApiResponse(responseCode = "200", description = "이메일 발송 성공")
    @ApiResponse(responseCode = "400", description = "이메일 발송 실패 (유효하지 않은 이메일)")
    public ResponseEntity<ApiResponseDTO> sendMemberEmailVerificationCode(
            @RequestBody VerificationRequestDTO verificationRequestDTO) {

        String memberEmail = verificationRequestDTO.getMemberEmail();
        boolean result = authService.sendUserEmailVerificationCode(memberEmail);

        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.of(false, "이메일 주소를 확인해주세요."));
        }
        return ResponseEntity.ok(ApiResponseDTO.of(true, "이메일이 발송되었습니다."));
    }

    // 이메일 인증코드 검증
    @PutMapping("/email/verification-code")
    @Operation(summary = "이메일 인증코드 검증", description = "발송된 이메일 인증코드 일치 여부 확인")
    @ApiResponse(responseCode = "200", description = "인증 성공")
    @ApiResponse(responseCode = "400", description = "인증 실패 (코드 불일치)")
    public ResponseEntity<ApiResponseDTO> verifyMemberEmailVerificationCode(
            @RequestBody VerificationRequestDTO verificationRequestDTO) {

        String memberEmail = verificationRequestDTO.getMemberEmail();
        String code = verificationRequestDTO.getCode();
        boolean result = authService.verifyUserEmailVerificationCode(memberEmail, code);

        if (!result) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.of(false, "인증번호를 확인해주세요."));
        }
        return ResponseEntity.ok(ApiResponseDTO.of(true, "인증이 완료되었습니다."));
    }
}
