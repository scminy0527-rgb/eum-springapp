package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.request.UserReportRequestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class UserReportVO {
    private Long id;
    private String userReportTitle;
    private String userReportDetail;
    private LocalDateTime userReportCreateAt;
    private Long userId;
    private Long reportingUserId;

    public static UserReportVO from(UserReportRequestDTO userReportRequestDTO) {
        UserReportVO userReportVO = new UserReportVO();
        userReportVO.setUserReportTitle(userReportRequestDTO.getUserReportTitle());
        userReportVO.setUserReportDetail(userReportRequestDTO.getUserReportDetail());
        userReportVO.setReportingUserId(userReportRequestDTO.getReportingUserId());

        return userReportVO;
    }
}
