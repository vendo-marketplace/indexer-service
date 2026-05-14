package com.vendo.indexer_service.adapter.security.out.jwt;

import com.vendo.core_lib.type.ServiceName;
import com.vendo.core_lib.type.ServiceRole;
import com.vendo.indexer_service.adapter.security.out.jwt.props.InternalJwtProperties;
import com.vendo.security_lib.type.InternalTokenClaim;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtInternalTokenService implements InternalTokenGenerationPort {

    private final JwtService jwtService;

    private final InternalJwtProperties internalJwtProperties;

    @Override
    public String generate(ServiceName audience) {
        Map<String, Object> claims = Map.of(
                InternalTokenClaim.ROLES.getClaim(), List.of(ServiceRole.INTERNAL.toString()),
                Claims.AUDIENCE, Set.of(audience.toString())
        );

        JwtService.JwtPayload jwtPayload = JwtService.JwtPayload.builder()
                .subject(ServiceName.INDEXER_SERVICE.toString())
                .claims(claims)
                .expirationTime(internalJwtProperties.getExpirationTime())
                .build();

        return jwtService.buildToken(internalJwtProperties.getKey(), jwtPayload);
    }

}
