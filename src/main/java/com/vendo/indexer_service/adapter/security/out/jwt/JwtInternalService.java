package com.vendo.indexer_service.adapter.security.out.jwt;

import com.vendo.core_lib.type.ServiceName;
import com.vendo.core_lib.type.ServiceRole;
import com.vendo.indexer_service.adapter.security.out.jwt.props.JwtProperties;
import com.vendo.security_lib.type.TokenClaim;
import com.vendo.security_starter.jwt.JwtPayload;
import com.vendo.security_starter.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtInternalService implements InternalGenerationPort {

    private final JwtProperties props;

    @Override
    public String generate(ServiceName audience) {
        JwtProperties.Internal internal = props.getInternal();

        JwtPayload jwtPayload = JwtPayload.builder()
                .subject(ServiceName.INDEXER_SERVICE.getServiceName())
                .claims(Map.of(TokenClaim.ROLES.getClaim(), ServiceRole.INTERNAL.name()))
                .audience(Set.of(ServiceRole.INTERNAL.name()))
                .expiration(internal.expirationTime())
                .build();

        return JwtService.buildToken(jwtPayload, internal.key());
    }

}
