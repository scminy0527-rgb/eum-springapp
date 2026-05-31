package com.app.springapp.service;

import com.app.springapp.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommunityAuthServiceImpl implements CommunityAuthService {
    @Override
    public Long getUserId() {
        return 2L;
    }

//    만약 userid 가 null 이면 예외 반환
    @Override
    public void checkUserValidity(Long userId) {
        if (userId == null || userId == 0L) {
            throw new UserException("접근 권한 없음", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
