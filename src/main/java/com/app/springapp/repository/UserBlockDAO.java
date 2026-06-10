package com.app.springapp.repository;

import com.app.springapp.domain.vo.UserBlockVO;
import com.app.springapp.mapper.UserBlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBlockDAO {

    private final UserBlockMapper userBlockMapper;

    //    유저 차단 추가
    public void save(UserBlockVO userBlockVO){
        userBlockMapper.insert(userBlockVO);
    }
}
