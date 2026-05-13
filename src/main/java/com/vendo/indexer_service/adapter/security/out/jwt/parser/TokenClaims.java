package com.vendo.indexer_service.adapter.security.out.jwt.parser;

import java.util.List;

public record TokenClaims(
        String userId,
        List<String> roles
) {

}
