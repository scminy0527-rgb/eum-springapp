package com.app.springapp.service.edu;

public interface QuizStartService {

    // 퀴즈 시작 기록 등록
    public void startQuiz(Long userId, Long quizId);

    // 퀴즈 진행 문제 수 증가
    public void updateProgress(Long userId, Long quizId, int totalCount, int isCorrect);




}
