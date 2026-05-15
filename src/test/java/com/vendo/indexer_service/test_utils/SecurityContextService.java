package com.vendo.indexer_service.test_utils;

import com.vendo.indexer_service.adapter.security.out.jwt.parser.TokenClaims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityContextService {

    public static Authentication initializeAuth(TokenClaims claims) {
        return new UsernamePasswordAuthenticationToken(
                claims.userId(),
                null,
                claims.roles().stream().map(SimpleGrantedAuthority::new).toList());
    }
}
