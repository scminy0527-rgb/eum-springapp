package com.app.springapp.service;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.UserResponseDTO;
import com.app.springapp.domain.vo.UserVO;
import com.app.springapp.domain.vo.SocialUserVO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.UserDAO;
import com.app.springapp.repository.SocialUserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final SocialUserDAO socialUserDAO;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public ApiResponseDTO join(UserDTO userDTO) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        Map<String, Object> claims = new HashMap<>();

        if (userDAO.existsUserByUserEmailAndSocialUserProvider(userDTO)) {
            throw new UserException("중복된 이메일 입니다.", HttpStatus.BAD_REQUEST);
        }

        UserVO userVO = UserVO.from(userDTO);
        SocialUserVO socialUserVO = SocialUserVO.from(userDTO);

        if ("local".equals(socialUserVO.getSocialUserProvider())) {
            userVO.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        }

        userDAO.save(userVO);
        socialUserVO.setUserId(userVO.getId());
        socialUserDAO.save(socialUserVO);

        claims.put("id", userVO.getId());
        claims.put("userEmail", userVO.getUserEmail());
        claims.put("socialUserProvider", socialUserVO.getSocialUserProvider());

        apiResponseDTO.setSuccess(true);
        apiResponseDTO.setMessage("회원가입이 완료되었습니다.");
        apiResponseDTO.setData(claims);

        return apiResponseDTO;
    }

    // 토큰 → 유저 정보 조회
    @Override
    public ApiResponseDTO me(Long id) {
        UserDTO foundUser = userDAO.findUserById(id)
                .orElseThrow(() -> new UserException("유저 조회 실패", HttpStatus.BAD_REQUEST));

        UserResponseDTO userResponseDTO = UserResponseDTO.from(foundUser);
        return new ApiResponseDTO(true, "유저 조회 성공", userResponseDTO);
    }

    // 프로필 사진 변경
    @Override
    public ApiResponseDTO updatePicture(UserVO userVO) {
        Map<String, Object> datas = new HashMap<>();
        userDAO.updatePicture(userVO);
        datas.put("updatedUserProfileUrl", userVO.getUserProfile());
        return ApiResponseDTO.of(true, "사진 변경 완료", datas);
    }
}

















