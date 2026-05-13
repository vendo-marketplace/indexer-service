package com.vendo.indexer_service.adapter.security.out.jwt.parser;

import com.vendo.indexer_service.adapter.security.out.jwt.JwtService;
import com.vendo.security_lib.type.UserTokenClaim;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtClaimsParser implements TokenClaimsParser {

    private final JwtService jwtService;

    @Override
    public TokenClaims extract(String token) {
        Claims claims = jwtService.extractAllClaims(token);

        String id = extractId(claims);
        List<String> roles = extractRoles(claims);

        return new TokenClaims(id, roles);
    }

    private String extractId(Claims claims) {
        String id = claims.get(UserTokenClaim.ID.getClaim(), String.class);

        if (id == null || id.isBlank()) {
            log.error("Id claim is not present.");
            throw new BadCredentialsException("Invalid token.");
        }

        return id;
    }

    private List<String> extractRoles(Claims claims) {
        Object rawRoles = claims.get(UserTokenClaim.ROLES.getClaim());
        AuthenticationException e = new BadCredentialsException("Invalid token.");

        if (rawRoles instanceof List<?> list && !list.isEmpty()) {
            if (list.stream().allMatch(String.class::isInstance)) {

                return list.stream()
                        .map(String.class::cast)
                        .toList();
            }
        }

        log.error("Invalid roles claim.");
        throw e;
    }
}
