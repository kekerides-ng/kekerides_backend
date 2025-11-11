package com.keke.keke.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import com.keke.keke.config.AppProperties;
import com.keke.keke.dao.entity.Permission;
import com.keke.keke.dao.entity.Role;
import com.keke.keke.dao.entity.User;
import com.keke.keke.exception.UnauthorizedException;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
   
    private final JwtEncoder encoder;
    private final JwtDecoder jwtDecoder;
    private final AppProperties appProperties;

    public String createToken(User user) {
        return createJwt(user, appProperties.getJwtExpiresAt());
    }

    public String createRefreshToken(User user) {
        long refreshTokenExpiry = 7 * 24 * 60 * 60L;
        return createJwt(user, refreshTokenExpiry);
    }

    private String createJwt(User user, long expiresInSeconds) {
        Instant now = Instant.now();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        List<String> permissions = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getName)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(appProperties.getJwtIssuer())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresInSeconds))
                .subject(user.getUsername())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .build();

        return encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String getUsernameFromToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public boolean isNotTokenExpired(String token) {
        Instant exp = jwtDecoder.decode(token).getExpiresAt();
        return exp != null && exp.isAfter(Instant.now());
    }

    public User getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User user) return user;
        throw new UnauthorizedException("User not authenticated");
    }
}

