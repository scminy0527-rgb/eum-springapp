package com.app.springapp.service;

import com.app.springapp.domain.dto.TestDTO;
import com.app.springapp.repository.TestDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDAO testDAO;

    @Override
    public List<TestDTO> getTestList() {
        return testDAO.findAll();
    }
}
