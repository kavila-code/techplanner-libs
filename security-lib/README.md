# security-lib

Reusable Spring Security library for TechPlanner microservices.

## What it provides

- Spring Security base configuration
- OAuth2 Resource Server + JWT support
- Keycloak role conversion to Spring Security authorities
- Stateless session policy
- Method security enabled for `@PreAuthorize`

## Default behavior

- Public endpoints: `/public/**`
- Any other request: authenticated
- JWT converter: maps Keycloak roles from `realm_access.roles` and `resource_access.*.roles`
- Session policy: `STATELESS`

## Usage in a microservice

Add the dependency and keep the standard resource server issuer configuration in the service application:

```xml
<dependency>
    <groupId>com.techplanner</groupId>
    <artifactId>security-lib</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/techplanner
```

## Optional customization

```yaml
security:
  lib:
    public-paths:
      - /public/**
      - /actuator/health
```
