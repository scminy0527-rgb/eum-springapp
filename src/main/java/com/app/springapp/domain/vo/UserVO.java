package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.UserDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class UserVO {
    private Long id;
    private String userName;
    private String userNickname;
    private String userIntro;
    private String userJob;
    private String userAddress;
    private String userEmail;
    private String userPhoneNum;
    private String userPassword;
    private int userExp;
    private String userProfile;
    private LocalDateTime userCreateAt;

    public static UserVO from(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        userVO.setUserEmail(userDTO.getUserEmail());
        userVO.setUserPassword(userDTO.getUserPassword());
        userVO.setUserProfile(userDTO.getUserProfile() != null ? userDTO.getUserProfile() : "https://testapp-codefuling.s3.ap-northeast-2.amazonaws.com/cat.jpg");
        userVO.setUserName(userDTO.getUserName());
        userVO.setUserNickname(userDTO.getUserNickname() != null ? userDTO.getUserNickname() : "개복치 1단계");
        return userVO;
    }
}
