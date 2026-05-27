package com.app.springapp.service;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.UserVO;

public interface UserService {
    // 회원가입
    public ApiResponseDTO join(UserDTO userDTO);

    // 토큰 -> 유저 정보 조회
    public ApiResponseDTO me(Long id);

    // 유저 수정

    // 프로필 사진 수정
    public ApiResponseDTO updatePicture(UserVO userVO);

    // 이메일 찾기
    public ApiResponseDTO findEmail(String userName);

    // 비밀번호 재설정
    public ApiResponseDTO resetPassword(String userEmail, String newPassword);

    // 회원 탈퇴
}
