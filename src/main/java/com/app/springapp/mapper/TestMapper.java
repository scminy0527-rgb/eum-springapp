package com.app.springapp.mapper;

import com.app.springapp.domain.dto.TestDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    // 시험 목록 조회
    List<TestDTO> selectAll();

    // 시험 단건 조회
    TestDTO select(Long id);
}
