package com.vendo.indexer_service.infrastructure.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@Profile("prod || dev")
public class AsyncConfig {
}
