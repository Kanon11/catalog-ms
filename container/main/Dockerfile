# syntax=docker/dockerfile:1

# ---- Build stage: compile + package the jar ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Cache dependencies on the pom first
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Build the application
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Runtime stage: slim JRE ----
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Run as non-root
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring

COPY --from=build /workspace/target/catalog-ms-*.jar app.jar

EXPOSE 7171
ENTRYPOINT ["java", "-jar", "app.jar"]
