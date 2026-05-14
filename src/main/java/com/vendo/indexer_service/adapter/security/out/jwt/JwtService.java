package com.vendo.indexer_service.adapter.security.out.jwt;

import com.vendo.utils_lib.StringUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    public Key getSignInKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String token, String secretKey) {
        try {
            return parseSignedClaims(token, secretKey).getPayload();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            throw new BadCredentialsException("Token expired.");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BadCredentialsException("Invalid token.");
        }
    }

    public Jws<Claims> parseSignedClaims(String token, String secretKey) throws JwtException {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey(secretKey))
                .build()
                .parseSignedClaims(token);
    }

    public String buildToken(String secretKey, JwtPayload payload) {
        if (payload == null || StringUtils.isEmpty(payload.subject)) throw new IllegalArgumentException("Invalid payload.");

        return Jwts.builder()
                .subject(payload.subject())
                .claims(payload.claims())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + payload.expirationTime))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    @Builder
    public record JwtPayload(
            String subject,
            Map<String, Object> claims,
            long expirationTime) {
    }
}
