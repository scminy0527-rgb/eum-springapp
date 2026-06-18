package com.app.springapp.config;

import com.app.springapp.domain.dto.request.EduWordFromSignWordRequestDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.SignWordDAO;
import com.app.springapp.service.edu.EduWordMapService;
import com.app.springapp.service.edu.SignWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudyDataBootstrapRunner implements ApplicationRunner {
    private final SignWordDAO signWordDAO;
    private final SignWordService signWordService;
    private final EduWordMapService eduWordMapService;

    @Value("${study.bootstrap.enabled:false}")
    private boolean bootstrapEnabled;

    // OpenAPI 자동 동기화
    @Override
    public void run(ApplicationArguments args) {
        if (!bootstrapEnabled) {
//            log.info("study bootstrap enabled = {}", bootstrapEnabled);
            return;
        }

        int signWordCount = signWordDAO.countAll();
//        log.info("sign word count before sync = {}", signWordCount);

        if (signWordCount == 0) {
            int totalSavedCount = 0;

            try {
                totalSavedCount += signWordService.syncSignWords(1, 3617);
            } catch (Exception e) {
                log.warn("study bootstrap skipped: OpenAPI sync failed.", e);
                return;
            }

            log.info("sign word saved count = {}", totalSavedCount);

            int afterSyncCount = signWordDAO.countAll();

            if (afterSyncCount == 0) {
//                log.warn("study bootstrap stopped: OpenAPI sync result is empty.");
                return;
            }
        }
        bootstrapEduWords();
    }

    // 기본 학습 단어 등록
    private void bootstrapEduWords() {
        registerEduWord(1L, 4L, "기초");
        registerEduWord(1L, 5L, "기초");
        registerEduWord(1L, 21L, "기초");
        registerEduWord(1L, 39L, "기초");
        registerEduWord(1L, 52L, "기초");

        registerEduWord(2L, 68L, "중급");
        registerEduWord(2L, 69L, "중급");
        registerEduWord(2L, 70L, "중급");
        registerEduWord(2L, 84L, "중급");
        registerEduWord(2L, 90L, "중급");

        registerEduWord(3L, 111L, "고급");
        registerEduWord(3L, 112L, "고급");
        registerEduWord(3L, 113L, "고급");
        registerEduWord(3L, 195L, "고급");
        registerEduWord(3L, 197L, "고급");
    }

    // 하나씩 등록
    private void registerEduWord(Long eduId, Long signWordId, String wordsType) {
        EduWordFromSignWordRequestDTO requestDTO = new EduWordFromSignWordRequestDTO();
        requestDTO.setEduId(eduId);
        requestDTO.setSignWordId(signWordId);
        requestDTO.setWordsType(wordsType);

        try {
            eduWordMapService.saveEduWordFromSignWord(requestDTO);
        } catch (EduException e) {
            if (e.getHttpStatus() == HttpStatus.CONFLICT) {
                return;
            }
            // 일부 데이터 등록 실패는 로그를 남기고 서버 시작 실패 원인을 확인할 수 있게 처리합니다.
            log.warn(
                    "study bootstrap edu word skipped. eduId={}, signWordId={}, reason={}",
                    eduId, signWordId, e.getMessage()
            );

            throw e;
        } catch (Exception e) {
            log.error(
                    "study bootstrap edu word failed. eduId={}, signWordId={}",
                    eduId,
                    signWordId,
                    e
            );
            throw e;
        }
    }
}