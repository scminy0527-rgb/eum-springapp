package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.request.PostReportRequestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class PostReportVO {
    private Long id;
    private String postReportTitle;
    private String postReportDetail;
    private LocalDateTime postReportCreateAt;
    private Long userId;
    private Long postId;

    public static PostReportVO from(PostReportRequestDTO postReportRequestDTO) {
        PostReportVO postReportVO = new PostReportVO();
        postReportVO.setPostReportTitle(postReportRequestDTO.getPostReportTitle());
        postReportVO.setPostReportDetail(postReportRequestDTO.getPostReportDetail());
        postReportVO.setPostId(postReportRequestDTO.getPostId());

        return postReportVO;
    }
}
