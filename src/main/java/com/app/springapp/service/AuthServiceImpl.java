package com.app.springapp.service;

import com.app.springapp.domain.dto.JwtTokenDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.vo.UserVO;
import com.app.springapp.domain.vo.SocialUserVO;
import com.app.springapp.exception.JwtTokenException;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.UserDAO;
import com.app.springapp.repository.SocialUserDAO;
import com.app.springapp.util.AuthCodeGenerator;
import com.app.springapp.util.JwtTokenUtil;
import com.app.springapp.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.token-blacklist-prefix}")
    private String BLACKLIST_TOKEN_PREFIX;

    @Value("${jwt.refresh-blacklist-prefix}")
    private String REFRESH_TOKEN_PREFIX;

    private final UserDAO userDAO;
    private final SocialUserDAO socialUserDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;
    private final SmsUtil smsUtil;

    @Override
    public JwtTokenDTO login(UserDTO userDTO) {
        UserVO userVO = UserVO.from(userDTO);
        log.info("userDTO: {}", userDTO);

        UserDTO foundUser = userDAO
                .findUserByUserEmailAndSocialUserProvider(userDTO)
                .orElseThrow(() -> new UserException("회원이 아닙니다.", HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(userVO.getUserPassword(), foundUser.getUserPassword())) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> claims = new HashMap<>();
        claims.put("id", foundUser.getId().toString());
        claims.put("userEmail", foundUser.getUserEmail());
        claims.put("socialUserProvider", "local");

        String accessToken = jwtTokenUtil.generateAccessToken(claims);
        String refreshToken = jwtTokenUtil.generateRefreshToken(claims);

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        jwtTokenDTO.setAccessToken(accessToken);
        jwtTokenDTO.setRefreshToken(refreshToken);

        saveRefreshToken(jwtTokenDTO);
        return jwtTokenDTO;
    }

    @Override
    public JwtTokenDTO socialLogin(UserDTO userDTO) {
        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        Map<String, String> claims = new HashMap<>();

        if (userDAO.existsUserByUserEmailAndSocialUserProvider(userDTO)) {
            UserDTO foundUser = userDAO
                    .findUserByUserEmailAndSocialUserProvider(userDTO)
                    .orElseThrow(() -> new UserException("socialLogin 유저 조회 실패", HttpStatus.BAD_REQUEST));
            claims.put("id", foundUser.getId().toString());
        } else {
            UserVO userVO = UserVO.from(userDTO);
            SocialUserVO socialUserVO = SocialUserVO.from(userDTO);

            userDAO.save(userVO);
            socialUserVO.setUserId(userVO.getId());
            socialUserDAO.save(socialUserVO);
            claims.put("id", userVO.getId().toString());
        }

        claims.put("userEmail", userDTO.getUserEmail());
        claims.put("socialUserProvider", userDTO.getSocialUserProvider());

        String accessToken = jwtTokenUtil.generateAccessToken(claims);
        String refreshToken = jwtTokenUtil.generateRefreshToken(claims);

        jwtTokenDTO.setAccessToken(accessToken);
        jwtTokenDTO.setRefreshToken(refreshToken);

        saveRefreshToken(jwtTokenDTO);
        return jwtTokenDTO;
    }

    // 실무에서는 Access Token을 받는것이 관례
    @Override
    public void logout(JwtTokenDTO jwtTokenDTO) {
        log.info("jwtTokenDTO: {}", jwtTokenDTO);
        log.info("{}", validateRefreshToken(jwtTokenDTO));

        if(validateRefreshToken(jwtTokenDTO)){
            saveBlackListedToken(jwtTokenDTO);
        }else {
            throw new JwtTokenException("권한 없음", HttpStatus.UNAUTHORIZED);
        }
    }

    // Redis에 refresh Token 저장
    // 콜론체이닝(:) 방법으로 저장
    @Override
    public boolean saveRefreshToken(JwtTokenDTO jwtTokenDTO) {
        String refreshToken = jwtTokenDTO.getRefreshToken();
        Long id = Long.parseLong((String)jwtTokenUtil.parseToken(refreshToken).get("id"));
        String key = REFRESH_TOKEN_PREFIX + id;

        try {
            redisTemplate.opsForValue().set(key, refreshToken, 30, TimeUnit.DAYS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Redis에 저장된 refresh Token이 유효한지 검증
    @Override
    public boolean validateRefreshToken(JwtTokenDTO jwtTokenDTO) {
        String refreshToken = jwtTokenDTO.getRefreshToken();
        Long id = Long.parseLong((String)jwtTokenUtil.parseToken(refreshToken).get("id"));
        String key = REFRESH_TOKEN_PREFIX + id;

        try {
            Object storedToken = redisTemplate.opsForValue().get(key);
            if(!refreshToken.equals(storedToken)){
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Redis에 블랙리스트를 등록 (로그아웃 시 토큰 무효화)
    @Override
    public boolean saveBlackListedToken(JwtTokenDTO jwtTokenDTO) {

        String refreshToken = jwtTokenDTO.getRefreshToken();
        Long refreshId = Long.parseLong((String)jwtTokenUtil.parseToken(refreshToken).get("id"));
        String refreshKey = BLACKLIST_TOKEN_PREFIX + refreshId;

        String accessToken = jwtTokenDTO.getAccessToken();
        Long accessId = Long.parseLong((String)jwtTokenUtil.parseToken(accessToken).get("id"));
        String accessKey = BLACKLIST_TOKEN_PREFIX + accessId;

        try {
            redisTemplate.opsForSet().add(refreshKey, refreshToken);
            redisTemplate.opsForSet().add(accessKey, accessToken);
            // TTL
            redisTemplate.expire(refreshKey, 30, TimeUnit.DAYS);
            redisTemplate.expire(accessKey, 1, TimeUnit.DAYS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Redis에 등록된 블랙리스트인지 검증
    @Override
    public boolean isBlackListedToken(JwtTokenDTO jwtTokenDTO) {
        String refreshToken = jwtTokenDTO.getRefreshToken();
        Long id = Long.parseLong((String)jwtTokenUtil.parseToken(refreshToken).get("id"));
        String key = BLACKLIST_TOKEN_PREFIX + id;

        try {
            Boolean isMember = redisTemplate.opsForSet().isMember(key, refreshToken);
            return isMember != null && isMember;
        } catch (Exception e) {
            return false;
        }
    }

    // refreshToken 토큰 -> accessToken을 재발급
    @Override
    public JwtTokenDTO reissueAccessToken(JwtTokenDTO jwtTokenDTO) {
        String refreshToken = jwtTokenDTO.getRefreshToken();
        Long id = Long.parseLong((String)jwtTokenUtil.parseToken(refreshToken).get("id"));

        if(isBlackListedToken(jwtTokenDTO)){
            throw new JwtTokenException("이미 로그아웃 된 토큰입니다", HttpStatus.BAD_REQUEST);
        }

        // 리프레쉬 검증
        if(!validateRefreshToken(jwtTokenDTO)){
            throw new JwtTokenException("유효하지 않은 토큰입니다", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> claims = new HashMap<>();
        UserDTO user = userDAO
                .findUserById(id).orElseThrow(() -> new UserException("유저가 없습니다", HttpStatus.BAD_REQUEST));

        claims.put("id", user.getId().toString());
        claims.put("userEmail", user.getUserEmail());
        claims.put("socialUserProvider", user.getSocialUserProvider());

        // 새로운 토큰 생성
        String newAccessToken = jwtTokenUtil.generateAccessToken(claims);
        jwtTokenDTO.setAccessToken(newAccessToken);

        return jwtTokenDTO;
    }

    // 핸드폰 인증 코드 발송
    @Override
    public boolean sendUserPhoneVerificationCode(String userPhone) {
        String code = AuthCodeGenerator.generateAuthCode();
        String message = "[이음]\n인증코드를 입력해주세요.\n[" + code + "]";
        String key = "phone:" + userPhone + ":" + code;
        try {
            var smsResponse = smsUtil.sendOneMemberPhone(userPhone, message);
            log.info("SMS 발송 결과: {}", smsResponse);
            redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            log.error("SMS 발송 실패 - phone: {}, error: {}", userPhone, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verifyUserPhoneVerificationCode(String userPhone, String code) {
        String key = "phone:" + userPhone + ":" + code;
        try {
            String storedCode = String.valueOf(redisTemplate.opsForValue().get(key));
            redisTemplate.delete(key);
            return code.equals(storedCode);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean sendUserEmailVerificationCode(String userEmail) {
        String code = AuthCodeGenerator.generateAuthCode();
        String subject = "[이음] 이메일 인증 코드";
        String content = "인증코드를 입력해주세요.\n[" + code + "]\n유효시간: 3분";
        String key = "email:" + userEmail + ":" + code;
        try {
            smsUtil.sendMemberEmail(userEmail, subject, content);
            redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            log.error("이메일 발송 실패 - email: {}, error: {}", userEmail, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verifyUserEmailVerificationCode(String userEmail, String code) {
        String key = "email:" + userEmail + ":" + code;
        try {
            String storedCode = String.valueOf(redisTemplate.opsForValue().get(key));
            redisTemplate.delete(key);
            return code.equals(storedCode);
        } catch (Exception e) {
            return false;
        }
    }
}













