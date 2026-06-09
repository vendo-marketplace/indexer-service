package com.vendo.indexer_service.test_utils;

import com.vendo.user_lib.model.User;
import com.vendo.user_lib.type.UserRole;
import com.vendo.user_lib.type.UserStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

public class SecurityContextService {

    public static Authentication initializeAuth(UserRole role) {
        User user = User.builder().id("id").roles(Set.of(role)).status(UserStatus.ACTIVE).build();

        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.singleton(new SimpleGrantedAuthority(role.name())));
    }
}
