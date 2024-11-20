# Stage 1: Build using Gradle image
FROM gradle:8.10.1-alpine AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY build.gradle.kts settings.gradle.kts ./

# Cache dependencies
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build the application
RUN gradle build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]