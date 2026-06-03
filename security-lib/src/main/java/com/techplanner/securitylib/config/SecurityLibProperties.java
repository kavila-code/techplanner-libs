package com.techplanner.securitylib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "security.lib")
public class SecurityLibProperties {

    /**
     * Endpoints públicos que no requieren autenticación.
     */
    private List<String> publicPaths = new ArrayList<>(List.of("/public/**"));

    public List<String> getPublicPaths() {
        return publicPaths;
    }

    public void setPublicPaths(List<String> publicPaths) {
        this.publicPaths = publicPaths;
    }
}
