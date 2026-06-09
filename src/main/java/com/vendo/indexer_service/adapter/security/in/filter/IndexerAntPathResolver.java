package com.vendo.indexer_service.adapter.security.in.filter;

import com.vendo.indexer_service.infrastructure.props.PathProps;
import com.vendo.security_lib.resolver.AntPathResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class IndexerAntPathResolver implements AntPathResolver {

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final PathProps pathProps;

    @Override
    public boolean isPermittedPath(String path) {
        return Arrays.stream(pathProps.getAllPaths()).anyMatch(pr -> antPathMatcher.match(pr, path));
    }
}
