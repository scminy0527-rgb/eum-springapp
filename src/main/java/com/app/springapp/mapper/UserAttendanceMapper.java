package com.app.springapp.mapper;

import com.app.springapp.domain.vo.UserAttendanceVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserAttendanceMapper {

    // 오늘 출석 여부 조회
    public int countTodayAttendance(Long userId);

    // 오늘 출석 등록
    public void insertTodayAttendance(UserAttendanceVO userAttendanceVO);

    // 오늘 출석 기록 번호 조회
    public Long selectTodayAttendanceId(Long userId);

    // 누적 출석일 조회
    public int countTotalAttendance(Long userId);

    // 현재 연속 출석일 조회
    public int selectConsecutiveDays(Long userId);

    // 기간별 출석 날짜 조회
    public List<LocalDate> selectAttendanceDatesByPeriod(Long userId, LocalDate startDate, LocalDate endDate);

    // 마지막 출석일 조회
    public LocalDate selectLastAttendanceDate(Long userId);
}
