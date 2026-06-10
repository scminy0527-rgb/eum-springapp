package com.app.springapp.mapper;

import com.app.springapp.domain.vo.UserBlockVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBlockMapper {
//    유저 차단
    public void insert(UserBlockVO userBlockVO);
}
