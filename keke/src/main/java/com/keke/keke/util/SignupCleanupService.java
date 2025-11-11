package com.keke.keke.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keke.keke.dao.entity.SignupRequest;
import com.keke.keke.dao.repository.SignupRequestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignupCleanupService {

    private final SignupRequestRepository signupRequestRepository;

    @Scheduled(fixedRate = 30 * 60 * 1000) 
    @Transactional
    public void cleanOldSignupRequests() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);

        List<SignupRequest> oldRequests = signupRequestRepository.findAllByCreatedAtBefore(cutoff);

        if (!oldRequests.isEmpty()) {
            signupRequestRepository.deleteAll(oldRequests);
            log.info("Cleaned {} old unverified signup requests", oldRequests.size());
        } else {
            log.debug("No old signup requests to clean");
        }
    }
}


