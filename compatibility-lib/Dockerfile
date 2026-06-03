FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /workspace

COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src src

RUN chmod +x mvnw && ./mvnw -q clean verify

FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app

RUN addgroup -S compatibility && adduser -S compatibility -G compatibility

COPY --from=build /workspace/target/compatibility-lib-1.0.0-SNAPSHOT.jar /app/compatibility-lib.jar

USER compatibility

ENTRYPOINT ["java", "-jar", "/app/compatibility-lib.jar"]
