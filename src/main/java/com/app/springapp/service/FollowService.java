package com.app.springapp.service;

public interface FollowService {
//    유저 팔로우
    public void userFollow(Long userId, Long followingId);

//    유저 팔로우 취소
    public void cancelFollow(Long userId, Long followingId);
}
