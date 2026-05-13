package com.app.springapp.repository;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.vo.UserVO;
import com.app.springapp.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final UserMapper userMapper;

    // 유저 추가
    public void save(UserVO userVO) {
        userMapper.insert(userVO);
    }

    // 유저 조회 (ID)
    public Optional<UserDTO> findUserById(Long id) {
        return Optional.ofNullable(userMapper.select(id));
    }

    // 유저 조회 (이메일 + 소셜 프로바이더)
    public Optional<UserDTO> findUserByUserEmailAndSocialUserProvider(UserDTO userDTO) {
        return Optional.ofNullable(userMapper.selectByUserEmailAndSocialUserProvider(userDTO));
    }

    // 가입 여부 조회
    public boolean existsUserByUserEmailAndSocialUserProvider(UserDTO userDTO) {
        return userMapper.existsUserByUserEmailAndSocialUserProvider(userDTO);
    }

    // 유저 수정
    public void update(UserVO userVO) {
        userMapper.update(userVO);
    }

    // 프로필 사진 변경
    public void updatePicture(UserVO userVO) {
        userMapper.updatePicture(userVO);
    }

    // 유저 삭제
    public void delete(Long id) {
        userMapper.delete(id);
    }
}
