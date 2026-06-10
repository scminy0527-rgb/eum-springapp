package com.app.springapp.mapper;

import com.app.springapp.domain.vo.FollowVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
//    팔로우 추가
    public void insert(FollowVO followVO);

//    팔로우 삭제
    public void deleteByFollowerIdAndFollowingId(FollowVO followVO);
}
