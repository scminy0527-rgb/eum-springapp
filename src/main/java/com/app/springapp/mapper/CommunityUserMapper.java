package com.app.springapp.mapper;

import com.app.springapp.domain.dto.CommunityUserDTO;
import com.app.springapp.domain.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunityUserMapper {
//    유저 id 로 유저 정보 조회
    public CommunityUserDTO select(Map<String,Object> req);

//    모든 유저 정보 페이지네이션으로 불러오기
    public List<CommunityUserDTO> selectAll(Map<String,Object> req);
}
