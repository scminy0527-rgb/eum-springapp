-- OpenAPI 동기화 후 실행
-- 선택한 수어 단어를 기초·중급·고급 학습에 연결

-- 실행 순서:
-- 1. Swagger에서 POST /api/sign-words/sync 실행
-- 2. DB 콘솔에서 seed_edu_openapi.sql 전체 실행
-- 3. Swagger에서 GET /api/words/edu/1 확인
-- 4. eduId 1, 2, 3에 각각 5개씩 나오면 완료

-- OpenAPI 학습 단어 초기 등록용 seed SQL
-- 현재는 /study/admin 관리자 화면에서 학습 단어를 등록할 수 있고
-- 다만 팀원 로컬 DB 초기 세팅이나 동일한 테스트 데이터 복구가 필요할 때 이 파일 실행하기
-- 실행 전 POST /api/sign-words/sync로 TBL_SIGN_WORD 동기화 먼저 ↓

-- 팀원들이 실행할 순서
-- 1. Swagger: POST /api/sign-words/sync -> 1 ~ 299 execute
-- 2. DB: seed_edu_openapi.sql 전체 실행

-- 1. 학습 영상 등록
MERGE INTO TBL_EDU_VIDEO T
    USING (
        SELECT SW.SIGN_WORD_TITLE || ' 수어 영상' AS TITLE,
               NVL(SW.SIGN_WORD_DESCRIPTION, '설명이 준비되지 않았습니다.') AS DETAIL,
               SW.SIGN_WORD_VIDEO_URL AS VIDEO_URL
        FROM TBL_SIGN_WORD SW
        WHERE SW.SIGN_WORD_TITLE IN (
                                     '썰매', '대장', '호랑이,범', '(동물)사자', '다물다',
                                     '상승', '소매치기', '거래', '오디오,듣다,소식,청각', '타결,합의',
                                     '살인,살해', '보이다,(현상을)보다', '불가능,불능', '신고', '영국'
            )
          AND SW.SIGN_WORD_VIDEO_URL IS NOT NULL
    ) S
    ON (T.EDU_VIDEO_URL = S.VIDEO_URL)
    WHEN NOT MATCHED THEN
        INSERT (ID, EDU_VIDEO_TITLE, EDU_VIDEO_DETAIL, EDU_VIDEO_TYPE, EDU_VIDEO_URL)
            VALUES (SEQ_EDU_VIDEO.NEXTVAL, S.TITLE, S.DETAIL, 'openapi', S.VIDEO_URL);



-- 2. 학습 문제용 단어 등록
MERGE INTO TBL_WORDS T
    USING (
        SELECT SW.ID AS SIGN_WORD_ID,
               SW.SIGN_WORD_TITLE AS TITLE,
               NVL(SW.SIGN_WORD_DESCRIPTION, '설명이 준비되지 않았습니다.') AS DETAIL,
               NVL(SW.SIGN_WORD_THUMBNAIL_URL, 'default.jpg') AS IMAGE_URL,
               CASE
                   WHEN SW.SIGN_WORD_TITLE IN ('썰매', '대장', '호랑이,범', '(동물)사자', '다물다') THEN '기초'
                   WHEN SW.SIGN_WORD_TITLE IN ('상승', '소매치기', '거래', '오디오,듣다,소식,청각', '타결,합의') THEN '중급'
                   ELSE '고급'
                   END AS WORDS_TYPE,
               EV.ID AS EDU_VIDEO_ID
        FROM TBL_SIGN_WORD SW
                 LEFT JOIN TBL_EDU_VIDEO EV
                           ON EV.EDU_VIDEO_URL = SW.SIGN_WORD_VIDEO_URL
        WHERE SW.SIGN_WORD_TITLE IN (
                                     '썰매', '대장', '호랑이,범', '(동물)사자', '다물다',
                                     '상승', '소매치기', '거래', '오디오,듣다,소식,청각', '타결,합의',
                                     '살인,살해', '보이다,(현상을)보다', '불가능,불능', '신고', '영국'
            )
    ) S
    ON (T.SIGN_WORD_ID = S.SIGN_WORD_ID)
    WHEN NOT MATCHED THEN
        INSERT (ID, WORDS_TITLE, WORDS_DETAIL, WORDS_IMAGE, WORDS_TYPE, EDU_VIDEO_ID, SIGN_WORD_ID)
            VALUES (SEQ_WORDS.NEXTVAL, S.TITLE, S.DETAIL, S.IMAGE_URL, S.WORDS_TYPE, S.EDU_VIDEO_ID, S.SIGN_WORD_ID);

-- 3. 학습 단계 연결
MERGE INTO TBL_EDU_WORD_MAP T
    USING (
        SELECT CASE
                   WHEN W.WORDS_TYPE = '기초' THEN 1
                   WHEN W.WORDS_TYPE = '중급' THEN 2
                   WHEN W.WORDS_TYPE = '고급' THEN 3
                   END AS EDU_ID,
               W.ID AS WORDS_ID
        FROM TBL_WORDS W
        WHERE W.SIGN_WORD_ID IS NOT NULL
          AND W.WORDS_TITLE IN (
                                '썰매', '대장', '호랑이,범', '(동물)사자', '다물다',
                                '상승', '소매치기', '거래', '오디오,듣다,소식,청각', '타결,합의',
                                '살인,살해', '보이다,(현상을)보다', '불가능,불능', '신고', '영국'
            )
    ) S
    ON (T.EDU_ID = S.EDU_ID AND T.WORDS_ID = S.WORDS_ID)
    WHEN NOT MATCHED THEN
        INSERT (ID, EDU_ID, WORDS_ID)
            VALUES (SEQ_EDU_WORD_MAP.NEXTVAL, S.EDU_ID, S.WORDS_ID);

COMMIT;


