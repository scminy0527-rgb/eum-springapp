package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.TestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class TestVO {
    private Long id;
    private String testTitle;
    private String testDetail;
    private LocalDateTime testDate;
    private int testLimit;
    private String testLocation;
    private int testPrice;
    private int testIsDeleted;

    public static TestVO from(TestDTO dto) {
        TestVO vo = new TestVO();
        vo.setId(dto.getId());
        vo.setTestTitle(dto.getTestTitle());
        vo.setTestDetail(dto.getTestDetail());
        vo.setTestDate(dto.getTestDate());
        vo.setTestLimit(dto.getTestLimit());
        vo.setTestLocation(dto.getTestLocation());
        vo.setTestPrice(dto.getTestPrice());
        vo.setTestIsDeleted(dto.getTestIsDeleted());
        return vo;
    }
}
