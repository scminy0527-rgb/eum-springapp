package com.app.springapp.mapper;

import com.app.springapp.domain.vo.SocialUserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SocialUserMapper {
//    회원 추가
    public void insert(SocialUserVO socialMemberVO);
}
