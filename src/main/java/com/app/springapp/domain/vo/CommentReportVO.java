package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.request.CommentReportRequestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class CommentReportVO {
    private Long id;
    private String commentReportTitle;
    private String commentReportDetail;
    private LocalDateTime commentReportCreateAt;
    private Long userId;
    private Long commentId;

    public static CommentReportVO from(CommentReportRequestDTO commentReportRequestDTO) {
        CommentReportVO commentReportVO = new CommentReportVO();
        commentReportVO.setCommentReportTitle(commentReportRequestDTO.getCommentReportTitle());
        commentReportVO.setCommentReportDetail(commentReportRequestDTO.getCommentReportDetail());
        commentReportVO.setCommentId(commentReportRequestDTO.getCommentId());

        return commentReportVO;
    }
}
