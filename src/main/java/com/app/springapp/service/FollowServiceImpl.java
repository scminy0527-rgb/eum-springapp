package com.app.springapp.service;

import com.app.springapp.domain.vo.FollowVO;
import com.app.springapp.repository.FollowDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowDAO followDAO;

//    유저 팔로우 하기
    @Override
    public void userFollow(Long userId, Long followingId) {
        FollowVO followVO = new FollowVO();
        followVO.setFollowerId(userId);
        followVO.setFollowingId(followingId);

        followDAO.save(followVO);
    }

//    유저 팔로우 취소
    @Override
    public void cancelFollow(Long userId, Long followingId) {
        FollowVO followVO = new FollowVO();
        followVO.setFollowerId(userId);
        followVO.setFollowingId(followingId);

        followDAO.remove(followVO);
    }
}
