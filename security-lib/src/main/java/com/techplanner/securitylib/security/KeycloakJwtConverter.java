package com.techplanner.securitylib.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // collect raw realm and resource roles separately so we can delegate
        // normalization/merging to native code when available
        List<String> realmRoles = new ArrayList<>();
        List<String> resourceRoles = new ArrayList<>();

        collectRealmRoles(jwt.getClaimAsMap("realm_access"), realmRoles);
        collectResourceRoles(jwt.getClaimAsMap("resource_access"), resourceRoles);

        String[] normalized;
        try {
            normalized = NativeSecurity.normalizeRoles(
                    realmRoles.toArray(new String[0]),
                    resourceRoles.toArray(new String[0]));
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            // native lib not available — fallback to Java implementation
            Set<String> roles = new LinkedHashSet<>();
            roles.addAll(realmRoles);
            roles.addAll(resourceRoles);
            normalized = roles.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(role -> !role.isBlank())
                    .map(role -> "ROLE_" + role.toUpperCase())
                    .toArray(String[]::new);
        }

        return java.util.Arrays.stream(normalized)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private void collectRealmRoles(Map<String, Object> realmAccess, List<String> roles) {
        if (realmAccess == null) {
            return;
        }

        Object realmRoles = realmAccess.get("roles");
        if (realmRoles instanceof Collection<?> collection) {
            for (Object role : collection) {
                if (role != null) {
                    roles.add(role.toString());
                }
            }
        }
    }

    private void collectResourceRoles(Map<String, Object> resourceAccess, List<String> roles) {
        if (resourceAccess == null) {
            return;
        }

        for (Object clientAccess : resourceAccess.values()) {
            if (clientAccess instanceof Map<?, ?> clientAccessMap) {
                Object clientRoles = clientAccessMap.get("roles");
                if (clientRoles instanceof Collection<?> collection) {
                    for (Object role : collection) {
                        if (role != null) {
                            roles.add(role.toString());
                        }
                    }
                }
            }
        }
    }
}
