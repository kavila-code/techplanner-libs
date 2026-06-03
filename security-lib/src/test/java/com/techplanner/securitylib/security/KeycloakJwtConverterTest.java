package com.techplanner.securitylib.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeycloakJwtConverterTest {

    private final KeycloakJwtConverter converter = new KeycloakJwtConverter();

    @Test
    void convertShouldMapRealmRolesToSpringAuthorities() {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("realm_access", Map.of("roles", List.of("admin", "user")))
                .subject("jose")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .collect(Collectors.toList());

        assertEquals(List.of("ROLE_ADMIN", "ROLE_USER"), authorities);
        assertEquals("jose", authentication.getName());
    }

    @Test
    void convertShouldMapResourceRolesToo() {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "RS256")
                .claim("resource_access", Map.of(
                        "clientes-service", Map.of("roles", List.of("editor"))))
                .subject("ana")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        AbstractAuthenticationToken authentication = converter.convert(jwt);

        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_EDITOR".equals(authority.getAuthority())));
    }
}
