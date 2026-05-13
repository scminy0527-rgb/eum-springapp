package com.app.springapp.mapper;

import com.app.springapp.domain.vo.ChatUserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatUserMapper {
    public void insert(ChatUserVO chatMemberVO);
}
