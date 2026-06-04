package com.app.springapp.service;

import com.app.springapp.domain.dto.CommunityUserDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.CommunityUserResponseDTO;
import com.app.springapp.domain.dto.response.UserResponseDTO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.CommunityUserDAO;
import com.app.springapp.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class, UserException.class})
@RequiredArgsConstructor
public class CommunityProfileServiceImpl implements CommunityProfileService {

    private final CommunityUserDAO communityUserDAO;

    //    커뮤니티 프로필 페이지에서 쓸 유저 정보 추출
    @Override
    public CommunityUserResponseDTO getUserInfo(Long id) {
        CommunityUserDTO communityUserDTO = communityUserDAO.findById(id).orElseThrow(
            () -> {throw new UserException("해당 유저 정보를 불러올 수 없습니다", HttpStatus.NOT_FOUND);
        });
        return CommunityUserResponseDTO.from(communityUserDTO);
    }
}
