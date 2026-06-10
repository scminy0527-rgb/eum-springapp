package com.app.springapp.repository;

import com.app.springapp.domain.vo.FollowVO;
import com.app.springapp.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowDAO {
    private final FollowMapper followMapper;

//    팔로후 하기
    public void save(FollowVO followVO) {
        followMapper.insert(followVO);
    }

//    팔로우 취소
    public void remove(FollowVO followVO) {
        followMapper.deleteByFollowerIdAndFollowingId(followVO);
    }
}
