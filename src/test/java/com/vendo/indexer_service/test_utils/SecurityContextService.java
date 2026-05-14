package com.vendo.indexer_service.test_utils;

import com.vendo.indexer_service.adapter.security.out.jwt.parser.TokenClaims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextService {

    public static SecurityContext initializeSecurityContext(TokenClaims claims) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                claims,
                null,
                claims.roles().stream().map(SimpleGrantedAuthority::new).toList())
        );

        return securityContext;
    }

    public static SecurityContext initializeSecurityContext(AbstractAuthenticationToken authentication) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }

}
