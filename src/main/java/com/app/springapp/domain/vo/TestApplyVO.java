package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.TestApplyDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class TestApplyVO {
    private Long id;
    private LocalDateTime testApplyAt;
    private Long userId;
    private Long testId;

    public static TestApplyVO from(TestApplyDTO dto) {
        TestApplyVO vo = new TestApplyVO();
        vo.setId(dto.getId());
        vo.setTestApplyAt(dto.getTestApplyAt());
        vo.setUserId(dto.getUserId());
        vo.setTestId(dto.getTestId());
        return vo;
    }
}
