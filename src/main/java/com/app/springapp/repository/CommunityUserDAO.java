package com.app.springapp.repository;

import com.app.springapp.domain.dto.CommunityUserDTO;
import com.app.springapp.mapper.CommunityUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommunityUserDAO {
    private final CommunityUserMapper communityUserMapper;

    public Optional<CommunityUserDTO> findById(Long id){
        return Optional.ofNullable(communityUserMapper.select(id));
    }
}
