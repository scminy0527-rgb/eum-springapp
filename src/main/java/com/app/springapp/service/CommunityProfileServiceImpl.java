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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class, UserException.class})
@RequiredArgsConstructor
public class CommunityProfileServiceImpl implements CommunityProfileService {

    private final CommunityUserDAO communityUserDAO;

    //    커뮤니티 프로필 페이지에서 쓸 유저 정보 추출
    @Override
    public CommunityUserResponseDTO getUserInfo(Map<String,Object> req) {
        CommunityUserDTO communityUserDTO = communityUserDAO.findById(req).orElseThrow(
            () -> {throw new UserException("해당 유저 정보를 불러올 수 없습니다", HttpStatus.NOT_FOUND);
        });
        return CommunityUserResponseDTO.from(communityUserDTO);
    }

//    커뮤니티 내에서 4개의 최신 유저 프로필 보여주기
    @Override
    public List<CommunityUserResponseDTO> getUsers(Map<String, Object> req) {
        return communityUserDAO.findAll(req)
                .stream()
                .map(CommunityUserResponseDTO::from)
                .collect(Collectors.toList());
    }
}
