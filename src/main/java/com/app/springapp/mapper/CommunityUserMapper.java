package com.app.springapp.mapper;

import com.app.springapp.domain.dto.CommunityUserDTO;
import com.app.springapp.domain.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityUserMapper {
//    유저 id 로 유저 정보 조회
    public CommunityUserDTO select(Long id);
}
