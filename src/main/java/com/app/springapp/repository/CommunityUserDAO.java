package com.app.springapp.repository;

import com.app.springapp.domain.dto.CommunityUserDTO;
import com.app.springapp.mapper.CommunityUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommunityUserDAO {
    private final CommunityUserMapper communityUserMapper;

//    유저 정보를 불러오기
    public Optional<CommunityUserDTO> findById(Map<String,Object> req){
        return Optional.ofNullable(communityUserMapper.select(req));
    }

//    모든 유저 정보 불러오기 (최근 4명)
    public List<CommunityUserDTO> findAll(Map<String,Object> req){
        return communityUserMapper.selectAll(req);
    }
}
