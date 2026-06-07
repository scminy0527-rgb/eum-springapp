package com.app.springapp.mapper;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 유저 추가
    public void insert(UserVO userVO);
    // 유저 조회 (ID)
    public UserDTO select(Long id);
    // 유저 조회 (이메일 + 소셜 프로바이더)
    public UserDTO selectByUserEmailAndSocialUserProvider(UserDTO userDTO);
    // 유저 가입 여부 조회
    public boolean existsUserByUserEmailAndSocialUserProvider(UserDTO userDTO);
    // 유저 수정
    public void update(UserVO userVO);
    // 프로필 사진 변경
    public void updatePicture(UserVO userVO);
    // 유저 삭제
    public void delete(Long id);
    // 이메일 찾기
    public String selectEmailByUserName(String userName);
    // 이메일 존재 여부 조회
    public boolean existsUserByEmail(@Param("userEmail") String userEmail);
    // 비밀번호 재설정
    public void updatePasswordByEmail(@Param("userEmail") String userEmail, @Param("newPassword") String newPassword);
    // 전체 유저 ID 조회
    public List<Long> selectAllUserIds();
}
