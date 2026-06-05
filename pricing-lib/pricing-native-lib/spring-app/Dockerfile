# =========================================================
# ETAPA 1 — Compilar librería nativa
# =========================================================
FROM eclipse-temurin:21-jdk AS native-builder

RUN apt-get update && \
    apt-get install -y gcc binutils && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /native

COPY native/pricing.h .
COPY native/pricing.c .

RUN gcc -shared -fPIC -O2 -o libpricing.so pricing.c

RUN echo "=== Symbols ===" && \
    nm -D libpricing.so | grep " T "


# =========================================================
# ETAPA 2 — Build Spring Boot
# =========================================================
FROM maven:3.9-eclipse-temurin-21 AS java-builder

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests


# =========================================================
# ETAPA 3 — Runtime final
# =========================================================
FROM eclipse-temurin:21-jre

COPY --from=native-builder \
    /native/libpricing.so \
    /usr/local/lib/libpricing.so

RUN ldconfig

COPY --from=java-builder \
    /app/target/pricing-native-app-*.jar \
    /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", \
            "-Djna.library.path=/usr/local/lib", \
            "-jar", "/app/app.jar"]