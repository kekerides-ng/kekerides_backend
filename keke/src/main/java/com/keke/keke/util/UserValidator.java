package com.keke.keke.util;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.keke.keke.dao.entity.User;
import com.keke.keke.dao.repository.UserRepository;
import com.keke.keke.exception.UnauthorizedException;
import com.keke.keke.security.JwtUtil;



@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    
    public User validateUser(Long userId) {
        User user = jwtUtil.getAuthenticatedUser();
        if (user == null) throw new UnauthorizedException("User not authenticated");

        User refreshedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!refreshedUser.getUsername().equals(user.getUsername())) {
            log.warn("Cross-user operation detected. Authenticated userId={}, requested userId={}", user.getId(), userId);
            throw new UnauthorizedException("Cross User Operation detected");
        }

        return refreshedUser;
    }
}