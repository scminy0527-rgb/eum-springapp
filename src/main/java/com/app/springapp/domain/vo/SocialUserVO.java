package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.UserDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SocialUserVO {
    private Long id;
    private String socialUserProviderId;
    private String socialUserProvider;
    private Long userId;

    public static SocialUserVO from(UserDTO userDTO) {
        SocialUserVO socialUserVO = new SocialUserVO();
        socialUserVO.setSocialUserProviderId(userDTO.getSocialUserProviderId());
        socialUserVO.setSocialUserProvider(userDTO.getSocialUserProvider() != null ? userDTO.getSocialUserProvider() : "local");
        socialUserVO.setUserId(userDTO.getId());
        return socialUserVO;
    }
}
