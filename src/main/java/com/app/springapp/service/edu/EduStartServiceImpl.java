package com.app.springapp.service.edu;

import com.app.springapp.domain.vo.EduStartVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.EduStartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor =  {EduException.class})
public class EduStartServiceImpl implements EduStartService {
    private final EduStartDAO eduStartDAO;

    // 학습 시작 기록 저장
    @Override
    public void startEdu(Long userId, Long eduId) {
        if (eduStartDAO.findByUserIdAndEduId(userId, eduId).isPresent()) {
            return;
        }

        EduStartVO eduStartVO = new EduStartVO();
        eduStartVO.setUserId(userId);
        eduStartVO.setEduId(eduId);

        eduStartDAO.save(eduStartVO);
    }
}
