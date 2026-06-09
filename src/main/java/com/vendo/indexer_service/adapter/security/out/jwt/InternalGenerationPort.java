package com.vendo.indexer_service.adapter.security.out.jwt;

import com.vendo.core_lib.type.ServiceName;

public interface InternalGenerationPort {

    String generate(ServiceName audience);

}
