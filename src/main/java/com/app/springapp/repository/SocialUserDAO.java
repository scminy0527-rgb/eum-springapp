package com.app.springapp.repository;

import com.app.springapp.domain.vo.SocialUserVO;
import com.app.springapp.mapper.SocialUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SocialUserDAO {
    private final SocialUserMapper socialUserMapper;

    // 소셜 유저 추가
    public void save(SocialUserVO socialUserVO) {
        socialUserMapper.insert(socialUserVO);
    }
}
